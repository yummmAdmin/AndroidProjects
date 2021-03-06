package com.digitaldestino.modelClass.signup;

public class SignUpResponse
{
    private String status;

    private Response_data response_data;

    private String msg;

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
