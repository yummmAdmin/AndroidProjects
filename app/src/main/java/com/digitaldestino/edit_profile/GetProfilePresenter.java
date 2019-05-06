package com.digitaldestino.edit_profile;

import java.util.HashMap;

public class GetProfilePresenter implements GetProfileContract.Presenter, GetProfileContract.Model.OnFinishedListener {
    private GetProfileContract.View getProfileDetailView;
    private GetProfileContract.Model getprofileDetailsModel;

    public GetProfilePresenter(GetProfileContract.View getProfileDetailView) {
        this.getProfileDetailView = getProfileDetailView;
        this.getprofileDetailsModel = new GetProfileModel();
    }

    @Override
    public void onFinished(String status, String msg, String first_name, String last_name, String email, String mobile, String address, String city, String zip_code,String key) {
        if (getProfileDetailView != null) {
            getProfileDetailView.hideProgress();
        }
        getProfileDetailView.setDataToViews(status, msg, first_name, last_name, email, mobile, address, city, zip_code,key);

    }

    @Override
    public void onFailure(Throwable t) {
        if (getProfileDetailView != null) {
            getProfileDetailView.hideProgress();
        }
        getProfileDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        getProfileDetailView = null;
    }

    @Override
    public void requestProfileData(HashMap<String, String> param) {
        if (getProfileDetailView != null) {
            getProfileDetailView.showProgress();
        }
        getprofileDetailsModel.getProfileDetails(this, param);
    }
}

