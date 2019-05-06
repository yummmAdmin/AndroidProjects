package com.digitaldestino.points;

import com.digitaldestino.payment.OrderPaymentContract;
import com.digitaldestino.payment.OrderPaymentModel;

import java.util.HashMap;

public class GetLoyaltyPresenter implements GetLoyaltyContract.Presenter, GetLoyaltyContract.Model.OnFinishedListener {

    private GetLoyaltyContract.View getLoyaltyView;
    private GetLoyaltyContract.Model getLoyaltyModel;

    public GetLoyaltyPresenter(GetLoyaltyContract.View getLoyaltyView) {
        this.getLoyaltyView = getLoyaltyView;
        this.getLoyaltyModel = new GetLoyaltyModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, String loyalityData) {
        if (getLoyaltyView != null) {
            getLoyaltyView.hideProgress();
        }
        getLoyaltyView.setDataToView(status, msg, key, loyalityData);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getLoyaltyView != null) {
            getLoyaltyView.hideProgress();
        }
        getLoyaltyView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getLoyaltyView = null;
    }

    @Override
    public void requestGetLoyalty(HashMap<String, String> param) {
        if (getLoyaltyView != null) {
            getLoyaltyView.showProgress();
        }
        getLoyaltyModel.getLoyalty(this, param);
    }
}
