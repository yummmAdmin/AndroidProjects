package com.digitaldestino.verify_code;
import java.util.HashMap;

public interface VerifyCodeContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String otp);

            void onFailure(Throwable t);
        }

        void getCodeVerify(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String otp);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void getOtp(HashMap<String,String>param);
    }
}
