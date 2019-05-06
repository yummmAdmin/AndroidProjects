package com.digitaldestino.points;

import com.digitaldestino.payment.OrderPaymentContract;

import java.util.HashMap;

public interface GetLoyaltyContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key,String loyalityData);

            void onFailure(Throwable t);
        }

        void getLoyalty(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToView(String status, String msg, String key,String loyalityData);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestGetLoyalty(HashMap<String, String> param);
    }
}
