package com.digitaldestino.reedem_point;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.avoid_queue.AvoidQueueActivity;
import com.digitaldestino.my_order.MyOrderActivity;
import com.digitaldestino.my_order.MyOrderPresenter;
import com.digitaldestino.restaurant_info.RestaurentInfoActivity;
import com.digitaldestino.scan_qr.ScanQrActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ReedemPointActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener, ReedemContract.View {
    private ZXingScannerView mScannerView;
    private ImageView img_back;
    private String session_token, order_timezone;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private ReedemPresenter reedemPresenter;
    private Date date;
    private final String TAG = "ReedemPointActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reedem_point);
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
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        session_token = BMSPrefs.getString(ReedemPointActivity.this, Constants.SESSION_TOKEN);
        order_timezone = TimeZone.getDefault().getID();
        mScannerView = findViewById(R.id.mscanner);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    // api call
    private void requestParam(String restaurant_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("restaurant_id", restaurant_id);
        params.put("loyality_date_time", "" + date.getTime());
        params.put("loyality_timezone", order_timezone);
        Log.d("param", params.toString());
        reedemPresenter = new ReedemPresenter(this);
        reedemPresenter.requestAddInLoyality(params);
    }

    // qr code  handle
    @Override
    public void handleResult(Result result) {
        if (isInternetPresent) {
            // if (result.getText().contains("restaurant_id") && !result.getText().contains("table_id"))
            if (result.getText().contains("restaurant_id") && result.getText().contains("loyality")) {
                String currentString = result.getText();
                String[] separated = currentString.split("restaurant_id=");
                String data = separated[1]; // this will contain "restaurant_id"
                String[] separated1 = data.split("&&loyality");
                String restaurant_id = separated1[0];
                if (restaurant_id.equalsIgnoreCase(BMSPrefs.getString(getApplicationContext(), Constants.RESTAURANT_ID))) {
                    requestParam(restaurant_id);
                } else {
                    CommonMethod.showToastShort("Restaurent does not match", getApplicationContext());
                    mScannerView.resumeCameraPreview(this);
                }
            } else {
                CommonMethod.showToastShort("invalid bar code", getApplicationContext());
                mScannerView.resumeCameraPreview(this);
            }

        } else {
            CommonMethod.showAlertDialog(ReedemPointActivity.this, getString(R.string.internet_toast));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, ReedemPointActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addinloyality")) {
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
        Log.d(TAG, throwable.getMessage());
    }
}
