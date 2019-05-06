package com.digitaldestino.modelClass.pay;

public class Items
{
    private String food_section_name;

    private String food_item_id;

    private String food_type_name;

    private String cat_name;

    private String price;

    private String qty;

    private String food_item_name;

    public String getFood_section_name ()
    {
        return food_section_name;
    }

    public void setFood_section_name (String food_section_name)
    {
        this.food_section_name = food_section_name;
    }

    public String getFood_item_id ()
    {
        return food_item_id;
    }

    public void setFood_item_id (String food_item_id)
    {
        this.food_item_id = food_item_id;
    }

    public String getFood_type_name ()
    {
        return food_type_name;
    }

    public void setFood_type_name (String food_type_name)
    {
        this.food_type_name = food_type_name;
    }

    public String getCat_name ()
    {
        return cat_name;
    }

    public void setCat_name (String cat_name)
    {
        this.cat_name = cat_name;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getQty ()
    {
        return qty;
    }

    public void setQty (String qty)
    {
        this.qty = qty;
    }

    public String getFood_item_name ()
    {
        return food_item_name;
    }

    public void setFood_item_name (String food_item_name)
    {
        this.food_item_name = food_item_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [food_section_name = "+food_section_name+", food_item_id = "+food_item_id+", food_type_name = "+food_type_name+", cat_name = "+cat_name+", price = "+price+", qty = "+qty+", food_item_name = "+food_item_name+"]";
    }
}