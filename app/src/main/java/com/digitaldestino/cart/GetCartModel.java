package com.digitaldestino.cart;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
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

public class GetCartModel implements GetCartContract.Model {
    private final String TAG = "GetCartModel";

    @Override
    public void getCartList(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getCart(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetCartResponse getCartResponse) {
                        if (getCartResponse != null) {
                            String status = getCartResponse.getStatus();
                            String msg = getCartResponse.getMsg();
                            String key = getCartResponse.getKey();
                            ArrayList<CartData> cartDataArrayList = null;
                            String cart_sum = "";
                            String service_tax="";
                            if (status.equalsIgnoreCase("1")) {
                                cartDataArrayList = getCartResponse.getResponse_data().getCartData();
                                cart_sum = getCartResponse.getResponse_data().getAdditionalCharges().getCart_sum();
                                service_tax=getCartResponse.getResponse_data().getAdditionalCharges().getService_tax();
                            }
                            //   Log.d(TAG, "Number of cart data received: " + cartDataArrayList.size());
                            onFinishedListener.onFinished(cartDataArrayList, status, msg, key, cart_sum,service_tax);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                 //       Log.d(TAG,throwable.getMessage());
                        onFinishedListener.onFailure(throwable);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
