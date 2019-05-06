package com.digitaldestino.modelClass.add_cart;

public class Upserted
{
    private String index;

    private String _id;

    public String getIndex ()
    {
        return index;
    }

    public void setIndex (String index)
    {
        this.index = index;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [index = "+index+", _id = "+_id+"]";
    }
}