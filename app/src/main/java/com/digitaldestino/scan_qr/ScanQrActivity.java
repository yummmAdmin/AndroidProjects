package com.digitaldestino.scan_qr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrActivity extends AppCompatActivity implements View.OnClickListener, ZXingScannerView.ResultHandler, ScanQrContract.View {
    private ImageView img_back, img_bookmark, img_notification;
    private EditText edt_table_no, edt_restaurant_id;
    private Button btn_booktable;
    private String tableNumber, restaurantId, session_token, _id, order_timezone, seekerId, table_id, discount = "";
    private ZXingScannerView mScannerView;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private ScanQrPresenter scanQrPresenter;
    private Date date;
    private String latitude = "0", longitude = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_qr);
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
        latitude = BMSPrefs.getString(ScanQrActivity.this, Constants.LATITUDE);
        longitude = BMSPrefs.getString(ScanQrActivity.this, Constants.LONGITUDE);

        seekerId = BMSPrefs.getString(ScanQrActivity.this, Constants.Seeker_id);
        discount = BMSPrefs.getString(ScanQrActivity.this, Constants.DISCOUNT);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        order_timezone = TimeZone.getDefault().getID();
        _id = BMSPrefs.getString(ScanQrActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(ScanQrActivity.this, Constants.SESSION_TOKEN);
        edt_table_no = findViewById(R.id.edt_table_no);
        edt_restaurant_id = findViewById(R.id.edt_restaurant_id);
        img_back = findViewById(R.id.img_back);
        btn_booktable = findViewById(R.id.btn_booktable);
        mScannerView = findViewById(R.id.mscanner);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        img_bookmark.setOnClickListener(this);
        btn_booktable.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // view listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_booktable: {
                CommonMethod.hideKeyBoard(ScanQrActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    if (isInternetPresent) {
                        requestParam();
                    } else {
                        CommonMethod.showAlertDialog(ScanQrActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(ScanQrActivity.this, result);
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

    // validation
    private String validateForm() {
        tableNumber = edt_table_no.getText().toString().trim();
        restaurantId = edt_restaurant_id.getText().toString().trim();
        if (tableNumber.isEmpty() || tableNumber.equals("")) {
            edt_table_no.setFocusable(true);
            edt_table_no.requestFocus();
            return getString(R.string.valid_table_no);
        }

//        if (restaurantId.equals("") || restaurantId.isEmpty()) {
//            edt_restaurant_id.setFocusable(true);
//            edt_restaurant_id.requestFocus();
//            return getString(R.string.valid_res_id);
//        }

        return "success";
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    // request payment  param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("seekerId", seekerId);
        params.put("card_id", "");
        params.put("stripe_customer_id", "");
        params.put("order_timezone", order_timezone);
        params.put("order_date_time", "" + date.getTime());
        params.put("order_address", "N/A");
        params.put("order_type", "dinein");
        params.put("table_id", tableNumber);
        params.put("discount", discount);
        params.put("drop_lat", "0");
        params.put("drop_lng", "0");
        Log.d("diningParam", params.toString());
        scanQrPresenter = new ScanQrPresenter(this);
        scanQrPresenter.requestForDininig(params);
    }

    //backpress
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

    @Override
    public void handleResult(Result result) {
        if (result.getText().contains("table_id")) {
            String currentString = result.getText();
            String[] separated = currentString.split("table_id=");
            table_id = separated[1];
            edt_table_no.setText(table_id);
            CommonMethod.showToastShort(getString(R.string.barcode_scann), getApplicationContext());
        } else {
            CommonMethod.showToastShort("Invalid bar code for dining", getApplicationContext());
            // If you would like to resume scanning, call this method below:
            mScannerView.resumeCameraPreview(this);
        }
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
        progressDialog = CommonMethod.showProgressDialog(progressDialog, ScanQrActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void orderForDininig(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("booknow")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}
