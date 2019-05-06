package com.digitaldestino.articledetailUI;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleListContract;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.modelClass.article.BaseResponse;
import com.digitaldestino.modelClass.articleDetail.ArticleDetailResponse;
import com.digitaldestino.modelClass.articleDetail.User;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ArticleDetailModel implements ArticleDetailContract.Model {
    private final String TAG = "ArticleDetailModel";

    @Override
    public void getArticleDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getArticleDetail(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDetailResponse articleDetailResponse) {
                        Log.d("responsedata", articleDetailResponse.toString());
                        if (articleDetailResponse != null) {
                            User user = articleDetailResponse.getResponse_data().getUser();
                            onFinishedListener.onFinished(user);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage());

                    }

                    @Override
                    public void onComplete()
                    {

                    }

                });
    }
}
