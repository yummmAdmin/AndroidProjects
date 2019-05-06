package com.digitaldestino.change_pwd;
import java.util.HashMap;

public class ChangePwdPresenter implements ChangePwdContract.Presenter, ChangePwdContract.Model.OnFinishedListener {
    private ChangePwdContract.View changePwdView;
    private ChangePwdContract.Model changePwdModel;

    public ChangePwdPresenter(ChangePwdContract.View changePwdView) {
        this.changePwdView = changePwdView;
        this.changePwdModel = new ChangePwdModel();
    }

    @Override
    public void onFinished(String status, String msg,String key) {
        if (changePwdView != null) {
            changePwdView.hideProgress();
        }
        changePwdView.setDataToViews(status, msg,key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (changePwdView != null) {
            changePwdView.hideProgress();
        }
        changePwdView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        changePwdView = null;
    }

    @Override
    public void requestChangePwd(HashMap<String, String> param) {
        if (changePwdView != null) {
            changePwdView.showProgress();
        }
        changePwdModel.getChangePwdDetails(this, param);
    }
}
