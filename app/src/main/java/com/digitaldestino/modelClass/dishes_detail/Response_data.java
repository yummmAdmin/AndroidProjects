package com.digitaldestino.modelClass.dishes_detail;

public class Response_data
{
    private DishDetial dishDetial;

    public DishDetial getDishDetial ()
    {
        return dishDetial;
    }

    public void setDishDetial (DishDetial dishDetial)
    {
        this.dishDetial = dishDetial;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dishDetial = "+dishDetial+"]";
    }
}
	