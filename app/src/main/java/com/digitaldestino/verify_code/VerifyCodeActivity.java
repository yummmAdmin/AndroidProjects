package com.digitaldestino.verify_code;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.forgotpassword.ForgotResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.reset_pwd.ResetPasswordActivity;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
public class VerifyCodeActivity extends AppCompatActivity implements View.OnClickListener, VerifyCodeContract.View {
    private Button btn_submit;
    private EditText edt_varification_code;
    private TextView txt_verify_code;
    private RelativeLayout hide_keyboard;
    private String Verify_Code, otp, Phone_number;
    private Intent intent;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private final String TAG = "VerifyCodeActivity";
    private VerifyCodePresenter verifyCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        findView();
    }

    private void findView() {
        intent = getIntent();
        otp = intent.getStringExtra("otp");
        Phone_number = intent.getStringExtra("Phone_number");
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        btn_submit = findViewById(R.id.btn_submit);
        edt_varification_code = findViewById(R.id.edt_varification_code);
        txt_verify_code = findViewById(R.id.txt_verify_code);
        hide_keyboard = findViewById(R.id.hide_keyboard);

        txt_verify_code.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        hide_keyboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: {
                CommonMethod.hideKeyBoard(VerifyCodeActivity.this);
                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                    intent.putExtra("Phone_number", Phone_number);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                } else {
                    CommonMethod.showAlertDialog(VerifyCodeActivity.this, result);
                }
                break;
            }

            case R.id.txt_verify_code: {
                if (isInternetPresent) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", Phone_number);
                    verifyCodePresenter = new VerifyCodePresenter(this);
                    verifyCodePresenter.getOtp(params);
                } else {
                    CommonMethod.showAlertDialog(VerifyCodeActivity.this, getString(R.string.internet_toast));
                }
                break;
            }

            case R.id.hide_keyboard: {
                CommonMethod.hideKeyBoard(VerifyCodeActivity.this);
                break;
            }
        }
    }

    private String validateForm() {
        Verify_Code = edt_varification_code.getText().toString().trim();
        if (Verify_Code.isEmpty() || Verify_Code.equals("")) {
            edt_varification_code.setFocusable(true);
            edt_varification_code.requestFocus();
            return getString(R.string.valid_code);
        }

        if (!Verify_Code.contentEquals(otp)) {
            return getString(R.string.valid_code);
        }

        return "success";
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, VerifyCodeActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String otpp) {
        CommonMethod.showToastShort(msg, getApplicationContext());
        if (status.equalsIgnoreCase("1")) {
            otp = otpp;
            //  CommonMethod.showToastShort(otp,getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
