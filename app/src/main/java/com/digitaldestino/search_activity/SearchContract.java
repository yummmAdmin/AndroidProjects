package com.digitaldestino.search_activity;

import com.digitaldestino.modelClass.auto_complete.AutoCompleteData;

import java.util.ArrayList;
import java.util.HashMap;

public interface SearchContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<AutoCompleteData> autoCompleteData);

            void onFailure(Throwable t);
        }

        void getSearchDish(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key, ArrayList<AutoCompleteData> autoCompleteData);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestSearchDish(HashMap<String, String> param);
    }
}
