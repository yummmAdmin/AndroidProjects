package com.digitaldestino.modelClass.getuser;

public class UserResponse
{
    private String status;

    private Response_data response_data;

    private String msg;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Response_data getResponse_data ()
    {
        return response_data;
    }

    public void setResponse_data (Response_data response_data)
    {
        this.response_data = response_data;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", response_data = "+response_data+", msg = "+msg+"]";
    }
}
