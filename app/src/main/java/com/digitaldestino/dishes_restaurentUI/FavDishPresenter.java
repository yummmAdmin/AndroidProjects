package com.digitaldestino.dishes_restaurentUI;

import java.util.HashMap;

public class FavDishPresenter implements FavDishContract.Presenter, FavDishContract.Model.OnFinishedListener {
    private FavDishContract.View favDishView;
    private FavDishContract.Model favDishModel;

    public FavDishPresenter(FavDishContract.View favDishView) {
        this.favDishView = favDishView;
        this.favDishModel = new FavDishModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        favDishView.setDataFav(status, msg, key);
        if (favDishView != null) {
            favDishView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        favDishView.onResponseFailure(t);
        if (favDishView != null) {
            favDishView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        favDishView = null;
    }

    @Override
    public void requestFavDishes(HashMap<String, String> param) {
        if (favDishView != null) {
            favDishView.showProgress();
        }
        favDishModel.getFavDish(this, param);
    }
}
