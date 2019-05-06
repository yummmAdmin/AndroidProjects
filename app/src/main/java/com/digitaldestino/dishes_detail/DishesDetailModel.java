package com.digitaldestino.dishes_detail;
import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.dishes_detail.DishDetial;
import com.digitaldestino.modelClass.dishes_detail.DishesDetailResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DishesDetailModel implements DishesDetailContract.Model {
    private final String TAG = "DishesDetailModel";

    @Override
    public void getDishDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getDishesDetail(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DishesDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DishesDetailResponse dishesDetailResponse) {
                        if (dishesDetailResponse != null) {
                            String status = dishesDetailResponse.getStatus();
                            String msg = dishesDetailResponse.getMsg();
                            String key=dishesDetailResponse.getKey();
                            DishDetial dishDetial = null;
                            if(status.equalsIgnoreCase("1")) {
                                dishDetial = dishesDetailResponse.getResponse_data().getDishDetial();
                            }
                            onFinishedListener.onFinished(status,msg,key,dishDetial);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,e.getMessage());
                    }

                    @Override
                    public void onComplete()
                    {

                    }

                });
    }
}
