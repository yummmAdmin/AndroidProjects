package com.digitaldestino.payment;

import java.util.HashMap;

public class DeleteCardPresenter implements DeleteCardContract.Presenter, DeleteCardContract.Model.OnFinishedListener {
    private DeleteCardContract.View deleteCardView;
    private DeleteCardContract.Model deleteCardModel;

    public DeleteCardPresenter(DeleteCardContract.View deleteCardView) {
        this.deleteCardView = deleteCardView;
        this.deleteCardModel = new DeleteCardModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (deleteCardView != null) {
            deleteCardView.hideProgress();
        }
        deleteCardView.deleteDataFromView(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (deleteCardView != null) {
            deleteCardView.hideProgress();
        }
        deleteCardView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        deleteCardView = null;
    }

    @Override
    public void requestDeleteCard(HashMap<String, String> param) {
        if (deleteCardView != null) {
            deleteCardView.showProgress();
        }
        deleteCardModel.deleteCard(this, param);
    }
}
