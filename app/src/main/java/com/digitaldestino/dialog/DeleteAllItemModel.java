package com.digitaldestino.dialog;
import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.add_cart.AddCartResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeleteAllItemModel implements DeleteAllItemContract.Model {
    private final String TAG = "DeleteAllItemModel";

    @Override
    public void deleteAllCart(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.deleteAllCart(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddCartResponse addCartResponse) {
                        if (addCartResponse != null) {
                            String status = addCartResponse.getStatus();
                            String msg = addCartResponse.getMsg();
                            String key = addCartResponse.getKey();
                            onFinishedListener.onFinished(status, msg, key);
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
