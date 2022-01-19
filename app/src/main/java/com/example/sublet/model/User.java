package com.example.sublet.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    String fullName = "";
    String userName = "";
    String email = "";
    String phone = "";
    String password = "";
    ArrayList<Post> postList = new ArrayList<>();
    //Image

    public User(){}

    public User(String fullName, String userName, String email, String phone, String password,ArrayList<Post> postList) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.postList = postList;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        userName = in.readString();
        email = in.readString();
        phone = in.readString();
        password = in.readString();
        postList = in.createTypedArrayList(Post.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeTypedList(postList);
    }
}
