package com.digitaldestino.modelClass.getLoyalty;

public class Response_data
{
    private String loyalityPoints;

    public String getLoyalityPoints() {
        return loyalityPoints;
    }

    public void setLoyalityPoints(String loyalityPoints) {
        this.loyalityPoints = loyalityPoints;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [loyalityData = "+loyalityPoints+"]";
    }
}