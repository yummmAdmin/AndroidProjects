package com.digitaldestino.loginUI;

import com.digitaldestino.modelClass.login.User;

import java.util.HashMap;

public class SocialLoginPresenter implements SocialContract.Presenter, SocialContract.Model.OnFinishedListener {
    private SocialContract.View socialLoginView;
    private SocialContract.Model socialLoginModel;

    public SocialLoginPresenter(SocialContract.View socialLoginView) {
        this.socialLoginView = socialLoginView;
        this.socialLoginModel = new SocialLoginModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, User user) {
        if (socialLoginView != null) {
            socialLoginView.hideProgress();
        }
        socialLoginView.setSocialData(status, msg, key, user);
    }

    @Override
    public void onFailure(Throwable t) {
        if (socialLoginView != null) {
            socialLoginView.hideProgress();
        }
        socialLoginView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        socialLoginView = null;
    }

    @Override
    public void requestSocialLoginData(HashMap<String, String> param) {
        if (socialLoginView != null) {
            socialLoginView.showProgress();
        }
        socialLoginModel.socailLoginDetails(this, param);
    }
}
