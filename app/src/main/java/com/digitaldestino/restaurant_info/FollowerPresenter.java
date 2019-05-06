package com.digitaldestino.restaurant_info;

import com.digitaldestino.modelClass.follower.FollowerData;

import java.util.ArrayList;
import java.util.HashMap;

public class FollowerPresenter implements FollowerContract.Presenter, FollowerContract.Model.OnFinishedListener {
    private FollowerContract.View followerView;
    private FollowerContract.Model followerModel;

    public FollowerPresenter(FollowerContract.View followerView) {
        this.followerView = followerView;
        this.followerModel = new FollowerModel();
    }

    @Override
    public void onFinished(String status, String msg, String key, ArrayList<FollowerData> followerData,String restaurant_followers) {
        if (followerView != null) {
            followerView.hideProgress();
        }
        followerView.setData(status, msg, key, followerData,restaurant_followers);
    }

    @Override
    public void onFailure(Throwable t) {
        if (followerView != null) {
            followerView.hideProgress();
        }
        followerView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        followerView = null;
    }

    @Override
    public void requestFollower(HashMap<String, String> param) {
        if (followerView != null) {
            followerView.showProgress();
        }
        followerModel.follower(this, param);
    }
}
