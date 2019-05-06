package com.digitaldestino.modelClass.get_feedback;

public class FeedbackDataCount
{
    private String food_rating;

    private String delivery_rating;

    private String reviews;

    private String ratings;

    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String service_rating;

    private String env_rating;

    public String getFood_rating ()
    {
        return food_rating;
    }

    public void setFood_rating (String food_rating)
    {
        this.food_rating = food_rating;
    }

    public String getDelivery_rating ()
    {
        return delivery_rating;
    }

    public void setDelivery_rating (String delivery_rating)
    {
        this.delivery_rating = delivery_rating;
    }

    public String getReviews ()
    {
        return reviews;
    }

    public void setReviews (String reviews)
    {
        this.reviews = reviews;
    }

    public String getRatings ()
    {
        return ratings;
    }

    public void setRatings (String ratings)
    {
        this.ratings = ratings;
    }



    public String getService_rating ()
    {
        return service_rating;
    }

    public void setService_rating (String service_rating)
    {
        this.service_rating = service_rating;
    }

    public String getEnv_rating ()
    {
        return env_rating;
    }

    public void setEnv_rating (String env_rating)
    {
        this.env_rating = env_rating;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [food_rating = "+food_rating+", delivery_rating = "+delivery_rating+", reviews = "+reviews+", ratings = "+ratings+", _id = "+_id+", service_rating = "+service_rating+", env_rating = "+env_rating+"]";
    }
}
			
			