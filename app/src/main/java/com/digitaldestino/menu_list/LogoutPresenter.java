package com.digitaldestino.menu_list;

import com.digitaldestino.loginUI.LoginContract;
import com.digitaldestino.loginUI.LoginModel;

import java.util.HashMap;

public class LogoutPresenter implements MenuListContract.Presenter, MenuListContract.Model.OnFinishedListener {
    private MenuListContract.View logoutView;
    private MenuListContract.Model logoutModel;

    public LogoutPresenter(MenuListContract.View logoutView) {
        this.logoutView = logoutView;
        this.logoutModel = new LogoutModel();
    }

    @Override
    public void onFinished(String status, String msg) {
        if (logoutView != null) {
            logoutView.hideProgress();
        }
        logoutView.setDataToViews(status, msg);
    }

    @Override
    public void onFailure(Throwable t) {
        if (logoutView != null) {
            logoutView.hideProgress();
        }
        logoutView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        logoutView=null;
    }

    @Override
    public void logoutFromServer(HashMap<String, String> param) {
        if (logoutView != null) {
            logoutView.showProgress();
        }
        logoutModel.getLogoutDetails(this, param);
    }
}
