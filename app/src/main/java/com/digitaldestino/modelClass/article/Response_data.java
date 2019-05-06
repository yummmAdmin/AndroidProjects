package com.digitaldestino.modelClass.article;

import java.util.ArrayList;

public class Response_data
{

    public ArrayList<Article> getArticle() {
        return article;
    }

    public void setArticle(ArrayList<Article> article) {
        this.article = article;
    }

    private ArrayList<Article>article;



    @Override
    public String toString()
    {
        return "ClassPojo [article = "+article+"]";
    }
}
	