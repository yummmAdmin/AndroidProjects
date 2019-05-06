package com.digitaldestino.modelClass.add_card;

public class Response_data
{
    private Request request;

    public Request getRequest ()
    {
        return request;
    }

    public void setRequest (Request request)
    {
        this.request = request;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [request = "+request+"]";
    }
}
			
	