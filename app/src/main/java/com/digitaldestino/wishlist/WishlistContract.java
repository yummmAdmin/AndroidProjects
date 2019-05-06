package com.digitaldestino.wishlist;

import com.digitaldestino.modelClass.get_favourite.FavData;
import com.digitaldestino.send_feedback.SendFeedbackContract;

import java.util.ArrayList;
import java.util.HashMap;

public interface WishlistContract
{
    interface Model {
        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<FavData> favData);

            void onFailure(Throwable t);
        }

        void getFavourites(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecycler(String status, String msg, String key, ArrayList<FavData> favData);

        void onResponseFailure(Throwable throwable);
        }


    interface Presenter {
        void onDestroy();

        void requestGetFavourites(HashMap<String, String> param);
    }
}
