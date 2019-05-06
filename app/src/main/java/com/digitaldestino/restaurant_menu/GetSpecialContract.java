package com.digitaldestino.restaurant_menu;

import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;

import java.util.ArrayList;
import java.util.HashMap;

public interface GetSpecialContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayLists);

            void onFailure(Throwable t);
        }

        void getRestaurantSpecialMenu(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToSpecial(String status, String msg, String key, ArrayList<ResDetial> detialArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestGetSpecialMenu(HashMap<String, String> param);
    }
}
