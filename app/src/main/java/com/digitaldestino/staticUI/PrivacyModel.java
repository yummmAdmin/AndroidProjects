package com.digitaldestino.staticUI;
import com.digitaldestino.interfaces.ApiInterface;

import com.digitaldestino.modelClass.static_pages.PrivacyResponse;
import com.digitaldestino.modelClass.static_pages.User;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.utils.CommonMethod;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PrivacyModel implements PrivacyContract.Model
{
    private final String TAG = "ArticleDetailModel";

    @Override
    public void getPrivacyDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("identifier", "privacy_policy");
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getStaticsDetails(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrivacyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrivacyResponse privacyResponse) {

                        if (privacyResponse != null) {
                            User user=privacyResponse.getResponse_data().getUser();
                            onFinishedListener.onFinished(user);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
