package com.digitaldestino.dialog;

import com.digitaldestino.dishes_detail.DishesDetailContract;
import com.digitaldestino.dishes_detail.DishesDetailModel;

import java.util.HashMap;

public class AddCartPresenter implements AddCartContract.Presenter, AddCartContract.Model.OnFinishedListener {
    private AddCartContract.View addCartView;
    private AddCartContract.Model addCartModel;

    public AddCartPresenter(AddCartContract.View addCartView) {
        this.addCartView = addCartView;
        this.addCartModel = new AddCartModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if(addCartView != null){
            addCartView.hideProgress();
        }
        addCartView.setDataToCart(status,msg,key);
    }

    @Override
    public void onFailure(Throwable t) {
        if(addCartView != null){
            addCartView.hideProgress();
        }
        addCartView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        addCartView=null;
    }

    @Override
    public void requestAddCart(HashMap<String, String> param) {
        if(addCartView != null){
            addCartView.showProgress();
        }
        addCartModel.getAddCart(this, param);
    }
}
