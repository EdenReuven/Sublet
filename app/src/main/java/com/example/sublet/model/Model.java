package com.example.sublet.model;

import android.util.Log;

import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<1;i++){
            Post post = new Post("10/10/2010"+i,"12/10/2010"+i,3,"Ramla"+i,4,120,
                    "post:myPost",2,3,currentDate);
            postList.add(post);
            User user = new User("myName"+i,""+i,"myEmail@"+i,"myPhone"+i,""+i,null);
            usersList.add(user);
        }
    }

    Date currentDate = Calendar.getInstance().getTime(); // bring today date and always = 0 days ago because the db is not correct for now
    List<User> usersList = new LinkedList<>();
    List<Post> postList = new LinkedList<>();
    User currentUser = null;

    //TODO: function for user + post

    public List<Post> getAllPosts() {
        return postList;
    }
    public Post getPost(int pos) {
        return postList.get(pos);
    }
    public void addPost(Post newPost) {
        postList.add(newPost);
    }
    public List<User> getAllUsers() {
        return usersList;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(String userName){
        for (int i=0;i<usersList.size();i++){
            if(usersList.get(i).getUserName().equals(userName))
                currentUser = usersList.get(i);
        }
    }

//    public User getUser(int pos) {
//        return usersList.get(pos);
//    }
    public boolean userExists(String userName,String password){
        for (int i =0;i<usersList.size();i++){
            if(usersList.get(i).getUserName().equals(userName) && usersList.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }

    public void addUser(User newUser) {
        usersList.add(newUser);
    }
}
