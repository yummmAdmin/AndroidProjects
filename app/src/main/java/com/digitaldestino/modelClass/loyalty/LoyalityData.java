package com.digitaldestino.modelClass.loyalty;

public class LoyalityData
{
    private String loyality_date_time;

    private String restaurant_id;

    private String seekerId;

    private String server_date_time;

    private String _id;

    private String loyality_timezone;

    public String getLoyality_date_time ()
    {
        return loyality_date_time;
    }

    public void setLoyality_date_time (String loyality_date_time)
    {
        this.loyality_date_time = loyality_date_time;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getSeekerId ()
    {
        return seekerId;
    }

    public void setSeekerId (String seekerId)
    {
        this.seekerId = seekerId;
    }

    public String getServer_date_time ()
    {
        return server_date_time;
    }

    public void setServer_date_time (String server_date_time)
    {
        this.server_date_time = server_date_time;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getLoyality_timezone ()
    {
        return loyality_timezone;
    }

    public void setLoyality_timezone (String loyality_timezone)
    {
        this.loyality_timezone = loyality_timezone;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [loyality_date_time = "+loyality_date_time+", restaurant_id = "+restaurant_id+", seekerId = "+seekerId+", server_date_time = "+server_date_time+", _id = "+_id+", loyality_timezone = "+loyality_timezone+"]";
    }
}
			
			