package com.digitaldestino.payment;

import java.util.HashMap;

public class OrderPaymentPresenter implements OrderPaymentContract.Presenter, OrderPaymentContract.Model.OnFinishedListener {
    private OrderPaymentContract.View orderPaymentView;
    private OrderPaymentContract.Model orderPaymentModel;

    public OrderPaymentPresenter(OrderPaymentContract.View orderPaymentView) {
        this.orderPaymentView = orderPaymentView;
        this.orderPaymentModel = new OrderPaymentModel();
    }
    @Override
    public void onFinished(String status, String msg, String key) {
        if (orderPaymentView != null) {
            orderPaymentView.hideProgress();
        }
        orderPaymentView.orderPaymentResponse(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (orderPaymentView != null) {
            orderPaymentView.hideProgress();
        }
        orderPaymentView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        orderPaymentView=null;
    }

    @Override
    public void requestOrderPyament(HashMap<String, String> param) {
        if (orderPaymentView != null) {
            orderPaymentView.showProgress();
        }
        orderPaymentModel.orderPayment(this, param);
    }
}
