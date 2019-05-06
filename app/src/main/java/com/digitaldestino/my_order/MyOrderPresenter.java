package com.digitaldestino.my_order;

import com.digitaldestino.modelClass.my_order.BookingData;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderPresenter implements MyOrderContract.Presenter, MyOrderContract.Model.OnFinishedListener {
    private MyOrderContract.View myOrderView;
    private MyOrderContract.Model myOrderModel;

    public MyOrderPresenter(MyOrderContract.View myOrderView) {
        this.myOrderView = myOrderView;
        this.myOrderModel = new MyOrderModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<BookingData> bookingDataArrayList) {
        if (myOrderView != null) {
            myOrderView.hideProgress();
        }
        myOrderView.setDataToViews(status, msg, key, bookingDataArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        if (myOrderView != null) {
            myOrderView.hideProgress();
        }
        myOrderView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        myOrderView = null;
    }

    @Override
    public void requestMyOrder(HashMap<String, String> param) {
        if (myOrderView != null) {
            myOrderView.showProgress();
        }
        myOrderModel.getMyOrders(this, param);
    }
}
