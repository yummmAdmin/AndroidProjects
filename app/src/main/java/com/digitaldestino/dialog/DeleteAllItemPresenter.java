package com.digitaldestino.dialog;

import java.util.HashMap;

public class DeleteAllItemPresenter implements DeleteAllItemContract.Presenter, DeleteAllItemContract.Model.OnFinishedListener {
    private DeleteAllItemContract.View deleteAllCartView;
    private DeleteAllItemContract.Model deleteAllCartModel;

    public DeleteAllItemPresenter(DeleteAllItemContract.View deleteAllCartView) {
        this.deleteAllCartView = deleteAllCartView;
        this.deleteAllCartModel = new DeleteAllItemModel();
    }


    @Override
    public void onFinished(String status, String msg, String key) {
        if(deleteAllCartView != null){
            deleteAllCartView.hideProgress();
        }
        deleteAllCartView.deleteAllCart(status,msg,key);
    }

    @Override
    public void onFailure(Throwable t) {
        if(deleteAllCartView != null){
            deleteAllCartView.hideProgress();
        }
        deleteAllCartView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        deleteAllCartView=null;
    }

    @Override
    public void requestDeleteCart(HashMap<String, String> param) {
        if(deleteAllCartView != null){
            deleteAllCartView.showProgress();
        }
        deleteAllCartModel.deleteAllCart(this, param);
    }
}
