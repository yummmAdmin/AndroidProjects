package com.digitaldestino.articleUI;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.modelClass.article.BaseResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.CommonMethod;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ArticleListModel implements ArticleListContract.Model {
    private final String TAG = "ArticleListModel";

    @Override
    public void getArticleList(final OnFinishedListener onFinishedListener) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getArticleList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse != null) {
                            ArrayList<Article> articles = baseResponse.getResponse_data().getArticle();
                            Log.d(TAG, "Number of articles received: " + articles.size());
                            onFinishedListener.onFinished(articles);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                        onFinishedListener.onFailure(throwable);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
