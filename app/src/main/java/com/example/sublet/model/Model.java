package com.example.sublet.model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<5;i++){
            Post post = new Post("from"+10/10/2010,"to"+12/10/2010,2,"location: Ramla",4,120,
                    "post:myPost",2,3);
            postList.add(post);
        }
    }

    List<User> usersList = new LinkedList<>();
    List<Post> postList = new LinkedList<>();

    //TODO: function for user + post

    public List<Post> getAllPosts() {
        return postList;
    }
}
