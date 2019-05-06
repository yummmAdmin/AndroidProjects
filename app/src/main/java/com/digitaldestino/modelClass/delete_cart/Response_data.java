package com.digitaldestino.modelClass.delete_cart;


public class Response_data
{

    private CartData cartData;

    public AdditionalCharges getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(AdditionalCharges additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    private AdditionalCharges additionalCharges;

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
			
			