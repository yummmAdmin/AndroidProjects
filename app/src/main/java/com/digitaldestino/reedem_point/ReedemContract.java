package com.digitaldestino.reedem_point;

import com.digitaldestino.reset_pwd.ResetPwdContract;

import java.util.HashMap;

public interface ReedemContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void getAddInLoyality(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestAddInLoyality(HashMap<String, String> param);
    }

}
