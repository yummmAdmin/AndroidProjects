package com.digitaldestino.modelClass.restaurant_info;

import java.util.ArrayList;

public class Response_data {
    private String user_following;


    public ArrayList<Details> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Details> details) {
        this.details = details;
    }

    private ArrayList<Details> details;

    public String getUser_following() {
        return user_following;
    }

    public void setUser_following(String user_following) {
        this.user_following = user_following;
    }


    @Override
    public String toString() {
        return "ClassPojo [user_following = " + user_following + ", details = " + details + "]";
    }
}
