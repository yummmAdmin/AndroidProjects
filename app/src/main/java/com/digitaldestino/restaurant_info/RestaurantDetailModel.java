package com.digitaldestino.restaurant_info;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.restaurant_info.Details;
import com.digitaldestino.modelClass.restaurant_info.InfoResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RestaurantDetailModel implements RestaurantDetailContract.Model {
    private static final String TAG = "RestaurantDetailModel";

    @Override
    public void getRestaurantDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.restaurantDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InfoResponse infoResponse) {
                        if (infoResponse != null) {
                            String status = infoResponse.getStatus();
                            String msg = infoResponse.getMsg();
                            String key = infoResponse.getKey();
                            String user_following=infoResponse.getResponse_data().getUser_following();
                            ArrayList<Details> detailsArrayList = null;
                            if (status.equalsIgnoreCase("1")) {
                                detailsArrayList = infoResponse.getResponse_data().getDetails();
                            }
                            onFinishedListener.onFinished(status, msg, key, detailsArrayList,user_following);
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
