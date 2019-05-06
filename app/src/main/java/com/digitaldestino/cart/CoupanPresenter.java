package com.digitaldestino.cart;

import com.digitaldestino.modelClass.apply_coupan.Promocode;

import java.util.HashMap;

public class CoupanPresenter implements CoupanContract.Presenter, CoupanContract.Model.OnFinishedListener {
    private CoupanContract.View coupanView;
    private CoupanContract.Model coupanModel;

    public CoupanPresenter(CoupanContract.View coupanView) {
        this.coupanView = coupanView;
        coupanModel = new CoupanModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, Promocode promocode) {
        if (coupanView != null) {
            coupanView.hideProgress();
        }
        coupanView.setDataToCoupan(status, msg, key, promocode);
    }

    @Override
    public void onFailure(Throwable t) {
        coupanView.onResponseFailure(t);
        if (coupanView != null) {
            coupanView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        coupanView = null;
    }

    @Override
    public void requestApplyCoupan(HashMap<String, String> param) {
        if (coupanView != null) {
            coupanView.showProgress();
        }
        coupanModel.getCoupan(this, param);
    }
}
