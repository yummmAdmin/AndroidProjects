package com.digitaldestino.restaurant_info;

import com.digitaldestino.modelClass.follower.FollowerData;

import java.util.ArrayList;
import java.util.HashMap;

public interface FollowerContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<FollowerData> followerData,String restaurant_followers);

            void onFailure(Throwable t);
        }

        void follower(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setData(String status, String msg, String key, ArrayList<FollowerData> followerData,String restaurant_followers);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestFollower(HashMap<String, String> param);
    }
}
