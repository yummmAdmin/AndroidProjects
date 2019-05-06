package com.digitaldestino.articleUI;

import com.digitaldestino.modelClass.article.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleListPresenter implements ArticleListContract.Presenter, ArticleListContract.Model.OnFinishedListener {
    private ArticleListContract.View articleListView;
    private ArticleListContract.Model articleListModel;

    public ArticleListPresenter(ArticleListContract.View articleListView) {
        this.articleListView = articleListView;
        articleListModel = new ArticleListModel();
    }

    @Override
    public void onDestroy() {
        this.articleListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

    }

    @Override
    public void requestDataFromServer() {
        if (articleListView != null) {
            articleListView.showProgress();
        }
        articleListModel.getArticleList(this);
    }

    @Override
    public void onFinished(ArrayList<Article> articleArrayList) {
        articleListView.setDataToRecyclerView(articleArrayList);
        if (articleListView != null) {
            articleListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        articleListView.onResponseFailure(t);
        if (articleListView != null) {
            articleListView.hideProgress();
        }
    }
}
