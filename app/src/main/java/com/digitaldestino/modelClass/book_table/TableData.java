package com.digitaldestino.modelClass.book_table;

public class TableData
{
    private String order_timezone;

    private String _id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private String order_date_time;

    private String address;

    private String name;

    private String no_of_person;

    private String restaurant_id;

    private String seeker_id;

    private String server_date_time;

    private String mobile;

    private String restaurant_name;

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    private String restaurant_address;
    private String restaurant_image;

    public String getOrder_timezone ()
    {
        return order_timezone;
    }

    public void setOrder_timezone (String order_timezone)
    {
        this.order_timezone = order_timezone;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getOrder_date_time ()
    {
        return order_date_time;
    }

    public void setOrder_date_time (String order_date_time)
    {
        this.order_date_time = order_date_time;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getNo_of_person ()
    {
        return no_of_person;
    }

    public void setNo_of_person (String no_of_person)
    {
        this.no_of_person = no_of_person;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getSeeker_id ()
    {
        return seeker_id;
    }

    public void setSeeker_id (String seeker_id)
    {
        this.seeker_id = seeker_id;
    }

    public String getServer_date_time ()
    {
        return server_date_time;
    }

    public void setServer_date_time (String server_date_time)
    {
        this.server_date_time = server_date_time;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [order_timezone = "+order_timezone+", _id = "+_id+", order_date_time = "+order_date_time+", address = "+address+", name = "+name+", no_of_person = "+no_of_person+", restaurant_id = "+restaurant_id+", seeker_id = "+seeker_id+", server_date_time = "+server_date_time+", mobile = "+mobile+"]";
    }
}