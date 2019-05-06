package com.digitaldestino.modelClass.apply_coupan;

public class Response_data
{
    private Promocode promocode;

    public Promocode getPromocode ()
    {
        return promocode;
    }

    public void setPromocode (Promocode promocode)
    {
        this.promocode = promocode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [promocode = "+promocode+"]";
    }
}
	