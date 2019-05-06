package com.digitaldestino.cart;

import com.digitaldestino.modelClass.update_cart.AdditionalCharges;
import com.digitaldestino.modelClass.update_cart.UpdatecartData;

import java.util.HashMap;

public class UpdateCartPresenter implements UpdateCartContract.Presenter, UpdateCartContract.Model.OnFinishedListener
{
    private UpdateCartContract.View updateCartView;
    private UpdateCartContract.Model updateCartModel;

    public UpdateCartPresenter(UpdateCartContract.View updateCartView) {
        this.updateCartView = updateCartView;
        updateCartModel = new UpdateCartModel();
    }
    @Override
    public void onFinished(String status, String msg, String key, UpdatecartData updatecartData, AdditionalCharges additionalCharges) {
        if (updateCartView != null) {
            updateCartView.hideProgress();
        }
        updateCartView.setUpdateCartItem(status,msg,key,updatecartData,additionalCharges);
    }

    @Override
    public void onFailure(Throwable t) {
        updateCartView.onResponseFailure(t);
        if (updateCartView != null) {
            updateCartView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        updateCartView=null;
    }

    @Override
    public void requestUpdateCart(HashMap<String, String> param) {
        if (updateCartView != null) {
            updateCartView.showProgress();
        }
        updateCartModel.updateCart(this,param);
    }
}
