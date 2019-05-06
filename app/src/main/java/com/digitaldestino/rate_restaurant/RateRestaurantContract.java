package com.digitaldestino.rate_restaurant;
import java.util.HashMap;

public interface RateRestaurantContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String key);

            void onFailure(Throwable t);
        }

        void giveRating(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestGiveRating(HashMap<String,String>param);
    }
}
