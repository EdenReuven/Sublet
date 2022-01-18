package com.example.sublet.model;

import android.media.Image;

import java.util.Date;
import java.util.List;
public class Post {
    String fromDate = "";
    String toDate = "";
//    List<Image> images = null;
    int numRoommate = 0;  // current num of people
    String location = "";
    int overallPeople = 0;
    float price = 0;
    String postContent = "";
    int numOfBathroom = 0;
    int numOfBedroom = 0;
    private Date createDate = null;

    public Post(){}

    public Post(String fromDate, String toDate, int numRoommate,
                String location, int overallPeople, float price, String postContent,
                int numOfBathroom, int numOfBedroom, Date createDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numRoommate = numRoommate;
        this.location = location;
        this.overallPeople = overallPeople;
        this.price = price;
        this.postContent = postContent;
        this.numOfBathroom = numOfBathroom;
        this.numOfBedroom = numOfBedroom;
        this.createDate = createDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getNumRoommate() {
        return numRoommate;
    }

    public void setNumRoommate(int numRoommate) {
        this.numRoommate = numRoommate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOverallPeople() {
        return overallPeople;
    }

    public void setOverallPeople(int overallPeople) {
        this.overallPeople = overallPeople;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getNumOfBathroom() {
        return numOfBathroom;
    }

    public void setNumOfBathroom(int numOfBathroom) {
        this.numOfBathroom = numOfBathroom;
    }

    public int getNumOfBedroom() {
        return numOfBedroom;
    }

    public void setNumOfBedroom(int numOfBedroom) {
        this.numOfBedroom = numOfBedroom;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
