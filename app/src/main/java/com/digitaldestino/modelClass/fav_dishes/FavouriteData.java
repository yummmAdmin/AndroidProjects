package com.digitaldestino.modelClass.fav_dishes;

public class FavouriteData
{
    private String food_item_id;

    private String seekerId;

    private String is_favourite;

    private String server_date_time;

    private String favourite_date_time;

    private String _id;

    public String getFood_item_id ()
    {
        return food_item_id;
    }

    public void setFood_item_id (String food_item_id)
    {
        this.food_item_id = food_item_id;
    }

    public String getSeekerId ()
    {
        return seekerId;
    }

    public void setSeekerId (String seekerId)
    {
        this.seekerId = seekerId;
    }

    public String getIs_favourite ()
    {
        return is_favourite;
    }

    public void setIs_favourite (String is_favourite)
    {
        this.is_favourite = is_favourite;
    }

    public String getServer_date_time ()
    {
        return server_date_time;
    }

    public void setServer_date_time (String server_date_time)
    {
        this.server_date_time = server_date_time;
    }

    public String getFavourite_date_time ()
    {
        return favourite_date_time;
    }

    public void setFavourite_date_time (String favourite_date_time)
    {
        this.favourite_date_time = favourite_date_time;
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
        return "ClassPojo [food_item_id = "+food_item_id+", seekerId = "+seekerId+", is_favourite = "+is_favourite+", server_date_time = "+server_date_time+", favourite_date_time = "+favourite_date_time+", _id = "+_id+"]";
    }
}
			
			