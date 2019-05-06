package com.digitaldestino.dishes_restaurentUI;

import com.digitaldestino.modelClass.near_restaurent.HomeData;

import java.util.ArrayList;
import java.util.HashMap;

public interface DishesRestaurentContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(ArrayList<HomeData> dishesArrayList);

            void onFailure(Throwable t);
        }

        void getDishesList(OnFinishedListener onFinishedListener, HashMap<String,String>param);

        // void getArticleList(OnFinishedListener onFinishedListener, int pageNo);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<HomeData> dishesArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer(HashMap<String,String>param);

    }
}
