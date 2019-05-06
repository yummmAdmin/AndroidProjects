package com.digitaldestino.modelClass.get_restaurant_menu;

import java.util.ArrayList;

public class Response_data
{
    private ArrayList<ResDetial>resDetial;

    public ArrayList<ResDetial> getResDetial() {
        return resDetial;
    }

    public void setResDetial(ArrayList<ResDetial> resDetial) {
        this.resDetial = resDetial;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [resDetial = "+resDetial+"]";
    }

}
	