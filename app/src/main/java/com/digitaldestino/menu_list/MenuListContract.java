package com.digitaldestino.menu_list;
import com.digitaldestino.modelClass.login.User;

import java.util.HashMap;

public interface MenuListContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status,String msg);

            void onFailure(Throwable t);
        }

        void getLogoutDetails(OnFinishedListener onFinishedListener, HashMap<String,String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status,String msg);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void logoutFromServer(HashMap<String,String>param);
    }

}
