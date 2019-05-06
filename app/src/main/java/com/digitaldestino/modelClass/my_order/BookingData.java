package com.digitaldestino.modelClass.my_order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookingData implements Parcelable {
    private String order_timezone;

    private String total;

    private String order_date_time;

    private String total_price;

    private String service_tax;

    private String order_address;

    private String name;

    private String discount;
   private String restaurant_id;

    public String getRes_image() {
        return res_image;
    }

    public void setRes_image(String res_image) {
        this.res_image = res_image;
    }

    public static Creator<BookingData> getCREATOR() {
        return CREATOR;
    }

    private String res_image;
    private String _id;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    protected BookingData(Parcel in) {
        order_timezone = in.readString();
        total = in.readString();
        order_date_time = in.readString();
        total_price = in.readString();
        service_tax = in.readString();
        order_address = in.readString();
        name = in.readString();
        discount = in.readString();
        _id = in.readString();
        status = in.readString();
        restaurant_id=in.readString();
        res_image=in.readString();
    }

    public static final Creator<BookingData> CREATOR = new Creator<BookingData>() {
        @Override
        public BookingData createFromParcel(Parcel in) {
            return new BookingData(in);
        }

        @Override
        public BookingData[] newArray(int size) {
            return new BookingData[size];
        }
    };

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    private ArrayList<Items> items;

    private String status;

    public String getOrder_timezone() {
        return order_timezone;
    }

    public void setOrder_timezone(String order_timezone) {
        this.order_timezone = order_timezone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public void setOrder_date_time(String order_date_time) {
        this.order_date_time = order_date_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [order_timezone = " + order_timezone + ", total = " + total + ", order_date_time = " + order_date_time + ", total_price = " + total_price + ", service_tax = " + service_tax + ", order_address = " + order_address + ", name = " + name + ", discount = " + discount + ", _id = " + _id + ", items = " + items + ", status = " + status + ",res_image = " + res_image + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_timezone);
        dest.writeString(total);
        dest.writeString(order_date_time);
        dest.writeString(total_price);
        dest.writeString(service_tax);
        dest.writeString(order_address);
        dest.writeString(name);
        dest.writeString(discount);
        dest.writeString(_id);
        dest.writeString(status);
        dest.writeString(restaurant_id);
        dest.writeString(res_image);
    }
}
	