package com.digitaldestino.modelClass.get_cart;

import java.util.ArrayList;

public class Response_data
{
    private AdditionalCharges additionalCharges;


    private ArrayList<CartData>cartData;

    public ArrayList<CartData> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<CartData> cartData) {
        this.cartData = cartData;
    }

    public AdditionalCharges getAdditionalCharges ()
    {
        return additionalCharges;
    }

    public void setAdditionalCharges (AdditionalCharges additionalCharges)
    {
        this.additionalCharges = additionalCharges;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [additionalCharges = "+additionalCharges+", cartData = "+cartData+"]";
    }
}
