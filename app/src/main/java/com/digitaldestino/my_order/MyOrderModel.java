package com.digitaldestino.my_order;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.my_order.BookingData;
import com.digitaldestino.modelClass.my_order.Items;
import com.digitaldestino.modelClass.my_order.OrderResponse;
import com.digitaldestino.modelClass.pay.PaymentResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyOrderModel implements MyOrderContract.Model {
    private final String TAG = "MyOrderModel";

    @Override
    public void getMyOrders(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getOrder(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onNext(OrderResponse orderResponse) {
                        if (orderResponse != null) {
                            String status = orderResponse.getStatus();
                            String msg = orderResponse.getMsg();
                            String key = orderResponse.getKey();
                            ArrayList<BookingData>bookingDataArrayList=null;
                            if(status.equalsIgnoreCase("1"))
                            {
                                bookingDataArrayList=orderResponse.getResponse_data().getBookingData();
                            }
                            onFinishedListener.onFinished(status, msg, key,bookingDataArrayList);
                        }
                    }

                    @Override

                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
