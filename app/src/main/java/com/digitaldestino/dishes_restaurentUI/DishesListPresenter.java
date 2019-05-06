package com.digitaldestino.dishes_restaurentUI;
import com.digitaldestino.modelClass.near_restaurent.HomeData;

import java.util.ArrayList;
import java.util.HashMap;

public class DishesListPresenter implements DishesRestaurentContract.Presenter, DishesRestaurentContract.Model.OnFinishedListener {
    private DishesRestaurentContract.View dishesListView;
    private DishesRestaurentContract.Model dishesListModel;

    public DishesListPresenter(DishesRestaurentContract.View dishesListView) {
        this.dishesListView = dishesListView;
        this.dishesListModel = new DishesListModel();
    }

    @Override
    public void onFinished(ArrayList<HomeData> articleDishesList) {
        dishesListView.setDataToRecyclerView(articleDishesList);
        if (dishesListView != null) {
            dishesListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        dishesListView.onResponseFailure(t);
        if (dishesListView != null) {
            dishesListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.dishesListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

    }

    @Override
    public void requestDataFromServer(HashMap<String, String> param) {
        if (dishesListView != null) {
            dishesListView.showProgress();
        }
        dishesListModel.getDishesList(this, param);
    }
}
