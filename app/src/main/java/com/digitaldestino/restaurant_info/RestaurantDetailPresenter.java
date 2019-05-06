package com.digitaldestino.restaurant_info;

import com.digitaldestino.modelClass.restaurant_info.Details;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantDetailPresenter implements RestaurantDetailContract.Presenter, RestaurantDetailContract.Model.OnFinishedListener {
    private RestaurantDetailContract.View restaurantDetailView;
    private RestaurantDetailContract.Model restaurantDetailModel;

    public RestaurantDetailPresenter(RestaurantDetailContract.View restaurantDetailView) {
        this.restaurantDetailView = restaurantDetailView;
        this.restaurantDetailModel = new RestaurantDetailModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<Details> detailsArrayList, String user_following) {
        if (restaurantDetailView != null) {
            restaurantDetailView.hideProgress();
        }
        restaurantDetailView.setDataToViews(status, msg, key, detailsArrayList, user_following);
    }

    @Override
    public void onFailure(Throwable t) {
        if (restaurantDetailView != null) {
            restaurantDetailView.hideProgress();
        }
        restaurantDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        restaurantDetailView = null;
    }

    @Override
    public void requestRestaurantDetails(HashMap<String, String> param) {
        if (restaurantDetailView != null) {
            restaurantDetailView.showProgress();
        }
        restaurantDetailModel.getRestaurantDetails(this, param);
    }
}
