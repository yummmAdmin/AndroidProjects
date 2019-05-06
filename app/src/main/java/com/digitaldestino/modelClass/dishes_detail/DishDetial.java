package com.digitaldestino.modelClass.dishes_detail;

import java.util.ArrayList;

public class DishDetial
{
    private String food_type_id;

    private String base_price;

    private String food_section_id;

    private String restaurant_id;

    private String food_desc;

    private String food_image;

    private String food_item_name;

    private String cat_id;

    public String getSalecount() {
        return salecount;
    }

    public void setSalecount(String salecount) {
        this.salecount = salecount;
    }

    private String salecount;

    public ArrayList<Price> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Price> price) {
        this.price = price;
    }

    private ArrayList<Price>price;

    private String _id;

    private String food_item_id;

    private String new_date_end;

    private String is_special;

    public String getFood_type_id ()
    {
        return food_type_id;
    }

    public void setFood_type_id (String food_type_id)
    {
        this.food_type_id = food_type_id;
    }

    public String getBase_price ()
    {
        return base_price;
    }

    public void setBase_price (String base_price)
    {
        this.base_price = base_price;
    }

    public String getFood_section_id ()
    {
        return food_section_id;
    }

    public void setFood_section_id (String food_section_id)
    {
        this.food_section_id = food_section_id;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getFood_desc ()
    {
        return food_desc;
    }

    public void setFood_desc (String food_desc)
    {
        this.food_desc = food_desc;
    }

    public String getFood_image ()
    {
        return food_image;
    }

    public void setFood_image (String food_image)
    {
        this.food_image = food_image;
    }

    public String getFood_item_name ()
    {
        return food_item_name;
    }

    public void setFood_item_name (String food_item_name)
    {
        this.food_item_name = food_item_name;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }


    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
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

    @Override
    public String toString()
    {
        return "ClassPojo [food_type_id = "+food_type_id+", base_price = "+base_price+", food_section_id = "+food_section_id+", restaurant_id = "+restaurant_id+", food_desc = "+food_desc+", food_image = "+food_image+", food_item_name = "+food_item_name+", cat_id = "+cat_id+", price = "+price+", _id = "+_id+", food_item_id = "+food_item_id+", new_date_end = "+new_date_end+", is_special = "+is_special+"]";
    }
}