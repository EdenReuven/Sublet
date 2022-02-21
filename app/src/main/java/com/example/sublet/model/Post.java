package com.example.sublet.model;
//TODO: connection the user to post list

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Post implements Parcelable {
    final public static String COLLECTION_NAME = "post";
    int generatePostId = 0;
    @PrimaryKey
    @NonNull
    String postId = "";
    String fromDate = "";
    String toDate = "";
    int numRoommate = 0;  // current num of people
    String location = "";
    int overallPeople = 0;
    float price = 0;
    String postContent = "";
    int numOfBathroom = 0;
    int numOfBedroom = 0;
    private String postImgUrl;
    boolean isDeleted = false;

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    Long updateDate=new Long(0);
    //    Date createDate = null;
    //    List<Image> images = null;
    public Post(){}

    public Post(String fromDate, String toDate, int numRoommate,
                String location, int overallPeople, float price, String postContent,
                int numOfBathroom, int numOfBedroom ,String postId,boolean isDeleted) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numRoommate = numRoommate;
        this.location = location;
        this.overallPeople = overallPeople;
        this.price = price;
        this.postContent = postContent;
        this.numOfBathroom = numOfBathroom;
        this.numOfBedroom = numOfBedroom;
        this.postId = postId;
        this.isDeleted = isDeleted;
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
        postId = in.readString();
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPostImgUrl() {
        return postImgUrl;
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
        dest.writeString(postId);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id" ,postId);
        json.put("fromDate" ,fromDate);
        json.put("toDate" ,toDate);
        json.put("numRoommate" ,numRoommate);
        json.put("location" ,location);
        json.put("overallPeople" ,overallPeople);
        json.put("price" ,price);
        json.put("postContent" ,postContent);
        json.put("numOfBathroom" ,numOfBathroom);
        json.put("numOfBedroom" ,numOfBedroom);
        json.put("postImgUrl" ,postImgUrl);
        json.put("updateDate" , FieldValue.serverTimestamp());
        json.put("isDeleted",isDeleted);
        return json;
    }

    public static Post create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String fromDate = (String) json.get("fromDate");
        String toDate = (String) json.get("toDate");
        Long numRoommate = (Long) json.get("numRoommate");
        String location = (String) json.get("location");
        Long overallPeople = (Long) json.get("overallPeople");
        Double price = (Double) json.get("price");
        String postContent = (String) json.get("postContent");
        Long numOfBathroom = (Long) json.get("numOfBathroom");
        Long numOfBedroom = (Long) json.get("numOfBedroom");
        String url = (String) json.get("postImgUrl");
        boolean isDeleted = (boolean)json.get("isDeleted");
        Post post = new Post(fromDate,toDate,numRoommate.intValue(),location,overallPeople.intValue()
        ,price.intValue(),postContent,numOfBathroom.intValue(),numOfBedroom.intValue(),id,isDeleted);
        Timestamp ts=(Timestamp)json.get("updateDate");
        Long updateDate = ts.getSeconds();
        post.setUpdateDate(updateDate);
        post.setPostImgUrl(url);
        return post;
    }
    //  TODO:...
    public Long getUpdateDate() {
        return updateDate;
    }

    public void setPostImgUrl(String url) {
        this.postImgUrl =url;
    }
}
