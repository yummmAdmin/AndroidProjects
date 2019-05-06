package com.digitaldestino.modelClass.near_restaurent;

public class HomeData {
    private String address;

    private String distance;

    private String cat_name;

    private String restaurant_id;

    private String name;

    private String mobile;

    private String closetime;

    private Dishes dishes;

    private String section;

    private String _id;

    private String type;

    private String opentime;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getCat_name ()
    {
        return cat_name;
    }

    public void setCat_name (String cat_name)
    {
        this.cat_name = cat_name;
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

    public Dishes getDishes ()
    {
        return dishes;
    }

    public void setDishes (Dishes dishes)
    {
        this.dishes = dishes;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getOpentime ()
    {
        return opentime;
    }

    public void setOpentime (String opentime)
    {
        this.opentime = opentime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", distance = "+distance+", cat_name = "+cat_name+", restaurant_id = "+restaurant_id+", name = "+name+", mobile = "+mobile+", closetime = "+closetime+", dishes = "+dishes+", section = "+section+", _id = "+_id+", type = "+type+", opentime = "+opentime+"]";
    }
}
			
			