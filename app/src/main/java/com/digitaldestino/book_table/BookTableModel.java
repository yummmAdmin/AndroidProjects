package com.digitaldestino.book_table;
import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.book_table.BookTableResponse;
import com.digitaldestino.networkClass.RetrofitClient;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BookTableModel implements BookTableContract.Model {
    private final String TAG = "BookTableModel";

    @Override
    public void getBookTableDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.bookTable(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookTableResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BookTableResponse bookTableResponse) {
                        if (bookTableResponse != null) {
                            String status = bookTableResponse.getStatus();
                            String msg = bookTableResponse.getMsg();
                            String key = bookTableResponse.getKey();
                            onFinishedListener.onFinished(status, msg, key);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
