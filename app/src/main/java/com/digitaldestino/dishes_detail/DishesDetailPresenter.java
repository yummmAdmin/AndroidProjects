package com.digitaldestino.dishes_detail;
import com.digitaldestino.modelClass.dishes_detail.DishDetial;

import java.util.HashMap;

public class DishesDetailPresenter implements DishesDetailContract.Presenter, DishesDetailContract.Model.OnFinishedListener {
    private DishesDetailContract.View dishDetailView;
    private DishesDetailContract.Model dishDetailsModel;

    public DishesDetailPresenter(DishesDetailContract.View dishDetailView) {
        this.dishDetailView = dishDetailView;
        this.dishDetailsModel = new DishesDetailModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, DishDetial dishDetial) {
        if(dishDetailView != null){
            dishDetailView.hideProgress();
        }
        dishDetailView.setDataToViews(status,msg,key,dishDetial);
    }

    @Override
    public void onFailure(Throwable t) {
        if(dishDetailView != null){
            dishDetailView.hideProgress();
        }
        dishDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        dishDetailView=null;
    }

    @Override
    public void requestDishDetail(HashMap<String, String> param) {
        if(dishDetailView != null){
            dishDetailView.showProgress();
        }
        dishDetailsModel.getDishDetails(this, param);
    }
}
