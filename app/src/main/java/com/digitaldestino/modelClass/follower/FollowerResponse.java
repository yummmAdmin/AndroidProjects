package com.digitaldestino.modelClass.follower;

public class FollowerResponse
{private String msg;

    private Response_data response_data;

    private String key;

    private String status;

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    public Response_data getResponse_data ()
    {
        return response_data;
    }

    public void setResponse_data (Response_data response_data)
    {
        this.response_data = response_data;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [msg = "+msg+", response_data = "+response_data+", key = "+key+", status = "+status+"]";
    }
}
