package com.digitaldestino.cart;
import java.util.HashMap;

public interface DeleteCartContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, String cart_sum,String service_tax);

            void onFailure(Throwable t);
        }

        void getDeleteCart(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDeleteDataToView(String status,String msg,String key,String cart_sum,String service_tax);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestDeleteCart(HashMap<String,String>param);
    }
}
