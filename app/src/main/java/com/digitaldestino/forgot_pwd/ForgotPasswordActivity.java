package com.digitaldestino.forgot_pwd;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.digitaldestino.R;
import com.digitaldestino.verify_code.VerifyCodeActivity;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, ForgotPwdContract.View {
    private EditText edt_phn_number;
    private Button btn_submit;
    private RelativeLayout hide_keyboard, relative_verify_code, relative_forgot_pass;
    private String Phone_number, otp;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private Intent intent;
    private ProgressDialog progressDialog;
    private Dialog dialogMsg;
    private ForgotPwdPresenter forgotPwdPresenter;
    HashMap<String, String> params = new HashMap<>();
    private final String TAG = "ForgotPwdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findView();
    }

    private void findView() {
        connectionDetector = new ConnectionDetector(getApplicationContext());
        edt_phn_number = findViewById(R.id.edt_phn_number);
        btn_submit = findViewById(R.id.btn_submit);
        hide_keyboard = findViewById(R.id.hide_keyboard);
        relative_verify_code = findViewById(R.id.relative_verify_code);
        relative_forgot_pass = findViewById(R.id.relative_forgot_pass);
        btn_submit.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: {
                CommonMethod.hideKeyBoard(ForgotPasswordActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        params.put("mobile", Phone_number);
                        forgotPwdPresenter = new ForgotPwdPresenter(this);
                        forgotPwdPresenter.requestForgotPwd(params);
                    } else {
                        CommonMethod.showAlertDialog(ForgotPasswordActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(ForgotPasswordActivity.this, result);
                }
                break;
            }
            case R.id.hide_keyboard: {
                CommonMethod.hideKeyBoard(ForgotPasswordActivity.this);
                break;
            }
        }
    }

    private String validateForm() {
        Phone_number = edt_phn_number.getText().toString().trim();
        if (Phone_number.isEmpty() || Phone_number.equals("") || Phone_number.length() < 6) {
            edt_phn_number.setFocusable(true);
            edt_phn_number.requestFocus();
            return getString(R.string.valid_phone);
        }
        return "success";
    }


    // backpress method
    @Override
    public void onBackPressed() {
        if (relative_forgot_pass.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else if (relative_verify_code.getVisibility() == View.VISIBLE) {
            relative_verify_code.setVisibility(View.GONE);
            relative_verify_code.animate();
            relative_forgot_pass.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        }
    }


    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, ForgotPasswordActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String otpp) {
        if (status.equalsIgnoreCase("0")) {
            relative_verify_code.setVisibility(View.VISIBLE);
            relative_forgot_pass.setVisibility(View.GONE);
        } else {
            otp = otpp;
            openDialouge();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    //dialog open on button click
    private void openDialouge() {
        dialogMsg = new Dialog(ForgotPasswordActivity.this, R.style.DialogAnimation);
        dialogMsg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMsg.setCancelable(true);
        dialogMsg.setContentView(R.layout.popup);
        dialogMsg.getWindow().setBackgroundDrawableResource(R.color.background_color);
        Window window = dialogMsg.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialogMsg.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        ImageView img_close = dialogMsg.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMsg.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("Phone_number", Phone_number);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_left,
                                R.anim.anim_slide_out_left);
                        dialogMsg.getWindow().setBackgroundDrawableResource(R.color.white);
                        //    dialogMsg.dismiss();
                    }
                }, 1000);
            }

        });

        dialogMsg.show();
    }

}
