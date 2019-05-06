package com.digitaldestino.modelClass.logout;

public class User
{
    private String n;

    private String ok;

    private String nModified;

    public String getN ()
    {
        return n;
    }

    public void setN (String n)
    {
        this.n = n;
    }

    public String getOk ()
    {
        return ok;
    }

    public void setOk (String ok)
    {
        this.ok = ok;
    }

    public String getNModified ()
    {
        return nModified;
    }

    public void setNModified (String nModified)
    {
        this.nModified = nModified;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [n = "+n+", ok = "+ok+", nModified = "+nModified+"]";
    }
}
			
		