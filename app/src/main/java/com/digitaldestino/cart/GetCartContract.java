package com.digitaldestino.cart;

import com.digitaldestino.modelClass.get_cart.CartData;

import java.util.ArrayList;
import java.util.HashMap;

public interface GetCartContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(ArrayList<CartData> getCartArrayList, String status, String msg, String key, String cart_sum, String service_tax);

            void onFailure(Throwable t);
        }

        void getCartList(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<CartData> getCartArrayList, String status, String msg, String key, String cart_sum, String service_tax);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestgetCartList(HashMap<String, String> param);
    }
}
