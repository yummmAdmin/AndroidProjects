package com.digitaldestino.change_pwd;

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
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.login.LoginResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, ChangePwdContract.View {
    private EditText edt_old_pass, edt_new_pass, edt_confirm_pass;
    private ImageView img_back,img_bookmark,img_notification;
    private Button btn_save;
    private String Old_Password, New_Password, Confirm_Password, session_token;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private ChangePwdPresenter changePwdPresenter;
    private final String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        findView();
    }

    private void findView() {
        session_token = BMSPrefs.getString(ChangePasswordActivity.this, Constants.SESSION_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        edt_old_pass = findViewById(R.id.edt_old_pass);
        edt_new_pass = findViewById(R.id.edt_new_pass);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        btn_save = findViewById(R.id.btn_save);
        img_back = findViewById(R.id.img_back);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save: {
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("session_token", session_token);
                        params.put("oldpass", Old_Password);
                        params.put("newpass", New_Password);
                        changePwdPresenter = new ChangePwdPresenter(this);
                        changePwdPresenter.requestChangePwd(params);
                    } else {
                        CommonMethod.showAlertDialog(ChangePasswordActivity.this, getString(R.string.internet_toast));
                    }

                } else {
                    CommonMethod.showAlertDialog(ChangePasswordActivity.this, result);
                }
                break;
            }
            case R.id.img_back: {
                onBackPressed();
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

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    private String validateForm() {
        Old_Password = edt_old_pass.getText().toString().trim();
        New_Password = edt_new_pass.getText().toString().trim();
        Confirm_Password = edt_confirm_pass.getText().toString().trim();
        if (Old_Password.equals("") || Old_Password.isEmpty()) {
            edt_old_pass.setFocusable(true);
            edt_old_pass.requestFocus();
            return getString(R.string.valid_old_pass);
        }

        if (New_Password.equals("") || New_Password.isEmpty()) {
            edt_new_pass.setFocusable(true);
            edt_new_pass.requestFocus();
            return getString(R.string.valid_new_password);
        }

        if (!CommonMethod.isValidPassword(New_Password)) {
            edt_new_pass.setFocusable(true);
            edt_new_pass.requestFocus();
            return getString(R.string.valid_password);
        }

        if (New_Password.contentEquals(Old_Password)) {
            edt_new_pass.setFocusable(true);
            edt_new_pass.requestFocus();
            return getString(R.string.old_new);
        }

        if (Confirm_Password.isEmpty()) {
            edt_confirm_pass.setFocusable(true);
            edt_confirm_pass.requestFocus();
            return getString(R.string.valid_password_blank);
        }

        if (!Confirm_Password.contentEquals(New_Password)) {
            edt_confirm_pass.setFocusable(true);
            edt_confirm_pass.requestFocus();
            return getString(R.string.same_password);
        }
        return "success";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, ChangePasswordActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("change_password")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), EditProfileActivity.class));
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
