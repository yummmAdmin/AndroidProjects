package com.digitaldestino.notification;


import com.digitaldestino.modelClass.notification.NotificationsData;
import com.digitaldestino.scan_qr.ScanQrContract;
import com.digitaldestino.scan_qr.ScanQrModel;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationPresenter implements NotificationContract.Presenter, NotificationContract.Model.OnFinishedListener {
    private NotificationContract.View notificationView;
    private NotificationContract.Model notificationModel;

    public NotificationPresenter(NotificationContract.View notificationView) {
        this.notificationView = notificationView;
        this.notificationModel = new NotificationModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<NotificationsData> notificationsData) {
        if (notificationView != null) {
            notificationView.hideProgress();
        }
        notificationView.setDataToRecyclerView(status, msg, key, notificationsData);
    }

    @Override
    public void onFailure(Throwable t) {
        if (notificationView != null) {
            notificationView.hideProgress();
        }

        notificationView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        notificationView = null;
    }

    @Override
    public void requestForGetNotification(HashMap<String, String> param) {
        if (notificationView != null) {
            notificationView.showProgress();
        }
        notificationModel.getNotification(this, param);
    }
}
