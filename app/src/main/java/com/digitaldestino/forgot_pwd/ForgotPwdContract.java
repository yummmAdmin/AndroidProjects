package com.digitaldestino.forgot_pwd;
import java.util.HashMap;

public interface ForgotPwdContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String otp);

            void onFailure(Throwable t);
        }

        void getForgotPwdDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String otp);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestForgotPwd(HashMap<String,String>param);
    }
}
