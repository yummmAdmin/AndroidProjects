package com.digitaldestino.staticUI;

import com.digitaldestino.articledetailUI.ArticleDetailContract;
import com.digitaldestino.modelClass.static_pages.User;


import java.util.HashMap;

public interface PrivacyContract
{
    interface Model {

        interface OnFinishedListener {

            void onFailure(Throwable t);

            void onFinished(User user);
        }

        void getPrivacyDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(User user);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestPrivacyData(HashMap<String,String>param);
    }
}
