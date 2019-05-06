package com.digitaldestino.dishes_restaurentUI;

import java.util.HashMap;

public interface FavDishContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void getFavDish(OnFinishedListener onFinishedListener, HashMap<String, String> param);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataFav(String status, String msg, String key);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void requestFavDishes(HashMap<String, String> param);

    }
}
