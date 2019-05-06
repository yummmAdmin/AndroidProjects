package com.digitaldestino.articleUI;

import com.digitaldestino.modelClass.article.Article;

import java.util.ArrayList;
import java.util.List;

public interface ArticleListContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(ArrayList<Article> articleArrayList);

            void onFailure(Throwable t);
        }

        void getArticleList(OnFinishedListener onFinishedListener);

        // void getArticleList(OnFinishedListener onFinishedListener, int pageNo);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<Article> articleArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();

    }
}
