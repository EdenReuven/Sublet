package com.example.sublet.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sublet.MyApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    public static final Model instance = new Model();
    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public interface SaveImageListener{
        void onComplete(String url);
    }

    public void saveimage(Bitmap imageBitmap, String imageName, SaveImageListener listener) {


    }

    public enum PostsListLoadingState {
        loading,
        loaded
    }

    MutableLiveData<PostsListLoadingState>postsListLoadingState= new MutableLiveData<PostsListLoadingState>();

    public LiveData<PostsListLoadingState> getPostsListLoadingState() {
        return postsListLoadingState;
    }

    ModelFirebase modelFirebase =new ModelFirebase();

    private Model() {
        postsListLoadingState.setValue(PostsListLoadingState.loaded);
    }



    List<User> usersList = new LinkedList<>();
    //List<Post> postListCurrentUser = new LinkedList<>();
    User currentUser = null;

    public interface GetAllUsersListener{
        void onComplete(List<User> postList);
    }

    public void getAllUsers(GetAllUsersListener listener) {
        modelFirebase.getAllUsers(listener);
//        return usersList;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){ //TODO : set current user fireBaseModel
        currentUser = user;
    }

    public boolean userExists(String userName,String password){
        for (int i =0;i<usersList.size();i++){
            if(usersList.get(i).getUserName().equals(userName) && usersList.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void addUser(User newUser,AddUserListener listener) {
        modelFirebase.addUser(newUser,listener);
//        usersList.add(newUser);
    }

    public void addPostToCurrentUser(Post newPost){
//        currentUser.getPostList().add(newPost);
    }

    public interface GetUserListener{
        void onComplete(User user);
    }

    public void getUser(String userName,GetUserListener listener){
        modelFirebase.getUser(userName,listener);
    }

    public interface createUserWithEmailAndPasswordListener{
        void onComplete();
    }

    public void createUserWithEmailAndPassword(String email,String password,createUserWithEmailAndPasswordListener listener){
        modelFirebase.createUserWithEmailAndPassword(email,password,listener);
    }

    public interface signInWithEmailAndPasswordListener{
        void onComplete();
    }

    public void signInWithEmailAndPasswordListener(String email,String password,signInWithEmailAndPasswordListener listener){
        modelFirebase.signInWithEmailAndPassword(email,password,listener);
    }

    public void signOut(){
        modelFirebase.signOut();
    }

    ////////////////////////////////////***POST***//////////////////////////////////////////////

    ArrayList<Post> userPostList = new ArrayList<>();
    String currentPostId;



   /* public void getAllPosts(GetAllPostsListener listener) {
        modelFirebase.getAllPosts( listener);
        *//*executor.execute(() -> {
            List<Post> postList = AppLocalDb.db.postDao().getAllPost();
            mainThread.post(() -> {
                listener.onComplete(postList);
            });
        });*//*

    }*/

    MutableLiveData <List<Post>> postsList = new MutableLiveData<List<Post>>();
    public LiveData <List<Post>> getAll (){
        if (postsList.getValue()==null){refreshPostList();}
        return postsList;
    }

    public void refreshPostList(){
        postsListLoadingState.setValue(PostsListLoadingState.loading);

        Long postLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostLastUpdate", 0);

        modelFirebase.getAllPosts(postLastUpdate, new ModelFirebase.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> postList) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lastUpdateDate=new Long(0);
                        Log.d("TAG", "fb returned" +postList.size());
                        for(Post post:postList){
                            AppLocalDb.db.postDao().insertAll(post);
                            if(lastUpdateDate <post.getUpdateDate()){
                                lastUpdateDate=post.getUpdateDate();
                            }
                        }
                        MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                                .edit().putLong("PostLastUpdate",lastUpdateDate).commit();
                        List<Post>poList=AppLocalDb.db.postDao().getAllPost();
                        postsList.postValue(poList);
                        postsListLoadingState.postValue(PostsListLoadingState.loaded);
                    }
                });




            }
        });
        //TODO: DELETE
//        modelFirebase.getAllPosts(postLastUpdate, new ModelFirebase.GetAllPostsListener() {
//            @Override
//            public void onComplete(List<Post> postList) {
//                postsList.setValue(postList);
//                postsListLoadingState.setValue(PostsListLoadingState.loaded);
//            }
//        });
    }

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
}
