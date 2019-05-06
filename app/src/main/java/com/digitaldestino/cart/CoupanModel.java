package com.digitaldestino.cart;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.apply_coupan.ApplyCoupanResponse;
import com.digitaldestino.modelClass.apply_coupan.Promocode;
import com.digitaldestino.modelClass.delete_cart.DeleteCartResponse;
import com.digitaldestino.modelClass.get_cart.CartData;
import com.digitaldestino.modelClass.get_cart.GetCartResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CoupanModel implements CoupanContract.Model {
    private final String TAG = "CoupanModel";


    @Override
    public void getCoupan(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.checkPromocode(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyCoupanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApplyCoupanResponse applyCoupanResponse) {
                        if (applyCoupanResponse != null) {
                            String status = applyCoupanResponse.getStatus();
                            String msg = applyCoupanResponse.getMsg();
                            String key = applyCoupanResponse.getKey();
                            Promocode promocode = null;

                            if (status.equalsIgnoreCase("1")) {
                                promocode = applyCoupanResponse.getResponse_data().getPromocode();
                            }
                            onFinishedListener.onFinished(status, msg, key, promocode);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage());
                        onFinishedListener.onFailure(throwable);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
