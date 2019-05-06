package com.digitaldestino.restaurant_menu;

import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public class GetSpecialPresenter implements GetSpecialContract.Presenter, GetSpecialContract.Model.OnFinishedListener {
    private GetSpecialContract.View getSpecialMenuView;
    private GetSpecialContract.Model getSpecialMenuModel;

    public GetSpecialPresenter(GetSpecialContract.View getSpecialMenuView) {
        this.getSpecialMenuView = getSpecialMenuView;
        this.getSpecialMenuModel = new GetSpecialModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayLists) {
        if (getSpecialMenuView != null) {
            getSpecialMenuView.hideProgress();
        }
        getSpecialMenuView.setDataToSpecial(status, msg, key, detialArrayLists);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getSpecialMenuView != null) {
            getSpecialMenuView.hideProgress();
        }
        getSpecialMenuView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getSpecialMenuView = null;
    }

    @Override
    public void requestGetSpecialMenu(HashMap<String, String> param) {
        if (getSpecialMenuView != null) {
            getSpecialMenuView.showProgress();
        }

        getSpecialMenuModel.getRestaurantSpecialMenu(this, param);
    }
}
