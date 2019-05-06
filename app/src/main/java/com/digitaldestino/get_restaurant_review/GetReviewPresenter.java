package com.digitaldestino.get_restaurant_review;
import com.digitaldestino.modelClass.get_feedback.FeedbackData;
import com.digitaldestino.modelClass.get_feedback.FeedbackDataCount;
import java.util.ArrayList;
import java.util.HashMap;

public class GetReviewPresenter implements GetReviewContract.Presenter, GetReviewContract.Model.OnFinishedListener {
    private GetReviewContract.View getReviewView;
    private GetReviewContract.Model getReviewModel;

    public GetReviewPresenter(GetReviewContract.View getReviewView) {
        this.getReviewView = getReviewView;
        this.getReviewModel = new GetReviewModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<FeedbackData> feedbackDataArrayList, ArrayList<FeedbackDataCount> feedbackDataCountArrayList) {
        if (getReviewView != null) {
            getReviewView.hideProgress();
        }
        getReviewView.setDataToViews(status, msg, key, feedbackDataArrayList, feedbackDataCountArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        if (getReviewView != null) {
            getReviewView.hideProgress();
        }
        getReviewView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getReviewView = null;
    }

    @Override
    public void requestGetReview(HashMap<String, String> param) {
        if (getReviewView != null) {
            getReviewView.showProgress();
        }

        getReviewModel.getRestaurantFeedback(this, param);
    }
}
