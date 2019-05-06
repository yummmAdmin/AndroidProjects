package com.digitaldestino.payment;

import java.util.HashMap;

public interface OrderPaymentContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void orderPayment(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void orderPaymentResponse(String status, String msg, String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestOrderPyament(HashMap<String, String> param);
    }
}
