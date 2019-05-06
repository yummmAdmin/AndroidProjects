package com.digitaldestino.restaurant_menu;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.menu_list.MenuListContract;
import com.digitaldestino.modelClass.get_feedback.FeedbackData;
import com.digitaldestino.modelClass.get_feedback.FeedbackDataCount;
import com.digitaldestino.modelClass.get_feedback.GetFeedbackResponse;
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

public class MenuModel implements MenuContract.Model {
    private static final String TAG = "MenuModel";

    @Override
    public void getRestaurantMenu(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getMenu(param).subscribeOn(Schedulers.io())
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
