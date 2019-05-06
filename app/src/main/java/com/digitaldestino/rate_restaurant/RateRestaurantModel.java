package com.digitaldestino.rate_restaurant;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.getuser.UserResponse;
import com.digitaldestino.modelClass.rating_response.RatingResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RateRestaurantModel implements RateRestaurantContract.Model {
    private static final String TAG = "RateRestaurantModel";

    @Override
    public void giveRating(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.rateRestaurant(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RatingResponse RatingResponse) {
                        Log.d("response", RatingResponse.toString());
                        String status = RatingResponse.getStatus();
                        String msg = RatingResponse.getMsg();
                        String key = RatingResponse.getKey();
                        onFinishedListener.onFinished(status, msg, key);
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
