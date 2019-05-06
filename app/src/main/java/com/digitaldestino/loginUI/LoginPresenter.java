package com.digitaldestino.loginUI;
import com.digitaldestino.articledetailUI.ArticleDetailContract;
import com.digitaldestino.articledetailUI.ArticleDetailModel;
import com.digitaldestino.modelClass.login.User;

import java.util.HashMap;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Model.OnFinishedListener {
    private LoginContract.View loginDetailView;
    private LoginContract.Model loginDetailsModel;

    public LoginPresenter(LoginContract.View loginDetailView) {
        this.loginDetailView = loginDetailView;
        this.loginDetailsModel = new LoginModel();
    }

    @Override
    public void onFinished(String status, String msg, User user) {
        if (loginDetailView != null) {
            loginDetailView.hideProgress();
        }
        loginDetailView.setDataToViews(user, status, msg);
    }

    @Override
    public void onFailure(Throwable t) {
        if (loginDetailView != null) {
            loginDetailView.hideProgress();
        }
        loginDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        loginDetailView = null;
    }

    @Override
    public void requestLoginData(HashMap<String, String> param) {
        if (loginDetailView != null) {
            loginDetailView.showProgress();
        }
        loginDetailsModel.getLoginDetails(this, param);
    }
}
