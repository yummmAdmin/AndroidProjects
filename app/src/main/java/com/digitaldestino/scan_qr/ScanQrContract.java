package com.digitaldestino.scan_qr;

import java.util.HashMap;

public interface ScanQrContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void orderForDininig(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void orderForDininig(String status, String msg, String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestForDininig(HashMap<String, String> param);
    }
}
