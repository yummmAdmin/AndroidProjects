package com.digitaldestino.loginUI;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.login.LoginResponse;
import com.digitaldestino.modelClass.login.User;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SocialLoginModel implements SocialContract.Model {
    private static final String TAG = "SocialLoginModel";

    @Override
    public void socailLoginDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.socialLogin(param).subscribeOn(Schedulers.io())
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
                            String key = loginResponse.getKey();
                            User user = null;
                            if (status.equalsIgnoreCase("1")) {
                                user = loginResponse.getResponse_data().getUser();
                            }
                            onFinishedListener.onFinished(status, msg, key, user);
                        }

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
