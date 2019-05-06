package com.digitaldestino.my_order;
import com.digitaldestino.modelClass.my_order.BookingData;

import java.util.ArrayList;
import java.util.HashMap;

public interface MyOrderContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg,String key,ArrayList<BookingData> bookingDataArrayList);

            void onFailure(Throwable t);
        }

        void getMyOrders(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key, ArrayList<BookingData> bookingDataArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestMyOrder(HashMap<String, String> param);
    }
}
