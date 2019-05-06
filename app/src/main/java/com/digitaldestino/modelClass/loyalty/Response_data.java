package com.digitaldestino.modelClass.loyalty;

public class Response_data
{
    private LoyalityData[] loyalityData;

    public LoyalityData[] getLoyalityData ()
    {
        return loyalityData;
    }

    public void setLoyalityData (LoyalityData[] loyalityData)
    {
        this.loyalityData = loyalityData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [loyalityData = "+loyalityData+"]";
    }
}