package com.digitaldestino.search_activity;

import com.digitaldestino.modelClass.auto_complete.AutoCompleteData;
import com.digitaldestino.restaurant_info.RestaurantDetailContract;
import com.digitaldestino.restaurant_info.RestaurantDetailModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchPresenter implements SearchContract.Presenter, SearchContract.Model.OnFinishedListener {
    private SearchContract.View searchView;
    private SearchContract.Model searchModel;

    public SearchPresenter(SearchContract.View searchView) {
        this.searchView = searchView;
        this.searchModel = new SearchModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<AutoCompleteData> autoCompleteData) {
        if (searchView != null) {
            searchView.hideProgress();
        }
        searchView.setDataToViews(status, msg, key, autoCompleteData);
    }

    @Override
    public void onFailure(Throwable t) {
        if (searchView != null) {
            searchView.hideProgress();
        }
        searchView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        searchView = null;
    }

    @Override
    public void requestSearchDish(HashMap<String, String> param) {
        if (searchView != null) {
            searchView.showProgress();
        }
        searchModel.getSearchDish(this, param);
    }
}
