package com.digitaldestino.points;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.delete_card.DeleteCardResponse;
import com.digitaldestino.modelClass.getLoyalty.GetLoyaltyResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GetLoyaltyModel implements GetLoyaltyContract.Model{
        private static final String TAG = "GetLoyaltyModel";

        @Override
        public void getLoyalty(OnFinishedListener onFinishedListener, HashMap<String, String> param) {
            Retrofit retrofit = RetrofitClient.getRetrofitClient();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            apiInterface.getLoyality(param).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetLoyaltyResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(GetLoyaltyResponse getLoyaltyResponse) {
                            if (getLoyaltyResponse != null) {
                                String status = getLoyaltyResponse.getStatus();
                                String msg = getLoyaltyResponse.getMsg();
                                String key = getLoyaltyResponse.getKey();
                                String loyalty = getLoyaltyResponse.getResponse_data().getLoyalityPoints();

                                onFinishedListener.onFinished(status, msg, key, loyalty);
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
