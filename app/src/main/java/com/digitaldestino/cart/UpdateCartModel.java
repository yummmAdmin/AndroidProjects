package com.digitaldestino.cart;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.update_cart.AdditionalCharges;
import com.digitaldestino.modelClass.update_cart.UpdateCartResponse;
import com.digitaldestino.modelClass.update_cart.UpdatecartData;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UpdateCartModel implements UpdateCartContract.Model {
    private final String TAG = "UpdateCartModel";

    @Override
    public void updateCart(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.updateCart(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateCartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateCartResponse updateCartResponse) {
                        if (updateCartResponse != null) {
                            String status = updateCartResponse.getStatus();
                            String msg = updateCartResponse.getMsg();
                            String key = updateCartResponse.getKey();
                            UpdatecartData updatecartData = null;
                            AdditionalCharges additionalCharges = null;
                            if (status.equalsIgnoreCase("1")) {
                                updatecartData = updateCartResponse.getResponse_data().getUpdatecartData();
                                additionalCharges=updateCartResponse.getResponse_data().getAdditionalCharges();
                            }
                            onFinishedListener.onFinished(status, msg, key, updatecartData, additionalCharges);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
