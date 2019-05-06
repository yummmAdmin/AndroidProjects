package com.digitaldestino.search_activity;
import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteData;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import java.util.ArrayList;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
public class SearchModel implements SearchContract.Model
{
    private final static String TAG="SearchModel";
    @Override
    public void getSearchDish(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.autoComplete(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoCompleteResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AutoCompleteResponse autoCompleteResponse) {
                        Log.d("response", autoCompleteResponse.toString());
                        String status = autoCompleteResponse.getStatus();
                        String msg = autoCompleteResponse.getMsg();
                        String key = autoCompleteResponse.getKey();
                        ArrayList<AutoCompleteData>autoCompleteData=null;
                        if(status.equalsIgnoreCase("1"))
                        {
                            autoCompleteData=autoCompleteResponse.getResponse_data().getAutoCompleteData();
                        }
                        onFinishedListener.onFinished(status, msg, key,autoCompleteData);
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
