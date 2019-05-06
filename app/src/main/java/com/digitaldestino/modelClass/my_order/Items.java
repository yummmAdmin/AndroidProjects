package com.digitaldestino.modelClass.my_order;

import android.os.Parcel;
import android.os.Parcelable;

public class Items implements Parcelable
{
    private String food_section_name;

    private String food_item_id;

    private String food_type_name;

    private String cat_name;

    private String price;

    private String qty;

    private String food_item_name;

    protected Items(Parcel in) {
        food_section_name = in.readString();
        food_item_id = in.readString();
        food_type_name = in.readString();
        cat_name = in.readString();
        price = in.readString();
        qty = in.readString();
        food_item_name = in.readString();
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(food_section_name);
        dest.writeString(food_item_id);
        dest.writeString(food_type_name);
        dest.writeString(cat_name);
        dest.writeString(price);
        dest.writeString(qty);
        dest.writeString(food_item_name);
    }
}
			
			