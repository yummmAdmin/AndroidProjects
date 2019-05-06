package com.digitaldestino.payment;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.get_card.Card_list;
import com.digitaldestino.modelClass.get_card.GetCardResponse;
import com.digitaldestino.modelClass.resetpwd.ResetPwdResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PaymentModel implements PaymentContract.Model
{
    private static final String TAG="PaymentModel";

    @Override
    public void getCardDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getCard(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCardResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GetCardResponse getCardResponse)
                    {
                        if (getCardResponse != null) {
                            String status = getCardResponse.getStatus();
                            String msg = getCardResponse.getMsg();
                            String key = getCardResponse.getKey();
                            ArrayList<Card_list>cardListArrayList=null;
                            if(status.equalsIgnoreCase("1"))
                            {
                                cardListArrayList=getCardResponse.getResponse_data().getCard_list();
                            }
                            onFinishedListener.onFinished(status,msg,key,cardListArrayList);
                        }
                    }

                    @Override

                    public void onError(Throwable e) {
                        Log.d(TAG,e.getMessage());
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
