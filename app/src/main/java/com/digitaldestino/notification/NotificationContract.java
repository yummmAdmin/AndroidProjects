package com.digitaldestino.notification;
import com.digitaldestino.modelClass.notification.NotificationsData;
import java.util.ArrayList;
import java.util.HashMap;

public interface NotificationContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<NotificationsData> notificationsData);

            void onFailure(Throwable t);
        }

        void getNotification(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(String status, String msg, String key, ArrayList<NotificationsData> notificationsData);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestForGetNotification(HashMap<String, String> param);
    }
}
