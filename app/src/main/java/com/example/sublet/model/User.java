package com.example.sublet.model;

import java.util.ArrayList;
import java.util.List;

public class User {
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
}
