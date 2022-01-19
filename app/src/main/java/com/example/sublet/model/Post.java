package com.example.sublet.model;
//TODO: connection the user to post list

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Post implements Parcelable {
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
    Date createDate = null;
    String userName = "";

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

    protected Post(Parcel in) {
        fromDate = in.readString();
        toDate = in.readString();
        numRoommate = in.readInt();
        location = in.readString();
        overallPeople = in.readInt();
        price = in.readFloat();
        postContent = in.readString();
        numOfBathroom = in.readInt();
        numOfBedroom = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    //Peaceable send obj to fragment

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeInt(numRoommate);
        dest.writeString(location);
        dest.writeInt(overallPeople);
        dest.writeFloat(price);
        dest.writeString(postContent);
        dest.writeInt(numOfBathroom);
        dest.writeInt(numOfBedroom);
        dest.writeString(createDate.toString());
    }
}
