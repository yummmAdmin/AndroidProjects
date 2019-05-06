package com.digitaldestino.restaurant_info;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.follower.FollowerData;
import com.digitaldestino.modelClass.follower.FollowerResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FollowerModel implements FollowerContract.Model {
    private static final String TAG = "FollowerModel";

    @Override
    public void follower(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.followRestaurant(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FollowerResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FollowerResponse followerResponse) {
                        if (followerResponse != null) {

                            String status = followerResponse.getStatus();
                            String msg = followerResponse.getMsg();
                            String key = followerResponse.getKey();
                            String restaurant_followers=followerResponse.getResponse_data().getRestaurant_followers();
                            ArrayList<FollowerData> followerDataArrayList = null;
                            if (key.equalsIgnoreCase("follow")) {
                                followerDataArrayList = followerResponse.getResponse_data().getFollowerData();
                            }

                            onFinishedListener.onFinished(status, msg, key, followerDataArrayList,restaurant_followers);
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
