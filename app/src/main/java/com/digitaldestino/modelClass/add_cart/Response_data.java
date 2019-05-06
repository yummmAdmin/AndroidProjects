package com.digitaldestino.modelClass.add_cart;

public class Response_data
{
    private CartData cartData;

    public CartData getCartData ()
    {
        return cartData;
    }

    public void setCartData (CartData cartData)
    {
        this.cartData = cartData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cartData = "+cartData+"]";
    }
}