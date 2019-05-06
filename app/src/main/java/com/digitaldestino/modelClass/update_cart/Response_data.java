package com.digitaldestino.modelClass.update_cart;

public class Response_data
{
    private AdditionalCharges additionalCharges;

    private UpdatecartData updatecartData;

    public AdditionalCharges getAdditionalCharges ()
    {
        return additionalCharges;
    }

    public void setAdditionalCharges (AdditionalCharges additionalCharges)
    {
        this.additionalCharges = additionalCharges;
    }

    public UpdatecartData getUpdatecartData ()
    {
        return updatecartData;
    }

    public void setUpdatecartData (UpdatecartData updatecartData)
    {
        this.updatecartData = updatecartData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [additionalCharges = "+additionalCharges+", updatecartData = "+updatecartData+"]";
    }
}
		