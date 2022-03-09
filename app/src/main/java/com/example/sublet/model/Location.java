package com.example.sublet.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class Location {
    final public static String COLLECTION_NAME = "location";
    String postId = "";
    float latitude = 0;
    float longitude = 0;

    public Location(){}

    public Location(float latitude,float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id" ,postId);
        json.put("latitude" ,latitude);
        json.put("longitude" ,longitude);
        return json;
    }

    public static Location create(Map<String, Object> json) {
        String id = (String) json.get("id");
        float latitude = (float) json.get("latitude");
        float longitude = (float) json.get("longitude");
        Location location = new Location(latitude,longitude);
        return location;
    }
}
