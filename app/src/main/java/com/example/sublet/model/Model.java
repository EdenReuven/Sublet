package com.example.sublet.model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<5;i++){
//            Post post = new Post("from date" + 00/00/2020,"to date" + 10/10/2020,null,i
//            ,"location - tel-aviv"+i, i,i,"postContect - blabla" +i, i,i);
//            Log.d("TAG",post.postContent);
//            postList.add(post);
        }
    }

    List<User> usersList = new LinkedList<>();
    List<Post> postList = new LinkedList<>();

    //TODO: function for user + post

    public List<Post> getAllPosts() {
        return postList;
    }
}
