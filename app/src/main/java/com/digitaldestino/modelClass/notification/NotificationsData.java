package com.digitaldestino.modelClass.notification;

public class NotificationsData
{
    private String queue_timezone;

    private String restaurant_id;

    private String queue_no;

    private String seekerId;

    private String _id;

    private String content;

    private String restaurant_name;

    private String queue_date_time;

    public String getAddedon() {
        return addedon;
    }

    public void setAddedon(String addedon) {
        this.addedon = addedon;
    }

    private String addedon;

    public String getQueue_timezone ()
    {
        return queue_timezone;
    }

    public void setQueue_timezone (String queue_timezone)
    {
        this.queue_timezone = queue_timezone;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getQueue_no ()
    {
        return queue_no;
    }

    public void setQueue_no (String queue_no)
    {
        this.queue_no = queue_no;
    }

    public String getSeekerId ()
    {
        return seekerId;
    }

    public void setSeekerId (String seekerId)
    {
        this.seekerId = seekerId;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getRestaurant_name ()
    {
        return restaurant_name;
    }

    public void setRestaurant_name (String restaurant_name)
    {
        this.restaurant_name = restaurant_name;
    }

    public String getQueue_date_time ()
    {
        return queue_date_time;
    }

    public void setQueue_date_time (String queue_date_time)
    {
        this.queue_date_time = queue_date_time;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [queue_timezone = "+queue_timezone+", restaurant_id = "+restaurant_id+", queue_no = "+queue_no+", seekerId = "+seekerId+", _id = "+_id+", content = "+content+", restaurant_name = "+restaurant_name+", queue_date_time = "+queue_date_time+"]";
    }
}
			
			