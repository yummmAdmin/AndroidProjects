package com.digitaldestino.loginUI;

import com.digitaldestino.modelClass.login.User;

import java.util.HashMap;

public interface SocialContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, User user);

            void onFailure(Throwable t);
        }

        void socailLoginDetails(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setSocialData(String status, String msg, String key, User user);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestSocialLoginData(HashMap<String, String> param);
    }
}
