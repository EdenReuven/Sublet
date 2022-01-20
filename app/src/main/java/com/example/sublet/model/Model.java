package com.example.sublet.model;

import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<1;i++){
//            Post post = new Post("10/10/2010"+i,"12/10/2010"+i,3,"Ramla"+i,4,120,
//                    "post:myPost",2,3,currentDate,getCurrentUser()+"i");
//            postList.add(post);
            User user = new User("myName"+i,""+i,"myEmail@"+i,"myPhone"+i,""+i, userPostList);
            usersList.add(user);
        }
    }

    Date currentDate = Calendar.getInstance().getTime(); // bring today date and always = 0 days ago because the db is not correct for now
    List<User> usersList = new LinkedList<>();
    List<Post> postList = new LinkedList<>();
    User currentUser = null;
    ArrayList<Post> userPostList = new ArrayList<>();
    String currentPostId = "";

    public List<Post> getAllPosts() {
        return postList;
    }
    public Post getPost(int pos) {
        return postList.get(pos);
    }
    public Post getPost(String posId) {
        for (int i=0;i<postList.size();i++){
            if(postList.get(i).getPostId().equals(posId))
                return postList.get(i);
        }
        return null;
    }
    public void addPost(Post newPost) {
        postList.add(newPost);
    }

    public void deletePost(String postId) {
        for (int i=0;i<postList.size();i++){
            if(postList.get(i).getPostId().equals(postId))
                postList.remove(postList.get(i));
        }
    }

    public void setCurrentPostId(String postId){
        currentPostId = postId;
    }

    public String getCurrentPostId(){
        return currentPostId;
    }

    public boolean containPostId(List<Post> postList,String postId){
        for(int i=0; i< postList.size();i++){
            if(postList.get(i).getPostId().equals(postId))
                return true;
        }
        return false;
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
