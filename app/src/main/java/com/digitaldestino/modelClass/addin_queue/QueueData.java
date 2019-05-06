package com.digitaldestino.modelClass.addin_queue;

public class QueueData
{
    private String queue_timezone;

    private String restaurant_id;

    private String seekerId;

    private String server_date_time;

    private String _id;

    private String queue_date_time;

    private String status;

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

    public String getQueue_date_time ()
    {
        return queue_date_time;
    }

    public void setQueue_date_time (String queue_date_time)
    {
        this.queue_date_time = queue_date_time;
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
        return "ClassPojo [queue_timezone = "+queue_timezone+", restaurant_id = "+restaurant_id+", seekerId = "+seekerId+", server_date_time = "+server_date_time+", _id = "+_id+", queue_date_time = "+queue_date_time+", status = "+status+"]";
    }
}
			
			