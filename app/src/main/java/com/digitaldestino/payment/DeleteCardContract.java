package com.digitaldestino.payment;

import com.digitaldestino.modelClass.get_card.Card_list;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeleteCardContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg,String key);

            void onFailure(Throwable t);
        }

        void deleteCard(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void deleteDataFromView(String status, String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestDeleteCard(HashMap<String, String> param);
    }
}
