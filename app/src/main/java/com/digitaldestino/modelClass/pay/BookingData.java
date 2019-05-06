package com.digitaldestino.modelClass.pay;

public class BookingData
{
    private String admin_percentage;

    private String order_date_time;

    private String restaurant_amt;

    private String total_price;

    private String restaurant_percentage;

    private String restaurant_id;

    private String payment_status;

    private String discount;

    private String admin_amt;

    private String seeker_id;

    private String order_timezone;

    private String service_tax;

    private String _id;

    private String server_date_time;

    private Items[] items;

    private String status;

    public String getAdmin_percentage ()
    {
        return admin_percentage;
    }

    public void setAdmin_percentage (String admin_percentage)
    {
        this.admin_percentage = admin_percentage;
    }

    public String getOrder_date_time ()
    {
        return order_date_time;
    }

    public void setOrder_date_time (String order_date_time)
    {
        this.order_date_time = order_date_time;
    }

    public String getRestaurant_amt ()
    {
        return restaurant_amt;
    }

    public void setRestaurant_amt (String restaurant_amt)
    {
        this.restaurant_amt = restaurant_amt;
    }

    public String getTotal_price ()
    {
        return total_price;
    }

    public void setTotal_price (String total_price)
    {
        this.total_price = total_price;
    }

    public String getRestaurant_percentage ()
    {
        return restaurant_percentage;
    }

    public void setRestaurant_percentage (String restaurant_percentage)
    {
        this.restaurant_percentage = restaurant_percentage;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getPayment_status ()
    {
        return payment_status;
    }

    public void setPayment_status (String payment_status)
    {
        this.payment_status = payment_status;
    }

    public String getDiscount ()
    {
        return discount;
    }

    public void setDiscount (String discount)
    {
        this.discount = discount;
    }

    public String getAdmin_amt ()
    {
        return admin_amt;
    }

    public void setAdmin_amt (String admin_amt)
    {
        this.admin_amt = admin_amt;
    }

    public String getSeeker_id ()
    {
        return seeker_id;
    }

    public void setSeeker_id (String seeker_id)
    {
        this.seeker_id = seeker_id;
    }

    public String getOrder_timezone ()
    {
        return order_timezone;
    }

    public void setOrder_timezone (String order_timezone)
    {
        this.order_timezone = order_timezone;
    }

    public String getService_tax ()
    {
        return service_tax;
    }

    public void setService_tax (String service_tax)
    {
        this.service_tax = service_tax;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getServer_date_time ()
    {
        return server_date_time;
    }

    public void setServer_date_time (String server_date_time)
    {
        this.server_date_time = server_date_time;
    }

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [admin_percentage = "+admin_percentage+", order_date_time = "+order_date_time+", restaurant_amt = "+restaurant_amt+", total_price = "+total_price+", restaurant_percentage = "+restaurant_percentage+", restaurant_id = "+restaurant_id+", payment_status = "+payment_status+", discount = "+discount+", admin_amt = "+admin_amt+", seeker_id = "+seeker_id+", order_timezone = "+order_timezone+", service_tax = "+service_tax+", _id = "+_id+", server_date_time = "+server_date_time+", items = "+items+", status = "+status+"]";
    }
}
	