package com.digitaldestino.restaurant_menu;

import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public class GetPopularPresenter implements GetPopularContract.Presenter, GetPopularContract.Model.OnFinishedListener {
    private GetPopularContract.View getPopularMenuView;
    private GetPopularContract.Model getPopularMenuModel;

    public GetPopularPresenter(GetPopularContract.View getPopularMenuView) {
        this.getPopularMenuView = getPopularMenuView;
        this.getPopularMenuModel = new GetPopularModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayLists) {
        if (getPopularMenuView != null) {
            getPopularMenuView.hideProgress();
        }
        getPopularMenuView.setDataToPopular(status, msg, key, detialArrayLists);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getPopularMenuView != null) {
            getPopularMenuView.hideProgress();
        }
        getPopularMenuView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getPopularMenuView = null;
    }

    @Override
    public void requestGetPopularMenu(HashMap<String, String> param) {
        if (getPopularMenuView != null) {
            getPopularMenuView.showProgress();
        }
        getPopularMenuModel.getRestaurantPopularMenu(this, param);
    }
}
