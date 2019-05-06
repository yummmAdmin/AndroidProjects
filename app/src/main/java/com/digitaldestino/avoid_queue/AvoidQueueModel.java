package com.digitaldestino.avoid_queue;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.addin_queue.AddQueueResponse;

import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AvoidQueueModel implements AvoidQueueContract.Model {
    private static final String TAG = "ScanQrModel";

    @Override
    public void orderAvoidQueue(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.addInQueue(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddQueueResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddQueueResponse addQueueResponse) {
                        if (addQueueResponse != null) {
                            String status = addQueueResponse.getStatus();
                            String msg = addQueueResponse.getMsg();
                            String key = addQueueResponse.getKey();
                            onFinishedListener.onFinished(status, msg, key);
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
