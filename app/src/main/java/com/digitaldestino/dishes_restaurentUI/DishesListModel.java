package com.digitaldestino.dishes_restaurentUI;

import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
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

public class DishesListModel implements  DishesRestaurentContract.Model
{
    private final String TAG = "DishesListModel";
    @Override
    public void getDishesList(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getRestaurentDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RestaurentResponse restaurentResponse) {
                        if (restaurentResponse != null) {
                           String status = restaurentResponse.getStatus();
                           String msg = restaurentResponse.getMsg();
                            if (status.equalsIgnoreCase("1")) {
                                ArrayList<HomeData> userArrayList  = restaurentResponse.getResponse_data().getHomeData();
                                Log.d(TAG, "Number of articles received: " + userArrayList.size());
                                onFinishedListener.onFinished(userArrayList);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.d("DishesListModel",e.getMessage());
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
