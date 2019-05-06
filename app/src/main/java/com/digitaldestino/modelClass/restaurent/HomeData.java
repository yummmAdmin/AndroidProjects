package com.digitaldestino.modelClass.restaurent;

public class HomeData
{
    private String distance;

    private String _id;

    private String address;

    private String name;

    private String closetime;

    private String opentime;

    private String res_image;

    public String getRes_image() {
        return res_image;
    }

    public void setRes_image(String res_image) {
        this.res_image = res_image;
    }

    private String mobile;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    private String  slogan;

    private String restaurant_id;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
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

    public String getClosetime ()
    {
        return closetime;
    }

    public void setClosetime (String closetime)
    {
        this.closetime = closetime;
    }

    public String getOpentime ()
    {
        return opentime;
    }

    public void setOpentime (String opentime)
    {
        this.opentime = opentime;
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
        return "ClassPojo [distance = "+distance+", _id = "+_id+", address = "+address+", name = "+name+", closetime = "+closetime+", opentime = "+opentime+", mobile = "+mobile+"]";
    }
}
			
			