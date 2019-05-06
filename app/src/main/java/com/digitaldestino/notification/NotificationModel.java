package com.digitaldestino.notification;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.notification.NotificationResponse;
import com.digitaldestino.modelClass.notification.NotificationsData;
import com.digitaldestino.modelClass.pay.PaymentResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NotificationModel implements NotificationContract.Model {
    private static final String TAG = "NotificationModel";

    @Override
    public void getNotification(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getNotifications(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NotificationResponse notificationResponse) {
                        if (notificationResponse != null) {
                            String status = notificationResponse.getStatus();
                            String msg = notificationResponse.getMsg();
                            String key = notificationResponse.getKey();
                            ArrayList<NotificationsData> notificationsData = null;
                            if (status.equalsIgnoreCase("1")) {
                                notificationsData = notificationResponse.getResponse_data().getNotificationsData();
                            }
                            onFinishedListener.onFinished(status, msg, key, notificationsData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
