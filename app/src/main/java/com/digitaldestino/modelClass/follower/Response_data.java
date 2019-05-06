package com.digitaldestino.modelClass.follower;

import java.util.ArrayList;

public class Response_data
{

    public ArrayList<FollowerData> getFollowerData() {
        return followerData;
    }

    public void setFollowerData(ArrayList<FollowerData> followerData) {
        this.followerData = followerData;
    }

    private ArrayList<FollowerData>followerData;

    private String restaurant_followers;



    public String getRestaurant_followers ()
    {
        return restaurant_followers;
    }

    public void setRestaurant_followers (String restaurant_followers)
    {
        this.restaurant_followers = restaurant_followers;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [followerData = "+followerData+", restaurant_followers = "+restaurant_followers+"]";
    }
}
	