package com.digitaldestino.bookingtable_list;
import android.util.Log;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.book_table.BookTableResponse;
import com.digitaldestino.modelClass.book_table.TableData;
import com.digitaldestino.networkClass.RetrofitClient;
import java.util.ArrayList;
import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BookingTableModel implements BookingTableContract.Model {
    private final String TAG="BookingTableModel";
    @Override
    public void getBookTableDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param) {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getBookTable(param).subscribeOn(Schedulers.io())
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
                            String key=bookTableResponse.getKey();
                            ArrayList<TableData>tableDataArrayList=null;
                            if(status.equalsIgnoreCase("1"))
                            {
                                tableDataArrayList=bookTableResponse.getResponse_data().getTableData();
                            }
                            onFinishedListener.onFinished(status, msg,key,tableDataArrayList);
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
