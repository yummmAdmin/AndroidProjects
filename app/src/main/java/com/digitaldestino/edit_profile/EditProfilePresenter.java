package com.digitaldestino.edit_profile;

import java.util.HashMap;

public class EditProfilePresenter implements  GetProfileContract.Presenter, GetProfileContract.Model.OnFinishedListener
{
    private GetProfileContract.View editProfileDetailView;
    private GetProfileContract.Model editprofileDetailsModel;

    public EditProfilePresenter(GetProfileContract.View editProfileDetailView) {
        this.editProfileDetailView = editProfileDetailView;
        this.editprofileDetailsModel = new EditProfileModel();
    }
    @Override
    public void onFinished(String status, String msg, String first_name, String last_name, String email, String mobile, String address, String city, String zip_code,String key) {
        if (editProfileDetailView != null) {
            editProfileDetailView.hideProgress();
        }
        editProfileDetailView.setDataToViews(status, msg, first_name, last_name, email, mobile, address, city, zip_code,key);

    }

    @Override
    public void onFailure(Throwable t) {
        if (editProfileDetailView != null) {
            editProfileDetailView.hideProgress();
        }
        editProfileDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        editProfileDetailView=null;
    }

    @Override
    public void requestProfileData(HashMap<String, String> param) {
        if (editProfileDetailView != null) {
            editProfileDetailView.showProgress();
        }

        editprofileDetailsModel.getProfileDetails(this, param);
    }
}
