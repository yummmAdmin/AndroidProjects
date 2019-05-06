package com.digitaldestino.modelClass.get_feedback;

public class FeedbackData
{
    private String food_rating;

    private String delivery_rating;

    private String restaurant_id;

    private String review;

    private String last_name;

    private String _id;

    private String service_rating;

    private String order_id;

    private String first_name;

    private String env_rating;

    private String restaurant_name;

    public String getFeedback_date_time() {
        return feedback_date_time;
    }

    public void setFeedback_date_time(String feedback_date_time) {
        this.feedback_date_time = feedback_date_time;
    }

    private  String feedback_date_time;

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

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
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

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getEnv_rating ()
    {
        return env_rating;
    }

    public void setEnv_rating (String env_rating)
    {
        this.env_rating = env_rating;
    }

    public String getRestaurant_name ()
    {
        return restaurant_name;
    }

    public void setRestaurant_name (String restaurant_name)
    {
        this.restaurant_name = restaurant_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [food_rating = "+food_rating+", delivery_rating = "+delivery_rating+", restaurant_id = "+restaurant_id+", review = "+review+", last_name = "+last_name+", _id = "+_id+", service_rating = "+service_rating+", order_id = "+order_id+", first_name = "+first_name+", env_rating = "+env_rating+", restaurant_name = "+restaurant_name+"]";
    }
}
			
			