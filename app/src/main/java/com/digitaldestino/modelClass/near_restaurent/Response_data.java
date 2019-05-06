package com.digitaldestino.modelClass.near_restaurent;

import java.util.ArrayList;

public class Response_data
{

    public ArrayList<HomeData> getHomeData() {
        return homeData;
    }

    public void setHomeData(ArrayList<HomeData> homeData) {
        this.homeData = homeData;
    }

    private ArrayList<HomeData>homeData;


    @Override
    public String toString()
    {
        return "ClassPojo [homeData = "+homeData+"]";
    }
}
	