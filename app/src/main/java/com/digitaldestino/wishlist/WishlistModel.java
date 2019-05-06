package com.digitaldestino.wishlist;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.get_favourite.FavData;
import com.digitaldestino.modelClass.get_favourite.GetFavResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class WishlistModel implements WishlistContract.Model {
    private static final String TAG = "WishlistModel";

    @Override
    public void getFavourites(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getFavourites(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetFavResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GetFavResponse getFavResponse) {
                        if (getFavResponse != null) {

                            String status = getFavResponse.getStatus();
                            String msg = getFavResponse.getMsg();
                            String key = getFavResponse.getKey();

                            ArrayList<FavData> favData = null;
                            if (status.equalsIgnoreCase("1")) {
                                favData = getFavResponse.getResponse_data().getFavData();
                            }

                            onFinishedListener.onFinished(status, msg, key, favData);
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
