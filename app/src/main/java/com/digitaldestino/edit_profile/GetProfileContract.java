package com.digitaldestino.edit_profile;
import java.util.HashMap;

public interface GetProfileContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg,String first_name,String last_name,String email,String mobile,String address,String city,String zip_code,String key);

            void onFailure(Throwable t);
        }

        void getProfileDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg,String first_name,String last_name,String email,String mobile,String address,String city,String zip_code,String key);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestProfileData(HashMap<String,String>param);
    }
}
