package com.digitaldestino.modelClass.get_favourite;

public class Dishes
{
    private String food_type_id;

    private String restaurant_id;

    private String is_favourite;

    private String food_image;

    private String food_item_id;

    private String new_date_end;

    private String is_special;

    private Price[] price;

    private String cat_id;

    private String base_price;

    private String salecount;

    private String food_desc;

    private String _id;

    private String food_section_id;

    private String food_item_name;

    private String status;

    public String getFood_type_id ()
    {
        return food_type_id;
    }

    public void setFood_type_id (String food_type_id)
    {
        this.food_type_id = food_type_id;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getIs_favourite ()
    {
        return is_favourite;
    }

    public void setIs_favourite (String is_favourite)
    {
        this.is_favourite = is_favourite;
    }

    public String getFood_image ()
    {
        return food_image;
    }

    public void setFood_image (String food_image)
    {
        this.food_image = food_image;
    }

    public String getFood_item_id ()
    {
        return food_item_id;
    }

    public void setFood_item_id (String food_item_id)
    {
        this.food_item_id = food_item_id;
    }

    public String getNew_date_end ()
    {
        return new_date_end;
    }

    public void setNew_date_end (String new_date_end)
    {
        this.new_date_end = new_date_end;
    }

    public String getIs_special ()
    {
        return is_special;
    }

    public void setIs_special (String is_special)
    {
        this.is_special = is_special;
    }

    public Price[] getPrice ()
    {
        return price;
    }

    public void setPrice (Price[] price)
    {
        this.price = price;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }

    public String getBase_price ()
    {
        return base_price;
    }

    public void setBase_price (String base_price)
    {
        this.base_price = base_price;
    }

    public String getSalecount ()
    {
        return salecount;
    }

    public void setSalecount (String salecount)
    {
        this.salecount = salecount;
    }

    public String getFood_desc ()
    {
        return food_desc;
    }

    public void setFood_desc (String food_desc)
    {
        this.food_desc = food_desc;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getFood_section_id ()
    {
        return food_section_id;
    }

    public void setFood_section_id (String food_section_id)
    {
        this.food_section_id = food_section_id;
    }

    public String getFood_item_name ()
    {
        return food_item_name;
    }

    public void setFood_item_name (String food_item_name)
    {
        this.food_item_name = food_item_name;
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
        return "ClassPojo [food_type_id = "+food_type_id+", restaurant_id = "+restaurant_id+", is_favourite = "+is_favourite+", food_image = "+food_image+", food_item_id = "+food_item_id+", new_date_end = "+new_date_end+", is_special = "+is_special+", price = "+price+", cat_id = "+cat_id+", base_price = "+base_price+", salecount = "+salecount+", food_desc = "+food_desc+", _id = "+_id+", food_section_id = "+food_section_id+", food_item_name = "+food_item_name+", status = "+status+"]";
    }
}
			
			