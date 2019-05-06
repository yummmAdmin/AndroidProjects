package com.digitaldestino.get_restaurant_review;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.get_feedback.FeedbackData;
import com.digitaldestino.modelClass.get_feedback.FeedbackDataCount;
import com.digitaldestino.modelClass.get_feedback.GetFeedbackResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GetReviewModel implements GetReviewContract.Model {
    private static final String TAG = "GetReviewModel";

    @Override
    public void getRestaurantFeedback(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getFeedback(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetFeedbackResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetFeedbackResponse getFeedbackResponse) {

                        Log.d("response", getFeedbackResponse.toString());
                        String status = getFeedbackResponse.getStatus();
                        String msg = getFeedbackResponse.getMsg();
                        String key = getFeedbackResponse.getKey();
                        ArrayList<FeedbackData> feedbackDataArrayList = null;
                        ArrayList<FeedbackDataCount> feedbackDataCountArrayList = null;
                        if (status.equalsIgnoreCase("1")) {
                            feedbackDataArrayList = getFeedbackResponse.getResponse_data().getFeedbackData();
                            feedbackDataCountArrayList = getFeedbackResponse.getResponse_data().getFeedbackDataCount();
                        }
                        onFinishedListener.onFinished(status, msg, key, feedbackDataArrayList, feedbackDataCountArrayList);

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
