package com.digitaldestino.menu_list;

import android.content.Intent;
import android.util.Log;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.logout.LogoutResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.Constants;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LogoutModel implements MenuListContract.Model
{
    private final String TAG="LogoutModel";

    @Override
    public void getLogoutDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getLogoutDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LogoutResponse logoutResponse) {
                        if (logoutResponse != null) {
                           String status = logoutResponse.getStatus();
                           String msg = logoutResponse.getMsg();
                           onFinishedListener.onFinished(status,msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
