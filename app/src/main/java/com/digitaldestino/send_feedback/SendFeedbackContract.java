package com.digitaldestino.send_feedback;


import java.util.HashMap;

public interface SendFeedbackContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key);

            void onFailure(Throwable t);
        }

        void sendFeedback(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestSendFeedback(HashMap<String, String> param);
    }
}
