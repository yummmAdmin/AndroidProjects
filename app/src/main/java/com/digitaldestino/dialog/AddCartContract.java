package com.digitaldestino.dialog;
import java.util.HashMap;

public interface AddCartContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void getAddCart(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToCart(String status,String msg,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestAddCart(HashMap<String,String>param);
    }
}
