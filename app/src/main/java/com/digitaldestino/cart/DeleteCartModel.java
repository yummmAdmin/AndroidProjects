package com.digitaldestino.cart;
import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.delete_cart.AdditionalCharges;
import com.digitaldestino.modelClass.delete_cart.DeleteCartResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeleteCartModel implements DeleteCartContract.Model
{
    private final String TAG="DeleteCartModel";
    @Override
    public void getDeleteCart(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.deleteCartData(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteCartResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeleteCartResponse deleteCartResponse) {
                        if (deleteCartResponse != null) {
                            String status = deleteCartResponse.getStatus();
                            String msg = deleteCartResponse.getMsg();
                            String key = deleteCartResponse.getKey();
                            String cart_sum = "";
                            String service_tax="";
                            if(status.equalsIgnoreCase("1"))
                            {
                               cart_sum=deleteCartResponse.getResponse_data().getAdditionalCharges().getCart_sum();
                               service_tax=deleteCartResponse.getResponse_data().getAdditionalCharges().getService_tax();
                            }
                            onFinishedListener.onFinished(status, msg, key,cart_sum,service_tax);
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
