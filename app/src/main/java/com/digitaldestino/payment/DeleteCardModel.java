package com.digitaldestino.payment;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.delete_card.DeleteCardResponse;
import com.digitaldestino.modelClass.get_card.Card_list;
import com.digitaldestino.modelClass.get_card.GetCardResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeleteCardModel implements DeleteCardContract.Model {
    private static final String TAG = "DeleteCardModel";

    @Override
    public void deleteCard(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.deleteCard(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteCardResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(DeleteCardResponse deleteCardResponse) {
                        if (deleteCardResponse != null) {
                            String status = deleteCardResponse.getStatus();
                            String msg = deleteCardResponse.getMsg();
                            String key = deleteCardResponse.getKey();

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
