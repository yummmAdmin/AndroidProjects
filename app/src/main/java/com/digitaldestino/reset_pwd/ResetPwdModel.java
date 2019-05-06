package com.digitaldestino.reset_pwd;

import android.nfc.Tag;
import android.util.Log;

import com.digitaldestino.R;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.resetpwd.ResetPwdResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.CommonMethod;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ResetPwdModel implements ResetPwdContract.Model
{
    private final String TAG = "ResetPwdModel";
    @Override
    public void getResetPwdDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getResetPwdDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetPwdResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResetPwdResponse resetPwdResponse)
                    {

                        if (resetPwdResponse != null) {
                            String status = resetPwdResponse.getStatus();
                            String msg = resetPwdResponse.getMsg();
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
