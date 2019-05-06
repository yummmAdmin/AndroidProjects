package com.digitaldestino.modelClass.get_feedback;

import java.util.ArrayList;

public class Response_data {
    private ArrayList<FeedbackDataCount> feedbackDataCount;

    private ArrayList<FeedbackData> feedbackData;


    public ArrayList<FeedbackDataCount> getFeedbackDataCount() {
        return feedbackDataCount;
    }

    public void setFeedbackDataCount(ArrayList<FeedbackDataCount> feedbackDataCount) {
        this.feedbackDataCount = feedbackDataCount;
    }


    public ArrayList<FeedbackData> getFeedbackData() {
        return feedbackData;
    }

    public void setFeedbackData(ArrayList<FeedbackData> feedbackData) {
        this.feedbackData = feedbackData;
    }

    @Override
    public String toString() {
        return "ClassPojo [feedbackData = " + feedbackData + ", feedbackDataCount = " + feedbackDataCount + "]";
    }
}
			
	