package com.digitaldestino.cart;

import com.digitaldestino.modelClass.apply_coupan.Promocode;
import java.util.HashMap;

public interface CoupanContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key,Promocode promocode);

            void onFailure(Throwable t);
        }

        void getCoupan(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToCoupan(String status,String msg,String key,Promocode promocode);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestApplyCoupan(HashMap<String,String>param);
    }
}
