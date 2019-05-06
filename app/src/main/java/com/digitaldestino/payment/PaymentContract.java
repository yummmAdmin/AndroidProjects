package com.digitaldestino.payment;
import com.digitaldestino.modelClass.get_card.Card_list;

import java.util.ArrayList;
import java.util.HashMap;

public interface PaymentContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg,String key, ArrayList<Card_list> card_list);

            void onFailure(Throwable t);
        }

        void getCardDetails(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg,String key,ArrayList<Card_list> card_list);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestCardList(HashMap<String, String> param);
    }
}
