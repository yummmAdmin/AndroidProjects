package com.digitaldestino.dishes_detail;

import com.digitaldestino.modelClass.dishes_detail.DishDetial;

import java.util.HashMap;

public interface DishesDetailContract
{
    interface Model {

        interface OnFinishedListener {
         void onFinished(String status, String msg, String key,DishDetial dishDetial);

            void onFailure(Throwable t);
        }

        void getDishDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String key,DishDetial dishDetial);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestDishDetail(HashMap<String,String>param);
    }
}
