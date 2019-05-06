package com.digitaldestino.cart;
import com.digitaldestino.modelClass.update_cart.AdditionalCharges;
import com.digitaldestino.modelClass.update_cart.UpdatecartData;

import java.util.ArrayList;
import java.util.HashMap;

public interface UpdateCartContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, UpdatecartData updatecartData, AdditionalCharges additionalCharges);

            void onFailure(Throwable t);
        }

        void updateCart(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setUpdateCartItem(String status, String msg, String key, UpdatecartData updatecartData, AdditionalCharges additionalCharges );

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestUpdateCart(HashMap<String,String>param);
    }
}
