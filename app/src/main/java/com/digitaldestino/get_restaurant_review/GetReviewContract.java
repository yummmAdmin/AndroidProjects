package com.digitaldestino.get_restaurant_review;


import com.digitaldestino.modelClass.get_feedback.FeedbackData;
import com.digitaldestino.modelClass.get_feedback.FeedbackDataCount;

import java.util.ArrayList;
import java.util.HashMap;

public interface GetReviewContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(String status, String msg, String key, ArrayList<FeedbackData> feedbackDataArrayList, ArrayList<FeedbackDataCount> feedbackDataCountArrayList);

            void onFailure(Throwable t);
        }

        void getRestaurantFeedback(OnFinishedListener onFinishedListener, HashMap<String, String> param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(String status, String msg, String key, ArrayList<FeedbackData> feedbackDataArrayList, ArrayList<FeedbackDataCount> feedbackDataCountArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestGetReview(HashMap<String, String> param);
    }
}
