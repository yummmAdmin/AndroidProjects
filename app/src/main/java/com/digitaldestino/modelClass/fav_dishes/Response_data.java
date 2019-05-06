package com.digitaldestino.modelClass.fav_dishes;

public class Response_data
{
    private FavouriteData[] favouriteData;

    public FavouriteData[] getFavouriteData ()
    {
        return favouriteData;
    }

    public void setFavouriteData (FavouriteData[] favouriteData)
    {
        this.favouriteData = favouriteData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [favouriteData = "+favouriteData+"]";
    }
}