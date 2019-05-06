package com.digitaldestino.restaurant_menu;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.get_restaurant_menu.MenuResponse;
import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GetSpecialModel implements GetSpecialContract.Model
{
    private static final String TAG="GetSpecialModel";

    @Override
    public void getRestaurantSpecialMenu(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getSpecialDish(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MenuResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MenuResponse menuResponse) {

                        Log.d("response", menuResponse.toString());
                        String status = menuResponse.getStatus();
                        String msg = menuResponse.getMsg();
                        String key = menuResponse.getKey();
                        ArrayList<ResDetial> resDetialArrayList = null;
                        if (status.equalsIgnoreCase("1")) {
                            resDetialArrayList = menuResponse.getResponse_data().getResDetial();
                        }
                        onFinishedListener.onFinished(status, msg, key, resDetialArrayList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
