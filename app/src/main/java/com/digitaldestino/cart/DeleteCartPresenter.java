package com.digitaldestino.cart;

import com.digitaldestino.modelClass.delete_cart.AdditionalCharges;

import java.util.HashMap;

public class DeleteCartPresenter implements DeleteCartContract.Presenter, DeleteCartContract.Model.OnFinishedListener {
    private DeleteCartContract.View deleteCartView;
    private DeleteCartContract.Model deleteCartModel;

    public DeleteCartPresenter(DeleteCartContract.View deleteCartView) {
        this.deleteCartView = deleteCartView;
        deleteCartModel = new DeleteCartModel();
    }

    @Override
    public void onFinished(String status, String msg, String key,String cart_sum,String service_tax) {
        if (deleteCartView != null) {
            deleteCartView.hideProgress();
        }
        deleteCartView.setDeleteDataToView(status, msg, key,cart_sum,service_tax);
    }

    @Override
    public void onFailure(Throwable t) {
        deleteCartView.onResponseFailure(t);
        if (deleteCartView != null) {
            deleteCartView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        deleteCartView = null;
    }

    @Override
    public void requestDeleteCart(HashMap<String, String> param) {
        if (deleteCartView != null) {
            deleteCartView.showProgress();
        }
        deleteCartModel.getDeleteCart(this, param);
    }
}
