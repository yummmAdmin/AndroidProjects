package com.digitaldestino.modelClass.send_feedback;

public class Response_data
{
    private String appFeeddback;

    public String getAppFeeddback ()
    {
        return appFeeddback;
    }

    public void setAppFeeddback (String appFeeddback)
    {
        this.appFeeddback = appFeeddback;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [appFeeddback = "+appFeeddback+"]";
    }
}
	