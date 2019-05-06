package com.digitaldestino.edit_profile;

import android.util.Log;

import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.getuser.UserResponse;
import com.digitaldestino.networkClass.RetrofitClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DeleteProfileModel implements GetProfileContract.Model
{
    private final String  TAG="DeleteProfileModel";
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void getProfileDetails(final OnFinishedListener onFinishedListener, HashMap<String, String> param)
    {
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.deleteUser(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        if (userResponse != null) {
                            String status = userResponse.getStatus();
                            String msg = userResponse.getMsg();
                            String key=userResponse.getKey();
                            String first_name="",last_name="",email="",mobile="",address="",city="",zip_code="";
                            if (status.equalsIgnoreCase("1")) {
                                first_name = userResponse.getResponse_data().getUser().getFirst_name();
                                last_name = userResponse.getResponse_data().getUser().getLast_name();
                                email = userResponse.getResponse_data().getUser().getEmail();
                                mobile = userResponse.getResponse_data().getUser().getMobile();
                                address = userResponse.getResponse_data().getUser().getAddress();
                                city = userResponse.getResponse_data().getUser().getCity();
                                zip_code = userResponse.getResponse_data().getUser().getZipcode();
                            }
                            onFinishedListener.onFinished(status, msg, first_name, last_name, email, mobile, address, city, zip_code,key);

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
