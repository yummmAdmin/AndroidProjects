package com.digitaldestino.book_table;

import java.util.HashMap;

public interface BookTableContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String key);

            void onFailure(Throwable t);
        }

        void getBookTableDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestBookTable(HashMap<String,String>param);
    }
}
