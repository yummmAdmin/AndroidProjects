package com.digitaldestino.dialog;

import java.util.HashMap;

public interface DeleteAllItemContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void deleteAllCart(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void deleteAllCart(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestDeleteCart(HashMap<String,String>param);
    }
}
