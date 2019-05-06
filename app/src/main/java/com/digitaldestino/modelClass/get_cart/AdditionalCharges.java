package com.digitaldestino.modelClass.get_cart;

public class AdditionalCharges
{
    private String cart_sum;
    private String service_tax;

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getCart_sum ()
    {
        return cart_sum;
    }

    public void setCart_sum (String cart_sum)
    {
        this.cart_sum = cart_sum;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cart_sum = "+cart_sum+"]";
    }
}
			
			