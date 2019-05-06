package com.digitaldestino.send_feedback;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.getuser.UserResponse;
import com.digitaldestino.modelClass.send_feedback.FeedbackResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SendFeedbackModel implements SendFeedbackContract.Model
{
    private static final String TAG="SendFeedbackModel";
    @Override
    public void sendFeedback(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.sendFeedback(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedbackResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FeedbackResponse feedbackResponse) {
                        if (feedbackResponse != null) {
                            String status = feedbackResponse.getStatus();
                            String msg = feedbackResponse.getMsg();
                            String key=feedbackResponse.getKey();
                            onFinishedListener.onFinished(status, msg,key);

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
