package com.digitaldestino.change_pwd;
import java.util.HashMap;

public interface ChangePwdContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String key);

            void onFailure(Throwable t);
        }

        void getChangePwdDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestChangePwd(HashMap<String,String>param);
    }
}
