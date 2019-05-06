package com.digitaldestino.modelClass.my_order;

import com.digitaldestino.modelClass.pay.BookingData;

import java.util.ArrayList;

public class Response_data
{

    private ArrayList<com.digitaldestino.modelClass.my_order.BookingData>bookingData;


    public ArrayList<com.digitaldestino.modelClass.my_order.BookingData> getBookingData() {
        return bookingData;
    }

    public void setBookingData(ArrayList<com.digitaldestino.modelClass.my_order.BookingData> bookingData) {
        this.bookingData = bookingData;
    }

    @Override

    public String toString()
    {
        return "ClassPojo [bookingData = "+bookingData+"]";
    }
}