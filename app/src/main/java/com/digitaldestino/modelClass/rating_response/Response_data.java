package com.digitaldestino.modelClass.rating_response;

public class Response_data
{
    private Feedback[] feedback;

    public Feedback[] getFeedback ()
    {
        return feedback;
    }

    public void setFeedback (Feedback[] feedback)
    {
        this.feedback = feedback;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [feedback = "+feedback+"]";
    }
}
			
			