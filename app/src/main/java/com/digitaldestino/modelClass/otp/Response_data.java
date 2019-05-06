package com.digitaldestino.modelClass.otp;

public class Response_data
{
    private String OTP;

    public String getOTP ()
    {
        return OTP;
    }

    public void setOTP (String OTP)
    {
        this.OTP = OTP;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OTP = "+OTP+"]";
    }
}