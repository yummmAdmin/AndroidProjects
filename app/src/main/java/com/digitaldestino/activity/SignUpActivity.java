package com.digitaldestino.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.modelClass.otp.OtpResponse;
import com.digitaldestino.modelClass.signup.SignUpResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.staticUI.PrivacyPolicy;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_address, edt_first_name, edt_last_name, edt_email, edt_mobile,
            edt_password, edt_con_password, edt_city, edt_zip_code, edt_varification_code,edt_referal_code;
    private Button btn_submit, btn_verify;
    private TextView text_resend_code, text_terms;
    private CheckBox chk_select;
    private RelativeLayout hide_keyboard, relative_mobile, relative_signup;
    private String First_Name, Last_Name, Email, Mobile, Password, Confirm_Pass, City, Zip_Code, Address,referal_code,
            Code = "", device_token, device_id, msg, status, otp, status_signup, profile_status, _id;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private double Latitude, Longitude;
    private GoogleApiClient mClient;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private Intent intent;
    private boolean isCheckedd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findView();
    }

    // to initialize object here...
    private void findView() {
        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        device_id = BMSPrefs.getString(SignUpActivity.this, Constants.DEVICE_ID);
        device_token = BMSPrefs.getString(SignUpActivity.this, Constants.DEVICE_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        hide_keyboard = findViewById(R.id.main_layout);
        edt_address = findViewById(R.id.edt_address);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_password = findViewById(R.id.edt_password);
        edt_con_password = findViewById(R.id.edt_con_password);
        edt_city = findViewById(R.id.edt_city);
        edt_zip_code = findViewById(R.id.edt_zip_code);
        edt_varification_code = findViewById(R.id.edt_varification_code);
        edt_referal_code=findViewById(R.id.edt_referal_code);
        btn_submit = findViewById(R.id.btn_submit);
        btn_verify = findViewById(R.id.btn_verify);
        relative_mobile = findViewById(R.id.relative_mobile);
        relative_signup = findViewById(R.id.relative_signup);
        text_resend_code = findViewById(R.id.text_resend_code);
        chk_select = findViewById(R.id.chk_select);
        text_terms = findViewById(R.id.text_terms);
        edt_address.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        text_resend_code.setOnClickListener(this);
        text_terms.setOnClickListener(this);
        chk_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCheckedd = true;
                } else {
                    isCheckedd = false;
                }
            }
        });

        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
            }
        });
    }

    // to manage listener here...
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_address: {
             //   edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                //hide password
              //  edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(SignUpActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            }

            case R.id.btn_submit: {
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        CommonMethod.hideKeyBoard(SignUpActivity.this);
                        if (isCheckedd) {
                            getOtpData("1");
                        } else {
                            CommonMethod.showToastShort(getString(R.string.chk_terms_amp_conditions), getApplicationContext());
                        }
                    } else {
                        CommonMethod.showAlertDialog(SignUpActivity.this, getString(R.string.internet_toast));
                    }

                } else {
                    CommonMethod.showAlertDialog(SignUpActivity.this, result);
                }
                break;
            }
            case R.id.main_layout: {
                CommonMethod.hideKeyBoard(SignUpActivity.this);
                break;
            }
            case R.id.text_resend_code: {
                isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    getOtpData("1");
                } else {
                    CommonMethod.showAlertDialog(SignUpActivity.this, getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.btn_verify: {
                String result_mob = validatePhoneForm();
                if (result_mob.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        CommonMethod.hideKeyBoard(SignUpActivity.this);
                        getSignupData();
                    } else {
                        CommonMethod.showAlertDialog(SignUpActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(SignUpActivity.this, result_mob);
                }
                break;
            }
            case R.id.text_terms: {
                intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                //     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                // finish();
                break;
            }
        }
    }

    // validate form
    private String validateForm() {
        First_Name = edt_first_name.getText().toString().trim();
        Last_Name = edt_last_name.getText().toString().trim();
        Email = edt_email.getText().toString().trim();
        Mobile = edt_mobile.getText().toString().trim();
        Password = edt_password.getText().toString().trim();
        Confirm_Pass = edt_con_password.getText().toString();
        Address = edt_address.getText().toString().trim();
        City = edt_city.getText().toString().trim();
        Zip_Code = edt_zip_code.getText().toString().trim();
        referal_code=edt_referal_code.getText().toString().trim();
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

        if (Password.isEmpty() || !CommonMethod.isValidPassword(Password)) {
            edt_password.setFocusable(true);
            edt_password.requestFocus();
            return getString(R.string.valid_password);
        }

        if (Confirm_Pass.isEmpty()) {
            edt_con_password.setFocusable(true);
            edt_con_password.requestFocus();
            return getString(R.string.valid_password_blank);
        }

        if (!Confirm_Pass.contentEquals(Password)) {
            edt_con_password.setFocusable(true);
            edt_con_password.requestFocus();
            return getString(R.string.same_password);
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

    // validate phn varification
    private String validatePhoneForm() {
        Code = edt_varification_code.getText().toString().trim();
        if (Code.isEmpty() || Code.equals("")) {
            edt_varification_code.setFocusable(true);
            edt_varification_code.requestFocus();
            return getString(R.string.valid_code);
        }

        if (!Code.contentEquals(otp)) {
            edt_varification_code.setFocusable(true);
            edt_varification_code.requestFocus();
            return getString(R.string.valid_code);
        }
        return "success";
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
                BMSPrefs.putString(getApplicationContext(),Constants.LATITUDE,""+Latitude);
                BMSPrefs.putString(getApplicationContext(),Constants.LONGITUDE,""+Longitude);
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

    // backpress method
    @Override
    public void onBackPressed() {
        if (relative_signup.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else if (relative_mobile.getVisibility() == View.VISIBLE) {
            relative_mobile.setVisibility(View.GONE);
            relative_mobile.animate();
            relative_signup.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        }
    }

    // get signup detail from api
    private void getOtpData(String req_type) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("device_id", device_id);
        params.put("device_token", device_token);
        params.put("email", Email);
        params.put("password", Password);
        params.put("latitude", String.valueOf(Latitude));
        params.put("longitude", String.valueOf(Longitude));
        params.put("device_type", "1");
        params.put("mobile", Mobile);
        params.put("first_name", First_Name);
        params.put("last_name", Last_Name);
        params.put("address", Address);
        params.put("city", City);
        params.put("zipcode", Zip_Code);

        params.put("request_type", req_type);
        params.put("country_code", "+91");
        params.put("debug_mode", "1");
        Log.d("Param",params.toString());
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getOtpDetails(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OtpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog = CommonMethod.showProgressDialog(progressDialog, SignUpActivity.this, getString(R.string.please_wait));
                    }

                    @Override
                    public void onNext(OtpResponse otpResponse) {
                        progressDialog.dismiss();
                        Log.d("responseSignup", otpResponse.toString());
                        if (otpResponse != null) {
                            status = otpResponse.getStatus();
                            msg = otpResponse.getMsg();
                            if (status.equalsIgnoreCase("1")) {
                                relative_mobile.setVisibility(View.VISIBLE);
                                relative_signup.setVisibility(View.GONE);
                                otp = otpResponse.getResponse_data().getOTP();
                            }
                            CommonMethod.showToastShort(msg, getApplicationContext());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
    }

    // get signup detail from api
    private void getSignupData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("device_id", device_id);
        params.put("device_token", device_token);
        params.put("email", Email);
        params.put("password", Password);
        params.put("latitude", String.valueOf(Latitude));
        params.put("longitude", String.valueOf(Longitude));
        params.put("device_type", "1");
        params.put("mobile", Mobile);
        params.put("first_name", First_Name);
        params.put("last_name", Last_Name);
        params.put("address", Address);
        params.put("city", City);
        params.put("zipcode", Zip_Code);
        params.put("refferal_code", referal_code);
        params.put("request_type", "2");
        params.put("country_code", "+91");
        params.put("debug_mode", "1");
        Log.d("Param",params.toString());
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getSignupDetail(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignUpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog = CommonMethod.showProgressDialog(progressDialog, SignUpActivity.this, getString(R.string.please_wait));
                    }

                    @Override
                    public void onNext(SignUpResponse signUpResponse) {
                        progressDialog.dismiss();
                        Log.d("response", signUpResponse.toString());
                        if (signUpResponse != null) {
                            status_signup = signUpResponse.getStatus();
                            msg = signUpResponse.getMsg();
                            if (status_signup.equalsIgnoreCase("1")) {
                                profile_status = signUpResponse.getResponse_data().getUser().getProfile_status();
                                _id = signUpResponse.getResponse_data().getUser().get_id();
                                BMSPrefs.putString(SignUpActivity.this, Constants._ID, _id);
                                CommonMethod.showToastShort(msg, getApplicationContext());
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_slide_in_left,
                                        R.anim.anim_slide_out_left);
                                finish();
                            } else {
                                CommonMethod.showToastShort(msg, getApplicationContext());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }


}
