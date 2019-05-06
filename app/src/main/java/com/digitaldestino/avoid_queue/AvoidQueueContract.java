package com.digitaldestino.avoid_queue;

import com.digitaldestino.scan_qr.ScanQrContract;

import java.util.HashMap;

public interface AvoidQueueContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void orderAvoidQueue(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void orderForAvoidQueue(String status, String msg, String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestForAvoidQueue(HashMap<String, String> param);
    }
}
