package com.digitaldestino.articledetailUI;

import com.digitaldestino.articleUI.ArticleListContract;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.modelClass.articleDetail.ArticleDetailResponse;
import com.digitaldestino.modelClass.articleDetail.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface ArticleDetailContract
{
    interface Model {

        interface OnFinishedListener {
            void onFinished(User user);

            void onFailure(Throwable t);
        }

        void getArticleDetails(OnFinishedListener onFinishedListener, HashMap<String,String>param);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(User user);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestArticleData(HashMap<String,String>param);
    }
}
