package com.digitaldestino.send_feedback;


import com.digitaldestino.edit_profile.GetProfileContract;
import com.digitaldestino.edit_profile.GetProfileModel;

import java.util.HashMap;

public class SendFeedbackPresenter implements  SendFeedbackContract.Presenter, SendFeedbackContract.Model.OnFinishedListener {
    private SendFeedbackContract.View sendFeedbackView;
    private SendFeedbackContract.Model sendFeedbackModel;

    public SendFeedbackPresenter(SendFeedbackContract.View sendFeedbackView) {
        this.sendFeedbackView = sendFeedbackView;
        this.sendFeedbackModel = new SendFeedbackModel();
    }
    @Override
    public void onFinished(String status, String msg, String key) {
        if (sendFeedbackView != null) {
            sendFeedbackView.hideProgress();
        }
        sendFeedbackView.setDataToViews(status, msg,key);

    }

    @Override
    public void onFailure(Throwable t) {
        if (sendFeedbackView != null) {
            sendFeedbackView.hideProgress();
        }
        sendFeedbackView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        sendFeedbackView=null;
    }

    @Override
    public void requestSendFeedback(HashMap<String, String> param) {
        if (sendFeedbackView != null) {
            sendFeedbackView.showProgress();
        }
        sendFeedbackModel.sendFeedback(this, param);
    }
}
