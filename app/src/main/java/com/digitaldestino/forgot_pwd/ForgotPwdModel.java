package com.digitaldestino.forgot_pwd;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import com.digitaldestino.R;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.forgotpassword.ForgotResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.CommonMethod;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForgotPwdModel implements ForgotPwdContract.Model {
    private final String TAG = "ForgotPwdModel";
    @Override
    public void getForgotPwdDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getForgotPassDetails(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForgotResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ForgotResponse forgotResponse) {
                        if (forgotResponse != null) {
                            String status = forgotResponse.getStatus();
                            String msg = forgotResponse.getMsg();
                            String otp = "";
                            if (status.equalsIgnoreCase("1")) {
                                otp = forgotResponse.getResponse_data().getOTP();
                            }
                            onFinishedListener.onFinished(status, msg, otp);
                        } else {
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.d(TAG, e.getMessage());
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }
}
