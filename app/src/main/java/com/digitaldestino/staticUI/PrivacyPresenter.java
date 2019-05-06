package com.digitaldestino.staticUI;

import com.digitaldestino.articledetailUI.ArticleDetailContract;
import com.digitaldestino.articledetailUI.ArticleDetailModel;
import com.digitaldestino.modelClass.static_pages.User;

import java.util.HashMap;

public class PrivacyPresenter implements  PrivacyContract.Presenter,PrivacyContract.Model.OnFinishedListener
{
    private PrivacyContract.View privacyDetailView;
    private PrivacyContract.Model privacyDetailsModel;

    public PrivacyPresenter(PrivacyContract.View privacyDetailView)
    {
        this.privacyDetailView = privacyDetailView;
        this.privacyDetailsModel = new PrivacyModel();
    }

    @Override
    public void onFinished(User user) {
        if(privacyDetailView != null){
            privacyDetailView.hideProgress();
        }
        privacyDetailView.setDataToViews(user);
    }

    @Override
    public void onFailure(Throwable t) {
        if(privacyDetailView != null){
            privacyDetailView.hideProgress();
        }
        privacyDetailView.onResponseFailure(t);
    }


    @Override
    public void onDestroy() {
        privacyDetailView = null;
    }

    @Override
    public void requestPrivacyData(HashMap<String,String> param) {
        if(privacyDetailView != null){
            privacyDetailView.showProgress();
        }
        privacyDetailsModel.getPrivacyDetails(this, param);
    }
}
