package com.digitaldestino.avoid_queue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.localdatabase.DatabaseHelper;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.restaurant_info.RestaurentInfoActivity;
import com.digitaldestino.scan_qr.ScanQrActivity;
import com.digitaldestino.scan_qr.ScanQrPresenter;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.facebook.login.LoginManager;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AvoidQueueActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener, AvoidQueueContract.View {
    private ZXingScannerView mScannerView;
    private ImageView img_back,img_bookmark,img_notification;
    private String session_token, _id, order_timezone;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private AvoidQueuePresenter avoidQueuePresenter;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_avoid_queue);
        findView();
    }

    // initialize object
    private void findView() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String current_time = format.format(Calendar.getInstance().getTime());
        try {
            date = format.parse(current_time);
            //    CommonMethod.showToastShort("" + date.getTime(), getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order_timezone = TimeZone.getDefault().getID();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        _id = BMSPrefs.getString(AvoidQueueActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(AvoidQueueActivity.this, Constants.SESSION_TOKEN);
        mScannerView = findViewById(R.id.mscanner);
        img_back = findViewById(R.id.img_back);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // manage view listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.img_bookmark: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }
            case R.id.img_notification: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }
        }
    }

    // request payment  param
    private void requestParam(String restaurant_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("restaurant_id", restaurant_id);
        params.put("queue_date_time", "" + date.getTime());
        params.put("queue_timezone", order_timezone);
        Log.d("diningParam", params.toString());
        avoidQueuePresenter = new AvoidQueuePresenter(this);
        avoidQueuePresenter.requestForAvoidQueue(params);
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

    // handle backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    @Override
    public void handleResult(Result result) {
        if (isInternetPresent) {
            // if (result.getText().contains("restaurant_id") && !result.getText().contains("table_id"))
            if (result.getText().contains("restaurant_id") && !result.getText().contains("table_id")) {
                String currentString = result.getText();
                String[] separated = currentString.split("restaurant_id=");
                String restaurant_id = separated[1]; // this will contain "restaurant_id"
                BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_ID, restaurant_id);
                BMSPrefs.putString(getApplicationContext(), Constants.TABLE_ID, "");
                callActivity(new Intent(getApplicationContext(), RestaurentInfoActivity.class));
                //  requestParam(restaurant_id);
                //   CommonMethod.showToastShort(getString(R.string.barcode_scann), getApplicationContext());
            } else if (result.getText().contains("restaurant_id") && result.getText().contains("table_id")) {
                String currentString = result.getText();
                String[] separated = currentString.split("restaurant_id=");
                String data = separated[1]; // this will contain "restaurant_id"
                String[] separated1 = data.split("&&table_id=");
                String restaurant_id = separated1[0];
                String table_id = separated1[1];
                BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_ID, restaurant_id);
                BMSPrefs.putString(getApplicationContext(), Constants.TABLE_ID, table_id);
                callActivity(new Intent(getApplicationContext(), RestaurentInfoActivity.class));
                //  requestParam(restaurant_id);
            } else {
                CommonMethod.showToastShort("invalid bar code for dining", getApplicationContext());
                mScannerView.resumeCameraPreview(this);
            }

        } else {
            CommonMethod.showAlertDialog(AvoidQueueActivity.this, getString(R.string.internet_toast));
        }

        // If you would like to resume scanning, call this method below:

    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, AvoidQueueActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void orderForAvoidQueue(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addinqueue")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivity(new Intent(getApplicationContext(), RestaurentInfoActivity.class));
            //callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            LoginManager.getInstance().logOut();
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}
