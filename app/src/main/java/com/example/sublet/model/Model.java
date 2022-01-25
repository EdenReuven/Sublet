package com.example.sublet.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import androidx.core.os.HandlerCompat;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase =new ModelFirebase();

    private Model() {
        for(int i=0;i<1;i++){
            User user = new User("myName"+i,""+i,"myEmail@"+i,"myPhone"+i,""+i, null); //userPostList
            usersList.add(user);
        }
    }

    List<User> usersList = new LinkedList<>();
    //List<Post> postListCurrentUser = new LinkedList<>();
    User currentUser = null;

    public List<User> getAllUsers() {
        modelFirebase.getAllUsers();
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
        modelFirebase.addUser(newUser);
        usersList.add(newUser);
    }

    public void addPostToCurrentUser(Post newPost){
        currentUser.getPostList().add(newPost);
    }

    ////////////////////////////////////***POST***//////////////////////////////////////////////

    ArrayList<Post> userPostList = new ArrayList<>();
    String currentPostId;

    public interface GetAllPostsListener{
        void onComplete(List<Post> postList);
    }

    public void getAllPosts(GetAllPostsListener listener) {
        modelFirebase.getAllPosts( listener);
        /*executor.execute(() -> {
            List<Post> postList = AppLocalDb.db.postDao().getAllPost();
            mainThread.post(() -> {
                listener.onComplete(postList);
            });
        });*/

    }

//    public interface GetPostsListener{
//        void onComplete(Post post);
//    }
//
//    public void getPost(int pos,GetPostsListener listener) {
//        modelFirebase.getPost(pos,listener);
//        executor.execute(() -> {
//            List<Post> postList = AppLocalDb.db.postDao().getAllPost();
//            Post post = postList.get(pos);
//            mainThread.post(() -> {
//                listener.onComplete(post);
//            });
//        });
//    }

    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post newPost,AddPostListener listener) {
        modelFirebase.addPost(newPost, listener);
        /*executor.execute(() -> {
            AppLocalDb.db.postDao().insertAll(newPost);
            mainThread.post(() -> {
                listener.onComplete();
            });
        });*/
    }


    public interface GetPostByIdListener{
        void onComplete(Post post);
    }
    public void getPostById(String postId,GetPostByIdListener listener){
        modelFirebase.getPostById(postId, listener);
        /*executor.execute(() -> {
            List<Post> postList = AppLocalDb.db.postDao().getAllPost();
            for (int i=0;i<postList.size();i++){
                if(postList.get(i).getPostId().equals(postId)){
                    Post post = postList.get(i);
                    mainThread.post(() -> {
                        listener.onComplete(post);
                    });
                }
            }
        });*/
    }
    public interface DeletePostsListener{
        void onComplete();
    }

    public void deletePost(String postId,DeletePostsListener listener) {
        modelFirebase.deletePost(postId,listener);
        /*executor.execute(() -> {
            List<Post> postList = AppLocalDb.db.postDao().getAllPost();
            for (int i=0;i<postList.size();i++){
                if(postList.get(i).getPostId().equals(postId)){
                    Post p = postList.get(i);
                    AppLocalDb.db.postDao().deletePost(p);
                    mainThread.post(() -> {
                        listener.onComplete();
                    });
                }
            }
        });*/
    }

    public String getGeneratePostId(){
        String postID = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        postID = postID+"-"+currentUser.getUserName();
        Log.d("TAG",postID);
        return postID;
    }

    public void setCurrentPostId(String postId){
        currentPostId = postId;
    }

    public String getCurrentPostId(){
        return currentPostId;
    }


}
