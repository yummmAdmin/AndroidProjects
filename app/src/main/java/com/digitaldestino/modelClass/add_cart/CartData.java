package com.digitaldestino.modelClass.add_cart;

public class CartData
{
    private Upserted[] upserted;

    private String n;

    private String ok;

    private String nModified;

    public Upserted[] getUpserted ()
    {
        return upserted;
    }

    public void setUpserted (Upserted[] upserted)
    {
        this.upserted = upserted;
    }

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
        return "ClassPojo [upserted = "+upserted+", n = "+n+", ok = "+ok+", nModified = "+nModified+"]";
    }
}
			
