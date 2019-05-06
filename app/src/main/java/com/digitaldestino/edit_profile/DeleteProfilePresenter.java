package com.digitaldestino.edit_profile;

import java.util.HashMap;

public class DeleteProfilePresenter implements  GetProfileContract.Presenter, GetProfileContract.Model.OnFinishedListener
{
    private GetProfileContract.View deleteProfileDetailView;
    private GetProfileContract.Model deleteprofileDetailsModel;

    public DeleteProfilePresenter(GetProfileContract.View deleteProfileDetailView) {
        this.deleteProfileDetailView = deleteProfileDetailView;
        this.deleteprofileDetailsModel = new DeleteProfileModel();
    }

    @Override
    public void onFinished(String status, String msg, String first_name, String last_name, String email, String mobile, String address, String city, String zip_code, String key) {
        if (deleteProfileDetailView != null) {
            deleteProfileDetailView.hideProgress();
        }
        deleteProfileDetailView.setDataToViews(status, msg, first_name, last_name, email, mobile, address, city, zip_code,key);

    }

    @Override
    public void onFailure(Throwable t) {
        if (deleteProfileDetailView != null) {
            deleteProfileDetailView.hideProgress();
        }
        deleteProfileDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        deleteProfileDetailView=null;
    }

    @Override
    public void requestProfileData(HashMap<String, String> param) {
        if (deleteProfileDetailView != null) {
            deleteProfileDetailView.showProgress();
        }
        deleteprofileDetailsModel.getProfileDetails(this, param);
    }
}
