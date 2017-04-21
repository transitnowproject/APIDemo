package com.example.angel.apidemo.APIConfiguration;

/**
 * Created by angel on 2017-04-19.
 */

public class ApiConfiguration {

    private String api = "http://192.168.57.210:8088/api/";
    private String imageURL = "http://192.168.57.210:8088/UserImages/";

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
