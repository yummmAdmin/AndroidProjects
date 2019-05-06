package com.digitaldestino.modelClass.delete_cart;

public class CartData {
    private String cart_date_time;

    private String price;

    private String food_item_id;

    private String _id;

    private String choice;

    private String restaurant_id;

    private String seeker_id;

    private String instruction;

    private String qty;

    private String total_price;

    private String food_item_name;

    private String discount;

    public String getCart_date_time() {
        return cart_date_time;
    }

    public void setCart_date_time(String cart_date_time) {
        this.cart_date_time = cart_date_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getSeeker_id() {
        return seeker_id;
    }

    public void setSeeker_id(String seeker_id) {
        this.seeker_id = seeker_id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getFood_item_name() {
        return food_item_name;
    }

    public void setFood_item_name(String food_item_name) {
        this.food_item_name = food_item_name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ClassPojo [cart_date_time = " + cart_date_time + ", price = " + price + ", food_item_id = " + food_item_id + ", _id = " + _id + ", choice = " + choice + ", restaurant_id = " + restaurant_id + ", seeker_id = " + seeker_id + ", instruction = " + instruction + ", qty = " + qty + ", total_price = " + total_price + ", food_item_name = " + food_item_name + ", discount = " + discount + "]";
    }
}