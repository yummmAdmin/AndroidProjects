package com.digitaldestino.dishes_restaurentUI;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.fav_dishes.FavDishesResponse;
import com.digitaldestino.modelClass.near_restaurent.HomeData;
import com.digitaldestino.modelClass.near_restaurent.RestaurentResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FavDishModel implements FavDishContract.Model {
    private final String TAG = "FavDishModel";

    @Override
    public void getFavDish(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.favouriteDish(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FavDishesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FavDishesResponse favDishesResponse) {
                        if (favDishesResponse != null) {
                            String status = favDishesResponse.getStatus();
                            String msg = favDishesResponse.getMsg();
                            String key = favDishesResponse.getKey();
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
