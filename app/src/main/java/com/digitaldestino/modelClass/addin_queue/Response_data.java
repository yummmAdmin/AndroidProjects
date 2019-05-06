package com.digitaldestino.modelClass.addin_queue;

public class Response_data
{
    private QueueData[] queueData;

    public QueueData[] getQueueData ()
    {
        return queueData;
    }

    public void setQueueData (QueueData[] queueData)
    {
        this.queueData = queueData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [queueData = "+queueData+"]";
    }
}