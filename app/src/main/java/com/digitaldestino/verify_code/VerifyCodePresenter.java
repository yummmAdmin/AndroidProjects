package com.digitaldestino.verify_code;
import com.digitaldestino.change_pwd.ChangePwdContract;
import com.digitaldestino.change_pwd.ChangePwdModel;

import java.util.HashMap;

public class VerifyCodePresenter implements VerifyCodeContract.Presenter,VerifyCodeContract.Model.OnFinishedListener
{
    private VerifyCodeContract.View verifyCodeView;
    private VerifyCodeContract.Model verifyCodeModel;

    public VerifyCodePresenter(VerifyCodeContract.View verifyCodeView) {
        this.verifyCodeView = verifyCodeView;
        this.verifyCodeModel = new VerifyCodeModel();
    }
    @Override
    public void onFinished(String status, String msg, String otp) {
        if (verifyCodeView != null) {
            verifyCodeView.hideProgress();
        }
        verifyCodeView.setDataToViews(status, msg,otp);
    }

    @Override
    public void onFailure(Throwable t) {
        if (verifyCodeView != null) {
            verifyCodeView.hideProgress();
        }
        verifyCodeView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        verifyCodeView=null;
    }

    @Override
    public void getOtp(HashMap<String, String> param) {
        if (verifyCodeView != null) {
            verifyCodeView.showProgress();
        }
        verifyCodeModel.getCodeVerify(this, param);
    }
}
