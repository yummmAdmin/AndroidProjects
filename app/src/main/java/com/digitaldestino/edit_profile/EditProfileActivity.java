package com.digitaldestino.edit_profile;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.change_pwd.ChangePasswordActivity;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.menu_list.MenuActivity;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, GetProfileContract.View {
    private ImageView img_back,img_bookmark,img_notification;
    private EditText edt_first_name, edt_last_name, edt_email, edt_mobile, edt_city, edt_zip_code, edt_change_password, edt_address;
    private Button btn_save;
    private TextView text_delete_account, text_chnge_pwd;
    private RelativeLayout hide_keyboard;
    private String First_Name, Last_Name, Email, Mobile, City, Zip_Code, Address, _id, session_token, device_id, device_token, login_type;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private GoogleApiClient mClient;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private double Latitude, Longitude;
    private ProgressDialog progressDialog;
    private String TAG = "EditProfileActivity";
    private GetProfilePresenter getProfilePresenter;
    private EditProfilePresenter editProfilePresenter;
    private DeleteProfilePresenter deleteProfilePresenter;
    private Dialog dialogMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findView();
        if (isInternetPresent) {
            HashMap<String, String> params = new HashMap<>();
            params.put("_id", _id);
            params.put("session_token", session_token);
            getProfilePresenter = new GetProfilePresenter(this);
            getProfilePresenter.requestProfileData(params);
        } else {
            CommonMethod.showAlertDialog(EditProfileActivity.this, getString(R.string.internet_toast));
        }
    }

    private void findView() {
        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        deleteProfilePresenter = new DeleteProfilePresenter(this);
        _id = BMSPrefs.getString(EditProfileActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(EditProfileActivity.this, Constants.SESSION_TOKEN);
        login_type = BMSPrefs.getString(EditProfileActivity.this, Constants.LOGIN_TYPE);
        device_id = BMSPrefs.getString(EditProfileActivity.this, Constants.DEVICE_ID);
        device_token = BMSPrefs.getString(EditProfileActivity.this, Constants.DEVICE_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        img_back = findViewById(R.id.img_back);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_city = findViewById(R.id.edt_city);
        edt_zip_code = findViewById(R.id.edt_zip_code);
        edt_change_password = findViewById(R.id.edt_change_password);
        btn_save = findViewById(R.id.btn_save);
        text_delete_account = findViewById(R.id.text_delete_account);
        text_chnge_pwd = findViewById(R.id.text_chnge_pwd);
        edt_address = findViewById(R.id.edt_address);
        hide_keyboard = findViewById(R.id.hide_keyboard);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        edt_change_password.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        text_delete_account.setOnClickListener(this);
        edt_address.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        if (login_type.equalsIgnoreCase("normal")) {
            text_delete_account.setVisibility(View.VISIBLE);
            text_chnge_pwd.setVisibility(View.VISIBLE);
            edt_change_password.setVisibility(View.VISIBLE);
            edt_mobile.setFocusable(false);
            edt_email.setFocusable(false);
        } else {
            text_delete_account.setVisibility(View.GONE);
            text_chnge_pwd.setVisibility(View.GONE);
            edt_change_password.setVisibility(View.GONE);
            edt_mobile.setFocusable(true);
            edt_email.setFocusable(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.edt_change_password: {
                callActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                break;
            }
            case R.id.btn_save: {
                CommonMethod.hideKeyBoard(EditProfileActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    if (isInternetPresent) {
                        final HashMap<String, String> params = new HashMap<>();
                        params.put("device_id", device_id);
                        params.put("device_token", device_token);
                        params.put("email", Email);
                        params.put("latitude", String.valueOf(Latitude));
                        params.put("longitude", String.valueOf(Longitude));
                        params.put("mobile", Mobile);
                        params.put("first_name", First_Name);
                        params.put("last_name", Last_Name);
                        params.put("address", Address);
                        params.put("city", City);
                        params.put("zipcode", Zip_Code);
                        params.put("_id", _id);
                        params.put("session_token", session_token);
                        params.put("country_code", "+91");
                        editProfilePresenter = new EditProfilePresenter(this);
                        editProfilePresenter.requestProfileData(params);
                    } else {
                        CommonMethod.showAlertDialog(EditProfileActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(EditProfileActivity.this, result);
                }
                break;
            }

            case R.id.text_delete_account: {
                if (isInternetPresent) {
                    openDialouge();
                } else {
                    CommonMethod.showAlertDialog(EditProfileActivity.this, getString(R.string.internet_toast));
                }

                break;
            }

            case R.id.img_bookmark: {
                callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;
            }
            case R.id.img_notification: {
                callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            }
            case R.id.hide_keyboard: {
                CommonMethod.hideKeyBoard(EditProfileActivity.this);
                break;
            }
            case R.id.edt_address: {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(EditProfileActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
            break;
        }

    }

    // validate form
    private String validateForm() {
        First_Name = edt_first_name.getText().toString().trim();
        Last_Name = edt_last_name.getText().toString().trim();
        Email = edt_email.getText().toString().trim();
        Mobile = edt_mobile.getText().toString().trim();
        City = edt_city.getText().toString().trim();
        Zip_Code = edt_zip_code.getText().toString().trim();
        Address = edt_address.getText().toString().trim();

        if (First_Name.isEmpty() || !CommonMethod.isValidName(First_Name)) {
            edt_first_name.setFocusable(true);
            edt_first_name.requestFocus();
            return getString(R.string.valid_name);
        }

        if (Last_Name.isEmpty() || !CommonMethod.isValidName(Last_Name)) {
            edt_last_name.setFocusable(true);
            edt_last_name.requestFocus();
            return getString(R.string.valid_last_name);
        }

        if (Email.isEmpty() || !CommonMethod.isEmailValid(Email)) {
            edt_email.setFocusable(true);
            edt_email.requestFocus();
            return getString(R.string.valid_email);
        }

        if (Mobile.isEmpty() || Mobile.length() < 6) {
            edt_mobile.setFocusable(true);
            edt_mobile.requestFocus();
            return getString(R.string.valid_phone);
        }
        if (Address.isEmpty()) {
            edt_address.setFocusable(true);
            edt_address.requestFocus();
            return getString(R.string.valid_address);
        }

        if (City.isEmpty() || !CommonMethod.isValidName(City)) {
            edt_city.setFocusable(true);
            edt_city.requestFocus();
            return getString(R.string.valid_city);
        }

        if (Zip_Code.isEmpty()) {
            edt_zip_code.setFocusable(true);
            edt_zip_code.requestFocus();
            return getString(R.string.valid_zip);
        }
        return "success";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivity(new Intent(getApplicationContext(), MenuActivity.class));
        finish();
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    protected void onStop() {
        mClient.disconnect();
        super.onStop();
    }

    // method override for place select
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                Latitude = place.getLatLng().latitude;
                Longitude = place.getLatLng().longitude;
                BMSPrefs.putString(getApplicationContext(), Constants.LATITUDE, "" + Latitude);
                BMSPrefs.putString(getApplicationContext(), Constants.LONGITUDE, "" + Longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append(placename);
                List<android.location.Address> addresses = null;
                try {
                    addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(Latitude, Longitude, 1);
                    String cityy = ((Address) addresses.get(0)).getLocality();
                    String postalcode = ((Address) addresses.get(0)).getPostalCode();

                    stBuilder.append(address);
                    edt_address.setText(placename);
                    edt_city.setText(cityy);
                    edt_zip_code.setText(postalcode);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //dialog open on button click
    private void openDialouge() {
        dialogMsg = new Dialog(EditProfileActivity.this, R.style.AppTheme1);
        dialogMsg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMsg.setCancelable(true);
        dialogMsg.setContentView(R.layout.deleteaccount_popup);
        dialogMsg.getWindow().setBackgroundDrawableResource(R.color.background_color);
        Window window = dialogMsg.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialogMsg.getWindow()
                .getAttributes().windowAnimations = R.style.AppTheme1;
        Button btn_yes = dialogMsg.findViewById(R.id.btn_yes);
        Button btn_no = dialogMsg.findViewById(R.id.btn_no);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> params = new HashMap<>();
                params.put("id", _id);
                params.put("session_token", session_token);
                deleteProfilePresenter.requestProfileData(params);
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMsg.dismiss();
            }
        });

        dialogMsg.show();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, EditProfileActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String first_name, String last_name, String email, String mobile, String address, String city, String zip_code, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("updateuser")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            BMSPrefs.putString(EditProfileActivity.this, Constants.USER_NAME, First_Name + " " + Last_Name);
            BMSPrefs.putString(EditProfileActivity.this, Constants.USER_Address, Address);
            BMSPrefs.putString(EditProfileActivity.this, Constants.USER_NUMBER, Mobile);
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getuser")) {
            edt_first_name.setText(first_name);
            edt_last_name.setText(last_name);
            edt_email.setText(email);
            edt_mobile.setText(mobile);
            edt_address.setText(address);
            edt_city.setText(city);
            edt_zip_code.setText(zip_code);
        } else if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("deleteuser")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            LoginManager.getInstance().logOut();
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
