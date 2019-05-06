package com.digitaldestino.restaurant_menu;

import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public class GetNewPresenter implements GetNewContract.Presenter, GetNewContract.Model.OnFinishedListener {
    private GetNewContract.View getNewMenuView;
    private GetNewContract.Model getNewMenuModel;

    public GetNewPresenter(GetNewContract.View getNewMenuView) {
        this.getNewMenuView = getNewMenuView;
        this.getNewMenuModel = new GetNewModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayLists) {
        if (getNewMenuView != null) {
            getNewMenuView.hideProgress();
        }
        getNewMenuView.setDataToNew(status, msg, key, detialArrayLists);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getNewMenuView != null) {
            getNewMenuView.hideProgress();
        }
        getNewMenuView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getNewMenuView = null;
    }

    @Override
    public void requestGetNewMenu(HashMap<String, String> param) {
        if (getNewMenuView != null) {
            getNewMenuView.showProgress();
        }

        getNewMenuModel.getRestaurantNewMenu(this, param);
    }
}
