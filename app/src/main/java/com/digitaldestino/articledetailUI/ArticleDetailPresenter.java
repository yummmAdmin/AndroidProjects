package com.digitaldestino.articledetailUI;

import com.digitaldestino.articleUI.ArticleListContract;
import com.digitaldestino.modelClass.articleDetail.ArticleDetailResponse;
import com.digitaldestino.modelClass.articleDetail.User;

import java.util.HashMap;

public class ArticleDetailPresenter implements ArticleDetailContract.Presenter,ArticleDetailContract.Model.OnFinishedListener {
    private ArticleDetailContract.View articleDetailView;
    private ArticleDetailContract.Model articleDetailsModel;

    public ArticleDetailPresenter(ArticleDetailContract.View articleDetailView) {
        this.articleDetailView = articleDetailView;
        this.articleDetailsModel = new ArticleDetailModel();
    }
    @Override
    public void onFinished(User user)
    {

        if(articleDetailView != null){
            articleDetailView.hideProgress();
        }
        articleDetailView.setDataToViews(user);
    }

    @Override
    public void onFailure(Throwable t) {
        if(articleDetailView != null){
            articleDetailView.hideProgress();
        }
        articleDetailView.onResponseFailure(t);
    }

    @Override
    public void onDestroy() {
        articleDetailView = null;
    }

    @Override
    public void requestArticleData(HashMap<String,String> param)
    {
        if(articleDetailView != null){
            articleDetailView.showProgress();
        }
        articleDetailsModel.getArticleDetails(this, param);
    }
}
