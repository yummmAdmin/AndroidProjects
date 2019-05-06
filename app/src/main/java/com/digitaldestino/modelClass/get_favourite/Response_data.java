package com.digitaldestino.modelClass.get_favourite;

import java.util.ArrayList;

public class Response_data {
    private ArrayList<FavData> favData;

    public ArrayList<FavData> getFavData() {
        return favData;
    }

    public void setFavData(ArrayList<FavData> favData) {
        this.favData = favData;
    }

    @Override
    public String toString() {
        return "ClassPojo [favData = " + favData + "]";
    }
}
	