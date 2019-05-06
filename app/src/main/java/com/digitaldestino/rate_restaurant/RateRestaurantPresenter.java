package com.digitaldestino.rate_restaurant;

import com.digitaldestino.edit_profile.DeleteProfileModel;
import com.digitaldestino.edit_profile.GetProfileContract;

import java.util.HashMap;

public class RateRestaurantPresenter implements RateRestaurantContract.Presenter, RateRestaurantContract.Model.OnFinishedListener {
    private RateRestaurantContract.View rateRestaurantView;
    private RateRestaurantContract.Model rateRestaurantModel;

    public RateRestaurantPresenter(RateRestaurantContract.View rateRestaurantView) {
        this.rateRestaurantView = rateRestaurantView;
        this.rateRestaurantModel = new RateRestaurantModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (rateRestaurantView != null) {
            rateRestaurantView.hideProgress();
        }
        rateRestaurantView.setDataToViews(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (rateRestaurantView != null) {
            rateRestaurantView.hideProgress();
        }
        rateRestaurantView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        rateRestaurantView = null;
    }

    @Override
    public void requestGiveRating(HashMap<String, String> param) {
        if (rateRestaurantView != null) {
            rateRestaurantView.showProgress();
        }
        rateRestaurantModel.giveRating(this, param);
    }
}
