package com.digitaldestino.reedem_point;

import com.digitaldestino.reset_pwd.ResetPwdContract;
import com.digitaldestino.reset_pwd.ResetPwdModel;

import java.util.HashMap;

public class ReedemPresenter implements ReedemContract.Presenter, ReedemContract.Model.OnFinishedListener {
    private ReedemContract.View reedemView;
    private ReedemContract.Model reedemModel;

    public ReedemPresenter(ReedemContract.View reedemView) {
        this.reedemView = reedemView;
        this.reedemModel = new ReedemModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (reedemView != null) {
            reedemView.hideProgress();
        }
        reedemView.setDataToViews(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (reedemView != null) {
            reedemView.hideProgress();
        }
        reedemView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        reedemView = null;
    }

    @Override
    public void requestAddInLoyality(HashMap<String, String> param) {
        if (reedemView != null) {
            reedemView.showProgress();
        }
        reedemModel.getAddInLoyality(this, param);
    }
}
