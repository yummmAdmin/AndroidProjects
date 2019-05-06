package com.digitaldestino.loginUI;

import android.content.Intent;
import android.util.Log;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.login.LoginResponse;
import com.digitaldestino.modelClass.login.User;
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

public class LoginModel implements LoginContract.Model
{
    private final String TAG = "LoginModel";
    @Override
    public void getLoginDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getLoginDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if (loginResponse != null) {
                            String status = loginResponse.getStatus();
                            String msg = loginResponse.getMsg();
                            User user = null;
                            if(status.equalsIgnoreCase("1")) {
                                 user = loginResponse.getResponse_data().getUser();
                            }
                            onFinishedListener.onFinished(status,msg,user);
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
