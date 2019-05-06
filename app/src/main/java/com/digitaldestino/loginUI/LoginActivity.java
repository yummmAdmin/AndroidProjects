package com.digitaldestino.loginUI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.forgot_pwd.ForgotPasswordActivity;
import com.digitaldestino.activity.SignUpActivity;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.modelClass.login.User;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View, SocialContract.View {

    private TextView txt_signup, txt_forgot_password;
    private ImageView img_insta;
    private EditText edt_email, edt_password;
    private RelativeLayout hide_keyboard, relative_fb;
    private String Email, Password, Latitude, Longitude, device_id, device_token, _id, session_token;
    private Button btn_login;
    private Intent intent;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private GPSTracker gpsTracker;
    private ProgressDialog progressDialog;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private final String TAG = "LoginActivity";
    private LoginPresenter loginPresenter;
    private SocialLoginPresenter socialLoginPresenter;
    private LoginButton loginButton;
    CallbackManager callbackManager;
    TwitterLoginButton twitterLoginButton;

    //twitter auth client required for custom login
    private TwitterAuthClient client;
    TwitterConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("3rYhLWcbZMqG434b6kMwpqmon", "EEBdSSXXTLbHglKIB1D7PNlRLHPfvtZy1BT6r9EXiun2UBxP2J"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        findView();
    }

    // to initialize object here...
    private void findView() {
        if (!checkPermission()) {
            requestPermission();
            getCurrentLatLong();
        } else {
            getCurrentLatLong();
        }
        //initialize twitter auth client
        client = new TwitterAuthClient();

        device_id = BMSPrefs.getString(LoginActivity.this, Constants.DEVICE_ID);
        device_token = BMSPrefs.getString(LoginActivity.this, Constants.DEVICE_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        loginButton = findViewById(R.id.login_button);
        txt_signup = findViewById(R.id.txt_signup);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        hide_keyboard = findViewById(R.id.hide_keyboard);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        relative_fb = findViewById(R.id.relative_fb);
        twitterLoginButton = findViewById(R.id.default_twitter_login_button);
        txt_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
        txt_forgot_password.setOnClickListener(this);
        relative_fb.setOnClickListener(this);
        fbLogin();
        //  defaultLoginTwitter();
    }

    //get current latitude and longitude
    private void getCurrentLatLong() {
        gpsTracker = new GPSTracker(LoginActivity.this);
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Longitude = String.valueOf(gpsTracker.getLongitude());
    }

    // social login
    private void requestParam(String first_name, String last_name, String email, String phoneNumber, String social_id, String social_type) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("device_id", device_id);
        params.put("device_token", device_token);
        params.put("email", email);
        params.put("latitude", String.valueOf(Latitude));
        params.put("longitude", String.valueOf(Longitude));
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("mobile", phoneNumber);
        params.put("device_type", "1");
        params.put("debug_mode", "1");
        params.put("social_type", social_type);
        params.put("social_id", social_id);
        Log.d("SocialParam", params.toString());
        socialLoginPresenter = new SocialLoginPresenter(this);
        socialLoginPresenter.requestSocialLoginData(params);
    }


    // fb login method
    private void fbLogin() {
        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
        if (!loggedOut) {
            // Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG123", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");
                getUserProfile(AccessToken.getCurrentAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.d("TAGfile", object.toString());
                            String first_name = object.optString("first_name");
                            String last_name = object.optString("last_name");
                            String email = object.optString("email");
                            String id = object.optString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            requestParam(first_name, last_name, email, "", id, "fb");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_signup: {
                callActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;
            }

            case R.id.btn_login: {
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        if (!checkPermission()) {
                            requestPermission();
                        } else {
                            getCurrentLatLong();
                            HashMap<String, String> params = new HashMap<>();
                            params.put("device_id", device_id);
                            params.put("device_token", device_token);
                            params.put("email", Email);
                            params.put("password", Password);
                            params.put("latitude", Latitude);
                            params.put("longitude", Longitude);
                            params.put("device_type", "1");
                            params.put("debug_mode", "1");
                            Log.d("loginparam", params.toString());
                            loginPresenter = new LoginPresenter(this);
                            loginPresenter.requestLoginData(params);
                            //  CommonMethod.showToastShort("Permission already granted",getApplicationContext());
                        }
                    } else {
                        CommonMethod.showAlertDialog(LoginActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(LoginActivity.this, result);
                }
                break;
            }

            case R.id.hide_keyboard: {
                CommonMethod.hideKeyBoard(LoginActivity.this);
                break;
            }

            case R.id.txt_forgot_password: {
                // CommonMethod.showToastShort(getString(R.string.under_development),getApplicationContext());
                callActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                break;
            }

            case R.id.relative_fb: {
                loginButton.performClick();
                break;
            }

        }
    }


    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // validate form
    private String validateForm() {
        Email = edt_email.getText().toString().trim();
        Password = edt_password.getText().toString().trim();

        if (Email.isEmpty() || !CommonMethod.isEmailValid(Email)) {
            edt_email.setFocusable(true);
            edt_email.requestFocus();
            return getString(R.string.valid_email);
        }

        if (Password.isEmpty()) {
            edt_password.setFocusable(true);
            edt_password.requestFocus();
            return getString(R.string.valid_pwd);
        }

        return "success";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
    }

    // call activity method
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        CommonMethod.showToastShort("Permission Granted, Now you can access location", getApplicationContext());
                    } else {
                        CommonMethod.showToastShort("Permission Denied, You cannot access location", getApplicationContext());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, LoginActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setSocialData(String status, String msg, String key, User user) {
        CommonMethod.showToastShort(msg, getApplicationContext());
        if (status.equalsIgnoreCase("1")) {
            userData(user);
        }
    }

    @Override
    public void setDataToViews(User user, String status, String msg) {
        CommonMethod.showToastShort(msg, getApplicationContext());
        if (status.equalsIgnoreCase("1")) {
            userData(user);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }


    //customer data
    private void userData(User user) {
        _id = user.get_id();
        session_token = user.getSession_token();
        BMSPrefs.putString(LoginActivity.this, Constants._ID, _id);
        BMSPrefs.putString(LoginActivity.this, Constants.REFERAL_CODE, user.getRefferal_code());
        BMSPrefs.putString(LoginActivity.this, Constants.SESSION_TOKEN, session_token);
        BMSPrefs.putString(LoginActivity.this, Constants.Seeker_id, user.getSeeker_id());
        BMSPrefs.putString(LoginActivity.this, Constants.USER_NAME, user.getFirst_name());
        BMSPrefs.putString(LoginActivity.this, Constants.USER_Address, user.getAddress());
        BMSPrefs.putString(LoginActivity.this, Constants.USER_NUMBER, user.getMobile());
        BMSPrefs.putString(LoginActivity.this, Constants.USER_EMAIL, user.getEmail());
        BMSPrefs.putString(LoginActivity.this, Constants.LOGIN_TYPE, user.getLogin_type());
        BMSPrefs.putString(LoginActivity.this, Constants.LATITUDE, user.getLocation().getLatitude());
        BMSPrefs.putString(LoginActivity.this, Constants.LONGITUDE, user.getLocation().getLongitude());
        intent = new Intent(getApplicationContext(), ArticleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // method to do Default Twitter Login
    public void defaultLoginTwitter() {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            twitterLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch email only when permission is granted
                    fetchTwitterEmail(twitterSession);

                }

                @Override
                public void failure(TwitterException exception) {
                    // Do something on failure
                    CommonMethod.showToastShort("Failed to authenticate. Please try again.", getApplicationContext());
                }
            });
        } else {

            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
            fetchTwitterEmail(getTwitterSession());
        }
    }

    public void customLoginTwitter(View view) {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch email only when permission is granted
                    fetchTwitterEmail(twitterSession);

                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    // Toast.makeText(MainActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                    //      CommonMethod.showToastShort("Failed to authenticate. Please try again.", getApplicationContext());
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
            fetchTwitterEmail(getTwitterSession());
        }
    }

    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
                //       CommonMethod.showToastShort("User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data, getApplicationContext());
                Log.d("AccountDetail", "" + twitterSession.getUserId() + "" + twitterSession.getUserName() + "" + result.data);
                requestParam(twitterSession.getUserName(), "", result.data, "", "" + twitterSession.getUserId(), "tw");
            }

            @Override
            public void failure(TwitterException exception) {

                CommonMethod.showToastShort("Failed to authenticate. Please try again.", getApplicationContext());

            }
        });
    }

    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        //NOTE : if you want to get token and secret too use uncomment the below code
//        TwitterAuthToken authToken = session.getAuthToken();
//        String token = authToken.token;
//        String secret = authToken.secret;

        return session;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the twitterAuthClient.
        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

}
