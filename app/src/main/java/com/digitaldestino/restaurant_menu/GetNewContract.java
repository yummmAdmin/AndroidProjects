package com.digitaldestino.restaurant_menu;

import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public interface GetNewContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayLists);

            void onFailure(Throwable t);
        }

        void getRestaurantNewMenu(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToNew(String status, String msg, String key, ArrayList<ResDetial> detialArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestGetNewMenu(HashMap<String, String> param);
    }
}
