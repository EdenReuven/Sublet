package com.example.sublet.model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<5;i++){
            Post post = new Post("10/10/2010"+i,"12/10/2010"+i,i,"Ramla"+i,4,120,
                    "post:myPost",2,3);
            postList.add(post);
            User user = new User("myName"+i,"myUserName"+i,"myEmail@"+i,"myPhone"+1,"myPassword"+i);
            usersList.add(user);
        }
    }

    List<User> usersList = new LinkedList<>();
    List<Post> postList = new LinkedList<>();

    //TODO: function for user + post

    public List<Post> getAllPosts() {
        return postList;
    }

    public List<User> getAllUsers() {
        return usersList;
    }
}
