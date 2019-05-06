package com.digitaldestino.send_feedback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.HashMap;

public class SendFeedbackActivity extends AppCompatActivity implements View.OnClickListener, SendFeedbackContract.View {
    private ImageView img_back, img_bookmark, img_notification;
    private EditText edt_feedback;
    private Button btn_submit;
    private String Feedback, session_token, _id, user_email;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private static final String TAG = "SendFeedbackActivity";
    private SendFeedbackPresenter sendFeedbackPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        findView();
    }

    // to initialize object here...
    private void findView() {
        session_token = BMSPrefs.getString(SendFeedbackActivity.this, Constants.SESSION_TOKEN);
        _id = BMSPrefs.getString(SendFeedbackActivity.this, Constants._ID);
        user_email = BMSPrefs.getString(SendFeedbackActivity.this, Constants.USER_EMAIL);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        img_back = findViewById(R.id.img_back);
        edt_feedback = findViewById(R.id.edt_feedback);
        btn_submit = findViewById(R.id.btn_submit);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_submit: {
                CommonMethod.hideKeyBoard(SendFeedbackActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        requestParam();
                    } else {
                        CommonMethod.showAlertDialog(SendFeedbackActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(SendFeedbackActivity.this, result);
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

    // apply validation here...
    private String validateForm() {
        Feedback = edt_feedback.getText().toString().trim();
        if (Feedback.isEmpty() || Feedback.equals("")) {
            edt_feedback.setFocusable(true);
            edt_feedback.requestFocus();
            return getString(R.string.valid_share_feedback);
        }
        return "success";
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    // request param for api
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("feedback", Feedback);
        params.put("email", user_email);
        Log.d("param", params.toString());
        sendFeedbackPresenter = new SendFeedbackPresenter(this);
        sendFeedbackPresenter.requestSendFeedback(params);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, SendFeedbackActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("appFeedback")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
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
