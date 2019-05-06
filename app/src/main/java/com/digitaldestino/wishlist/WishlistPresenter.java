package com.digitaldestino.wishlist;

import com.digitaldestino.modelClass.get_favourite.FavData;

import java.util.ArrayList;
import java.util.HashMap;

public class WishlistPresenter implements WishlistContract.Presenter, WishlistContract.Model.OnFinishedListener {
    private WishlistContract.View getFavView;
    private WishlistContract.Model getFavModel;

    public WishlistPresenter(WishlistContract.View getFavView) {
        this.getFavView = getFavView;
        this.getFavModel = new WishlistModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<FavData> favData) {
        if (getFavView != null) {
            getFavView.hideProgress();
        }
        getFavView.setDataToRecycler(status, msg, key, favData);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getFavView != null) {
            getFavView.hideProgress();
        }
        getFavView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getFavView = null;
    }

    @Override
    public void requestGetFavourites(HashMap<String, String> param) {
        if (getFavView != null) {
            getFavView.showProgress();
        }
        getFavModel.getFavourites(this, param);
    }
}
