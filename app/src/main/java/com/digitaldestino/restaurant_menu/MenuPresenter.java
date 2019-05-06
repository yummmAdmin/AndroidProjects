package com.digitaldestino.restaurant_menu;
import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuPresenter implements MenuContract.Presenter, MenuContract.Model.OnFinishedListener {
    private MenuContract.View getMenuView;
    private MenuContract.Model getMenuModel;

    public MenuPresenter(MenuContract.View getMenuView) {
        this.getMenuView = getMenuView;
        this.getMenuModel = new MenuModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayList) {
        if (getMenuView != null) {
            getMenuView.hideProgress();
        }
        getMenuView.setDataToViews(status, msg, key, detialArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getMenuView != null) {
            getMenuView.hideProgress();
        }
        getMenuView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getMenuView = null;
    }

    @Override
    public void requestGetMenu(HashMap<String, String> param) {
        if (getMenuView != null) {
            getMenuView.showProgress();
        }

        getMenuModel.getRestaurantMenu(this, param);
    }
}
