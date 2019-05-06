package com.digitaldestino.add_card;

import java.util.HashMap;

public interface AddCardContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String key);

            void onFailure(Throwable t);
        }

        void addCard(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestAddCard(HashMap<String,String>param);
    }
}
