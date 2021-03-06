package com.example.sublet.model;

import java.util.HashMap;
import java.util.Map;

public class Location {
    final public static String COLLECTION_NAME = "location";
    String postId = "";
    double latitude = 0;
    double longitude = 0;

    public Location(){}

    public Location(double latitude,double longitude,String postId){
        this.latitude = latitude;
        this.longitude = longitude;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("latitude" ,latitude);
        json.put("longitude" ,longitude);
        json.put("postId",postId);
        return json;
    }

    public static Location create(Map<String, Object> json) {
        Double latitude = (double) json.get("latitude");
        Double longitude = (double) json.get("longitude");
        String postId = (String) json.get("postId");
        Location location = new Location(latitude,longitude,postId);
        return location;
    }
}
