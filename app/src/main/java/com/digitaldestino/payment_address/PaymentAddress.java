package com.digitaldestino.payment_address;

import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.activity.SignUpActivity;
import com.digitaldestino.book_table.BookTableActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.modelClass.login.User;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.payment.PaymentActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PaymentAddress extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, img_bookmark, img_notification;
    private EditText edt_first_name, edt_mobile, edt_address, edt_city, edt_zip_code;
    private TextView text_name, text_address, text_mobile;
    private Button btn_proceedpayment;
    private CardView card_view;
    private RelativeLayout r4, r3;
    private RadioButton radio_new_address, radio_address;
    private String user_name, user_address, user_number, check_select = "1", Name, Mobile, Address, City, Zip_Code, delivery_address = "", login_type;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private double Latitude, Longitude;
    private GoogleApiClient mClient;
    private String lati, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_address);
        findView();
        setData();
    }


    // initialize object here...
    private void findView() {
        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        login_type = BMSPrefs.getString(PaymentAddress.this, Constants.LOGIN_TYPE);
        img_back = findViewById(R.id.img_back);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_address = findViewById(R.id.edt_address);
        edt_city = findViewById(R.id.edt_city);
        edt_zip_code = findViewById(R.id.edt_zip_code);
        btn_proceedpayment = findViewById(R.id.btn_proceedpayment);
        radio_address = findViewById(R.id.radio_address);
        r4 = findViewById(R.id.r4);
        r3 = findViewById(R.id.r3);
        radio_new_address = findViewById(R.id.radio_new_address);
        text_name = findViewById(R.id.text_name);
        text_address = findViewById(R.id.text_address);
        text_mobile = findViewById(R.id.text_mobile);
        card_view = findViewById(R.id.card_view);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        btn_proceedpayment.setOnClickListener(this);
        img_back.setOnClickListener(this);
        edt_address.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        radio_new_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check_select = "2";
                    r4.setVisibility(View.VISIBLE);
                    r3.setBackgroundColor(Color.parseColor("#E83B33"));
                    radio_address.setChecked(false);
                    radio_new_address.setChecked(true);
                }
            }
        });

        radio_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check_select = "1";
                    r4.setVisibility(View.GONE);
                    r3.setBackgroundColor(Color.parseColor("#A1A0A4"));
                    radio_address.setChecked(true);
                    radio_new_address.setChecked(false);
                }


            }
        });

        if (login_type.equalsIgnoreCase("normal")) {
            check_select = "1";
            card_view.setVisibility(View.VISIBLE);
            radio_address.setChecked(true);
            radio_new_address.setChecked(false);
        } else {
            check_select = "2";
            card_view.setVisibility(View.GONE);
            radio_address.setChecked(false);
            radio_new_address.setChecked(true);
        }
    }

    //set data
    private void setData() {
        user_name = BMSPrefs.getString(PaymentAddress.this, Constants.USER_NAME);
        user_address = BMSPrefs.getString(PaymentAddress.this, Constants.USER_Address);
        user_number = BMSPrefs.getString(PaymentAddress.this, Constants.USER_NUMBER);
        text_name.setText(user_name);
        text_address.setText(user_address);
        text_mobile.setText(user_number);
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


    // listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_proceedpayment: {
                BMSPrefs.putString(getApplicationContext(),"payment_status","1");
                if (check_select.equalsIgnoreCase("1")) {
                    lati = BMSPrefs.getString(getApplicationContext(), Constants.LATITUDE);
                    longi = BMSPrefs.getString(getApplicationContext(), Constants.LONGITUDE);
                    delivery_address = user_name + "," + user_address + "," + user_number;
                    BMSPrefs.putString(getApplicationContext(), Constants.DELIVERY_ADDRESS, delivery_address);
                    BMSPrefs.putString(getApplicationContext(), Constants.LATI, lati);
                    BMSPrefs.putString(getApplicationContext(), Constants.LONGI, longi);
                    callActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                } else {
                    String result = validateForm();
                    if (result.equalsIgnoreCase("success")) {
                        lati = "" + Latitude;
                        longi = "" + Longitude;

                        delivery_address = Name + "," + Address + "," + City + "," + Zip_Code + "," + Mobile;
                        BMSPrefs.putString(getApplicationContext(), Constants.DELIVERY_ADDRESS, delivery_address);
                        BMSPrefs.putString(getApplicationContext(), Constants.LATI, lati);
                        BMSPrefs.putString(getApplicationContext(), Constants.LONGI, longi);
                        callActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                    } else {
                        CommonMethod.showAlertDialog(PaymentAddress.this, result);
                    }
                }
                break;
            }
            case R.id.edt_address: {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(PaymentAddress.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
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
        }
    }

    // validation apply here...
    private String validateForm() {
        Name = edt_first_name.getText().toString().trim();
        Mobile = edt_mobile.getText().toString().trim();
        Address = edt_address.getText().toString().trim();
        City = edt_city.getText().toString().trim();
        Zip_Code = edt_zip_code.getText().toString().trim();
        if (Name.isEmpty() || !CommonMethod.isValidName(Name)) {
            edt_first_name.setFocusable(true);
            edt_first_name.requestFocus();
            return getString(R.string.valid_name);
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
                String address = String.format("%s", place.getAddress());
                stBuilder.append(placename);
                List<Address> addresses = null;
                try {
                    addresses = new Geocoder(this, Locale.getDefault()).getFromLocation(Latitude, Longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String cityy = ((Address) addresses.get(0)).getLocality();
                String postalcode = ((Address) addresses.get(0)).getPostalCode();

                stBuilder.append(address);
                edt_address.setText(placename);
                edt_address.setBackground(null);
                edt_city.setText(cityy);
                edt_zip_code.setText(postalcode);

            }
        }
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivityFinish(new Intent(getApplicationContext(), Cart_Activity.class));
    }

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

}
