package com.digitaldestino.payment;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.delete_card.DeleteCardResponse;
import com.digitaldestino.modelClass.pay.PaymentResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderPaymentModel implements OrderPaymentContract.Model
{
    private static final String TAG = "OrderPaymentModel";

    @Override
    public void orderPayment(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.orderPayment(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaymentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }
                    @Override
                    public void onNext(PaymentResponse paymentResponse) {
                        if (paymentResponse != null) {
                            String status = paymentResponse.getStatus();
                            String msg = paymentResponse.getMsg();
                            String key = paymentResponse.getKey();
                            onFinishedListener.onFinished(status, msg, key);
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
