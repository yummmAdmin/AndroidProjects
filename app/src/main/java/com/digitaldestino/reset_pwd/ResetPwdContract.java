package com.digitaldestino.reset_pwd;

import java.util.HashMap;

public interface ResetPwdContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg);

            void onFailure(Throwable t);
        }

        void getResetPwdDetails(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestResetPwd(HashMap<String, String> param);
    }
}
