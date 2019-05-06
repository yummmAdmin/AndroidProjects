package com.digitaldestino.reset_pwd;
import java.util.HashMap;

public class ResetPwdPresenter implements ResetPwdContract.Presenter, ResetPwdContract.Model.OnFinishedListener {
    private ResetPwdContract.View resetDetailView;
    private ResetPwdContract.Model resetDetailsModel;

    public ResetPwdPresenter(ResetPwdContract.View resetDetailView) {
        this.resetDetailView = resetDetailView;
        this.resetDetailsModel = new ResetPwdModel();
    }

    @Override
    public void onFinished(String status, String msg) {
        if (resetDetailView != null) {
            resetDetailView.hideProgress();
        }
        resetDetailView.setDataToViews(status, msg);
    }

    @Override
    public void onFailure(Throwable t) {
        if (resetDetailView != null) {
            resetDetailView.hideProgress();
        }
        resetDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        resetDetailView = null;
    }

    @Override
    public void requestResetPwd(HashMap<String, String> param) {
        if (resetDetailView != null) {
            resetDetailView.showProgress();
        }
        resetDetailsModel.getResetPwdDetails(this, param);
    }
}
