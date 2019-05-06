package com.digitaldestino.payment;
import com.digitaldestino.modelClass.get_card.Card_list;
import com.digitaldestino.reset_pwd.ResetPwdContract;
import com.digitaldestino.reset_pwd.ResetPwdModel;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentPresenter implements PaymentContract.Presenter, PaymentContract.Model.OnFinishedListener
{
    private PaymentContract.View getCardView;
    private PaymentContract.Model getCardModel;

    public PaymentPresenter(PaymentContract.View getCardView) {
        this.getCardView = getCardView;
        this.getCardModel = new PaymentModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<Card_list> card_list) {
        if (getCardView != null) {
            getCardView.hideProgress();
        }
        getCardView.setDataToViews(status, msg,key,card_list);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getCardView != null) {
            getCardView.hideProgress();
        }
        getCardView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getCardView=null;
    }

    @Override
    public void requestCardList(HashMap<String, String> param) {
        if (getCardView != null) {
            getCardView.showProgress();
        }
        getCardModel.getCardDetails(this, param);
    }
}
