package com.digitaldestino.modelClass.follower;

public class FollowerData
{
    private String is_follow;

    private String restaurant_id;

    private String seekerId;

    private String follow_date_time;

    private String server_date_time;

    private String _id;

    public String getIs_follow ()
    {
        return is_follow;
    }

    public void setIs_follow (String is_follow)
    {
        this.is_follow = is_follow;
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

    public String getFollow_date_time ()
    {
        return follow_date_time;
    }

    public void setFollow_date_time (String follow_date_time)
    {
        this.follow_date_time = follow_date_time;
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

    @Override
    public String toString()
    {
        return "ClassPojo [is_follow = "+is_follow+", restaurant_id = "+restaurant_id+", seekerId = "+seekerId+", follow_date_time = "+follow_date_time+", server_date_time = "+server_date_time+", _id = "+_id+"]";
    }
}
			
			