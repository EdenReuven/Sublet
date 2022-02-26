package com.example.sublet.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Parcelable {
    final public static String COLLECTION_NAME = "user";
    String fullName = "";
    String userName = "";
    String email = "";
    String phone = "";
    String password = "";
    String nickName="";
    List<String> postListId = new ArrayList<String>();
    private String profileUrl;

    public User(){}

    public User(String fullName, String userName, String email, String phone, String password,ArrayList<String> postListId) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.postListId = postListId;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        userName = in.readString();
        email = in.readString();
        phone = in.readString();
        password = in.readString();
        postListId = Collections.singletonList(in.readString());
        userName = in.readString();
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

    public List<String> getPostList() {
        return postListId;
    }

    public void setPostList(ArrayList<String> postListId) {
        this.postListId = postListId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
        dest.writeString(String.valueOf(postListId));
        dest.writeString(nickName);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("userName",userName);
        json.put("fullName",fullName);
        json.put("email",email);
        json.put("phone",phone);
        json.put("postList",postListId);
        json.put("password","");
        json.put("profileUrl",profileUrl);
        json.put("nickName", nickName);
        return json;
    }

    public static User create(Map<String, Object> json) {
        String userName = (String) json.get("userName");
        String fullName = (String) json.get("fullName");
        String password = (String) json.get("password");
        String email = (String) json.get("email");
        String phone = (String) json.get("phone");
        String url = (String)json.get("profileUrl");
        List<String> postListId = (List<String>) json.get("postList");
        String nickName=(String) json.get("nickName");

        User user = new User(fullName,userName,email,phone,password, (ArrayList<String>) postListId);
        user.setProfileUrl(url);
        user.setNickName(nickName);
        return user;
    }

    public void setProfileUrl(String url) {
        this.profileUrl= url;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
