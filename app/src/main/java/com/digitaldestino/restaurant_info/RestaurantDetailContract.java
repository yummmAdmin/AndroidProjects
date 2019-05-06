package com.digitaldestino.restaurant_info;
import com.digitaldestino.modelClass.restaurant_info.Details;

import java.util.ArrayList;
import java.util.HashMap;

public interface RestaurantDetailContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<Details> detailsArrayList,String user_following);

            void onFailure(Throwable t);
        }

        void getRestaurantDetails(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key, ArrayList<Details> detailsArrayList, String user_following);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestRestaurantDetails(HashMap<String, String> param);
    }
}
