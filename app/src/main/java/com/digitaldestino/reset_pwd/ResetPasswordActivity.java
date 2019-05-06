package com.digitaldestino.reset_pwd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.digitaldestino.R;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.loginUI.LoginContract;
import com.digitaldestino.modelClass.resetpwd.ResetPwdResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, ResetPwdContract.View {
    private EditText edt_new_pass, edt_confirm_pass;
    private Button btn_submit;
    private RelativeLayout hide_keyboard;
    private String New_Password, Confirm_Password, Phone_number;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private Intent intent;
    private ProgressDialog progressDialog;
    private HashMap<String, String> params = new HashMap<>();
    private ResetPwdPresenter resetPwdPresenter;
    private final String TAG = "ResetPwdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        findView();
    }

    // to initialize object here...
    private void findView() {
        intent = getIntent();
        Phone_number = intent.getStringExtra("Phone_number");
        connectionDetector = new ConnectionDetector(getApplicationContext());
        edt_new_pass = findViewById(R.id.edt_new_pass);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        hide_keyboard = findViewById(R.id.hide_keyboard);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_submit: {
                CommonMethod.hideKeyBoard(ResetPasswordActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        params.put("mobile", Phone_number);
                        params.put("password", New_Password);
                        resetPwdPresenter = new ResetPwdPresenter(this);
                        resetPwdPresenter.requestResetPwd(params);
                    } else {
                        CommonMethod.showAlertDialog(ResetPasswordActivity.this, getString(R.string.internet_toast));
                    }

                } else {
                    CommonMethod.showAlertDialog(ResetPasswordActivity.this, result);
                }
                break;
            }
            case R.id.hide_keyboard: {
                CommonMethod.hideKeyBoard(ResetPasswordActivity.this);
                break;
            }
        }
    }


    //apply form validation here...
    private String validateForm() {
        New_Password = edt_new_pass.getText().toString().trim();
        Confirm_Password = edt_confirm_pass.getText().toString().trim();
        if (New_Password.isEmpty() || !CommonMethod.isValidPassword(New_Password)) {
            edt_new_pass.setFocusable(true);
            edt_new_pass.requestFocus();
            return getString(R.string.valid_password);
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


    private void callActivity() {
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, ResetPasswordActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg) {
        CommonMethod.showToastShort(msg, getApplicationContext());
        if (status.equalsIgnoreCase("1")) {
            callActivity();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
