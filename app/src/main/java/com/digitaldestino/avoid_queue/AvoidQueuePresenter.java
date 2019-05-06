package com.digitaldestino.avoid_queue;

import com.digitaldestino.scan_qr.ScanQrContract;
import com.digitaldestino.scan_qr.ScanQrModel;

import java.util.HashMap;

public class AvoidQueuePresenter implements AvoidQueueContract.Presenter, AvoidQueueContract.Model.OnFinishedListener {
    private AvoidQueueContract.View avoidQueueView;
    private AvoidQueueContract.Model avoidQueueModel;

    public AvoidQueuePresenter(AvoidQueueContract.View avoidQueueView) {
        this.avoidQueueView = avoidQueueView;
        this.avoidQueueModel = new AvoidQueueModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (avoidQueueView != null) {
            avoidQueueView.hideProgress();
        }
        avoidQueueView.orderForAvoidQueue(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (avoidQueueView != null) {
            avoidQueueView.hideProgress();
        }
        avoidQueueView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        avoidQueueView = null;
    }

    @Override
    public void requestForAvoidQueue(HashMap<String, String> param) {
        if (avoidQueueView != null) {
            avoidQueueView.showProgress();
        }
        avoidQueueModel.orderAvoidQueue(this, param);
    }
}
