package com.digitaldestino.add_card;

import com.digitaldestino.change_pwd.ChangePwdContract;
import com.digitaldestino.change_pwd.ChangePwdModel;

import java.util.HashMap;

public class AddCardPresenter implements AddCardContract.Presenter, AddCardContract.Model.OnFinishedListener
{
    private AddCardContract.View addCardView;
    private AddCardContract.Model addCardModel;

    public AddCardPresenter(AddCardContract.View addCardView) {
        this.addCardView = addCardView;
        this.addCardModel = new AddCardModel();
    }
    @Override
    public void onFinished(String status, String msg, String key) {
        if (addCardView != null) {
            addCardView.hideProgress();
        }
        addCardView.setDataToViews(status,msg,key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (addCardView != null) {
            addCardView.hideProgress();
        }
        addCardView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        addCardView=null;
    }

    @Override
    public void requestAddCard(HashMap<String, String> param) {
        if (addCardView != null) {
            addCardView.showProgress();
        }
        addCardModel.addCard(this, param);
    }

}
