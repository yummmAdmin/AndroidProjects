package com.digitaldestino.restaurant_menu;
import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;
import java.util.ArrayList;
import java.util.HashMap;

public interface MenuContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<ResDetial> detialArrayList);

            void onFailure(Throwable t);
        }

        void getRestaurantMenu(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key, ArrayList<ResDetial> detialArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestGetMenu(HashMap<String, String> param);
    }
}
