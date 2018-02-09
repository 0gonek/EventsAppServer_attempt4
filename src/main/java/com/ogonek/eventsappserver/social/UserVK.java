package com.ogonek.eventsappserver.social;

public class UserVK {
    private ResponseVK[] response;

    public ResponseVK[] getResponse ()
    {
        return response;
    }

    public void setResponse (ResponseVK[] response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
