package com.digitaldestino.modelClass.restaurant_info;

public class Details
{
    private String address;

    private String followers;

    private String distance;

    private String restaurant_id;

    private String loyality;

    public String getLoyality() {
        return loyality;
    }

    public void setLoyality(String loyality) {
        this.loyality = loyality;
    }

    private String name;

    private String mobile;

    private String closetime;

    private Location location;

    private String  res_food_specialities;

    private String  res_hours;
    private String  res_additional_info;

    private String res_history_image;

    public String getRes_history_image() {
        return res_history_image;
    }

    public void setRes_history_image(String res_history_image) {
        this.res_history_image = res_history_image;
    }

    public String getRes_food_specialities() {
        return res_food_specialities;
    }

    public void setRes_food_specialities(String res_food_specialities) {
        this.res_food_specialities = res_food_specialities;
    }

    public String getRes_hours() {
        return res_hours;
    }

    public void setRes_hours(String res_hours) {
        this.res_hours = res_hours;
    }

    public String getRes_additional_info() {
        return res_additional_info;
    }

    public void setRes_additional_info(String res_additional_info) {
        this.res_additional_info = res_additional_info;
    }

    public String getRes_title_details() {
        return res_title_details;
    }

    public void setRes_title_details(String res_title_details) {
        this.res_title_details = res_title_details;
    }

    private String slogan;

    private String opentime;

    private String res_image;

    private String res_title_details;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getFollowers ()
    {
        return followers;
    }

    public void setFollowers (String followers)
    {
        this.followers = followers;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getClosetime ()
    {
        return closetime;
    }

    public void setClosetime (String closetime)
    {
        this.closetime = closetime;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public String getSlogan ()
    {
        return slogan;
    }

    public void setSlogan (String slogan)
    {
        this.slogan = slogan;
    }

    public String getOpentime ()
    {
        return opentime;
    }

    public void setOpentime (String opentime)
    {
        this.opentime = opentime;
    }

    public String getRes_image ()
    {
        return res_image;
    }

    public void setRes_image (String res_image)
    {
        this.res_image = res_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", followers = "+followers+", distance = "+distance+", restaurant_id = "+restaurant_id+", name = "+name+", mobile = "+mobile+", closetime = "+closetime+", location = "+location+", slogan = "+slogan+", opentime = "+opentime+", res_image = "+res_image+"]";
    }
}
			
			