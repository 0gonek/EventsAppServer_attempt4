package com.ogonek.eventsappserver.social;

public class UserVKLogin {
    private ResponseVkLogin[] response;

    public ResponseVkLogin[] getResponse ()
    {
        return response;
    }

    public void setResponse (ResponseVkLogin[] response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
