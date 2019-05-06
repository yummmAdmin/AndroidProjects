package com.digitaldestino.modelClass.rating_response;

public class Feedback
{
    private String seeker_id;

    private String food_rating;

    private String delivery_rating;

    private String restaurant_id;

    private String review;

    private String _id;

    private String service_rating;

    private String feedback_date_time;

    private String order_id;

    private String env_rating;

    private String status;

    public String getSeeker_id ()
    {
        return seeker_id;
    }

    public void setSeeker_id (String seeker_id)
    {
        this.seeker_id = seeker_id;
    }

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

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public String getReview ()
    {
        return review;
    }

    public void setReview (String review)
    {
        this.review = review;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getService_rating ()
    {
        return service_rating;
    }

    public void setService_rating (String service_rating)
    {
        this.service_rating = service_rating;
    }

    public String getFeedback_date_time ()
    {
        return feedback_date_time;
    }

    public void setFeedback_date_time (String feedback_date_time)
    {
        this.feedback_date_time = feedback_date_time;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getEnv_rating ()
    {
        return env_rating;
    }

    public void setEnv_rating (String env_rating)
    {
        this.env_rating = env_rating;
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
        return "ClassPojo [seeker_id = "+seeker_id+", food_rating = "+food_rating+", delivery_rating = "+delivery_rating+", restaurant_id = "+restaurant_id+", review = "+review+", _id = "+_id+", service_rating = "+service_rating+", feedback_date_time = "+feedback_date_time+", order_id = "+order_id+", env_rating = "+env_rating+", status = "+status+"]";
    }
}
			
			