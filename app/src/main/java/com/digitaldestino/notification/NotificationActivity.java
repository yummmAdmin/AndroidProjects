package com.digitaldestino.notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.adapter.NotificationAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.modelClass.notification.NotificationsData;
import com.digitaldestino.scan_qr.ScanQrActivity;
import com.digitaldestino.scan_qr.ScanQrPresenter;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, NotificationContract.View {
    private ImageView img_back;
    private RecyclerView recycler_notification;
    private NotificationAdapter notificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private String session_token, seekerId;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ArrayList<NotificationsData> notificationsDataArrayList = new ArrayList<>();
    private static final String TAG = "NotificationActivity";
    private NotificationPresenter notificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(NotificationActivity.this, getString(R.string.internet_toast));
        }

    }

    private void findView() {
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        session_token = BMSPrefs.getString(NotificationActivity.this, Constants.SESSION_TOKEN);
        seekerId = BMSPrefs.getString(NotificationActivity.this, Constants.Seeker_id);
        img_back = findViewById(R.id.img_back);
        recycler_notification = findViewById(R.id.recycler_notification);
        img_back.setOnClickListener(this);
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

    // set adapter
    private void setAdapter() {
        notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationsDataArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_notification.setLayoutManager(linearLayoutManager);
        recycler_notification.setAdapter(notificationAdapter);
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // request payment  param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seekerId", seekerId);
        Log.d("notificationParam", params.toString());
        notificationPresenter = new NotificationPresenter(this);
        notificationPresenter.requestForGetNotification(params);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, NotificationActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToRecyclerView(String status, String msg, String key, ArrayList<NotificationsData> notificationsData) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("notifications")) {
            notificationsDataArrayList = notificationsData;
            setAdapter();
        }
        else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        }
        else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
