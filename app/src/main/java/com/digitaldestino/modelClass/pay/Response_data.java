package com.digitaldestino.modelClass.pay;

public class Response_data
{
    private BookingData[] bookingData;


    public BookingData[] getBookingData ()
    {
        return bookingData;
    }

    public void setBookingData (BookingData[] bookingData)
    {
        this.bookingData = bookingData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bookingData = "+bookingData+"]";
    }
}