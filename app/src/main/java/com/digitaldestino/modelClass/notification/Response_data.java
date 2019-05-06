package com.digitaldestino.modelClass.notification;

import java.util.ArrayList;

public class Response_data
{
    public ArrayList<NotificationsData> getNotificationsData() {
        return notificationsData;
    }

    public void setNotificationsData(ArrayList<NotificationsData> notificationsData) {
        this.notificationsData = notificationsData;
    }

    private ArrayList<NotificationsData>notificationsData;


    @Override
    public String toString()
    {
        return "ClassPojo [notificationsData = "+notificationsData+"]";
    }
}