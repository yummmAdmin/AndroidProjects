package com.digitaldestino.loginUI;


import com.digitaldestino.modelClass.login.User;

import java.util.HashMap;

public interface LoginContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,User user);

            void onFailure(Throwable t);
        }

        void getLoginDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(User user,String status,String msg);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestLoginData(HashMap<String,String>param);
    }
}
