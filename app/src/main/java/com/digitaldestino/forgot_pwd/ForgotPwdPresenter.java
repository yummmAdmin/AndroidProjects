package com.digitaldestino.forgot_pwd;

import com.digitaldestino.loginUI.LoginContract;
import com.digitaldestino.loginUI.LoginModel;

import java.util.HashMap;

public class ForgotPwdPresenter implements ForgotPwdContract.Presenter, ForgotPwdContract.Model.OnFinishedListener {
    private ForgotPwdContract.View forgotDetailView;
    private ForgotPwdContract.Model forgotDetailsModel;

    public ForgotPwdPresenter(ForgotPwdContract.View forgotDetailView) {
        this.forgotDetailView = forgotDetailView;
        this.forgotDetailsModel = new ForgotPwdModel();
    }
    @Override
    public void onFinished(String status, String msg, String otp) {
        if(forgotDetailView != null){
            forgotDetailView.hideProgress();
        }
        forgotDetailView.setDataToViews(status,msg,otp);
    }

    @Override
    public void onFailure(Throwable t) {
        if(forgotDetailView != null){
            forgotDetailView.hideProgress();
        }
        forgotDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        forgotDetailView=null;
    }

    @Override
    public void requestForgotPwd(HashMap<String, String> param) {
        if(forgotDetailView != null){
            forgotDetailView.showProgress();
        }
        forgotDetailsModel.getForgotPwdDetails(this, param);
    }
}
