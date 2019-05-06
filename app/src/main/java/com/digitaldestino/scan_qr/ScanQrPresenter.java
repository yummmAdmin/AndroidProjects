package com.digitaldestino.scan_qr;
import java.util.HashMap;

public class ScanQrPresenter implements ScanQrContract.Presenter, ScanQrContract.Model.OnFinishedListener {
    private ScanQrContract.View scanQrView;
    private ScanQrContract.Model scanQrModel;

    public ScanQrPresenter(ScanQrContract.View scanQrView) {
        this.scanQrView = scanQrView;
        this.scanQrModel = new ScanQrModel();
    }

    @Override
    public void onFinished(String status, String msg, String key) {
        if (scanQrView != null) {
            scanQrView.hideProgress();
        }
        scanQrView.orderForDininig(status, msg, key);
    }

    @Override
    public void onFailure(Throwable t) {
        if (scanQrView != null) {
            scanQrView.hideProgress();
        }
        scanQrView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        scanQrView = null;
    }

    @Override
    public void requestForDininig(HashMap<String, String> param) {
        if (scanQrView != null) {
            scanQrView.showProgress();
        }
        scanQrModel.orderForDininig(this, param);
    }
}
