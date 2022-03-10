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
    public Executor executor = Executors.newFixedThreadPool(1);
    public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());


    public interface saveProfileImageListener {
        void onComplete(String url);
    }

    public void saveProfileImage(Bitmap profileImageBitmap, String imageName, saveProfileImageListener listener) {
        modelFirebase.saveProfileImage(profileImageBitmap, imageName, listener);
    }

    public interface SaveImageListener {
        void onComplete(String url);
    }

    public void saveimage(Bitmap imageBitmap, String imageName, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap, imageName, listener);
    }

    public enum PostsListLoadingState {
        loading,
        loaded
    }

    MutableLiveData<PostsListLoadingState> postsListLoadingState = new MutableLiveData<PostsListLoadingState>();

    public LiveData<PostsListLoadingState> getPostsListLoadingState() {
        return postsListLoadingState;
    }

    ModelFirebase modelFirebase = new ModelFirebase();

    private Model() {
        postsListLoadingState.setValue(PostsListLoadingState.loaded);
    }


    List<User> usersList = new LinkedList<>();
    User currentUser = null;

    public interface GetAllUsersListener {
        void onComplete(List<User> postList);
    }

    public void getAllUsers(GetAllUsersListener listener) {
        modelFirebase.getAllUsers(listener);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) { //TODO : set current user fireBaseModel
        currentUser = user;
    }

    public interface AddUserListener {
        void onComplete();
    }

    public void addUser(User newUser, AddUserListener listener) {
        modelFirebase.addUser(newUser, listener);
    }

    public interface GetUserListener {
        void onComplete(User user);
    }

    public void getUser(String userName, GetUserListener listener) {
        modelFirebase.getUser(userName, listener);
    }

    public interface createUserWithEmailAndPasswordListener {
        void onComplete();
    }

    public void createUserWithEmailAndPassword(String email, String password, createUserWithEmailAndPasswordListener listener) {
        modelFirebase.createUserWithEmailAndPassword(email, password, listener);
    }

    public interface signInWithEmailAndPasswordListener {
        void onComplete();
    }

    public void signInWithEmailAndPasswordListener(String email, String password, signInWithEmailAndPasswordListener listener) {
        modelFirebase.signInWithEmailAndPassword(email, password, listener);
    }

    public void signOut() {
        modelFirebase.signOut();
    }

    ////////////////////////////////////***POST***//////////////////////////////////////////////

    String currentPostId;

    MutableLiveData<List<Post>> postsList = new MutableLiveData<List<Post>>();

    public LiveData<List<Post>> getAll() {
        if (postsList.getValue() == null) {
            refreshPostList();
        }
        return postsList;
    }

    public void refreshPostList() {
        postsListLoadingState.setValue(PostsListLoadingState.loading);

        Long postLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostLastUpdate", 0);
        executor.execute(() -> {
            List<Post> poList = AppLocalDb.db.postDao().getAllPost();
            postsList.postValue(poList);
        });
        modelFirebase.getAllPosts(postLastUpdate, new ModelFirebase.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> postList) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lastUpdateDate = new Long(0);
                        Log.d("TAG", "fb returned" + postList.size());
                        for (Post post : postList) {
                            if (post.isDeleted())
                                AppLocalDb.db.postDao().deletePost(post);
                            else {
                                AppLocalDb.db.postDao().insertAll(post);
                            }
                            if (lastUpdateDate < post.getUpdateDate()) {
                                lastUpdateDate = post.getUpdateDate();
                            }
                        }
                        MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                                .edit().putLong("PostLastUpdate", lastUpdateDate).commit();
                        List<Post> poList = AppLocalDb.db.postDao().getAllPost();
                        postsList.postValue(poList);
                        postsListLoadingState.postValue(PostsListLoadingState.loaded);
                    }
                });


            }
        });
    }

    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post newPost, AddPostListener listener) {
        modelFirebase.addPost(newPost, new AddPostListener() {
            @Override
            public void onComplete() {
                refreshPostList();
                listener.onComplete();
            }
        });
    }

    public interface AddPostLocationListener {
        void onComplete();
    }

    public void saveLocation(String postId, double latitude, double longitude, AddPostLocationListener listener) {
        modelFirebase.saveLocation(postId,latitude,longitude,listener);
    }

    public interface GetPostByIdListener {
        void onComplete(Post post);
    }

    public void getPostById(String postId, GetPostByIdListener listener) {
        modelFirebase.getPostById(postId, listener);
    }

    public interface DeletePostsListener {
        void onComplete();
    }

    public void deletePost(String postId, DeletePostsListener listener) {
        modelFirebase.deletePost(postId, listener);
    }

    public interface UpdateProfileListener {
        void onComplete();
    }

    public void UpdateProfile(User user, UpdateProfileListener listener) {
        modelFirebase.UpdateProfile(user, listener);
    }

    public String getGeneratePostId() {
        String postID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        postID = postID + "-" + currentUser.getUserName();
        Log.d("TAG", postID);
        return postID;
    }

    public void setCurrentPostId(String postId) {
        currentPostId = postId;
    }

    public String getCurrentPostId() {
        return currentPostId;
    }

    /////////////////Authentication////////////////

    public boolean isSignedIn() {
        return modelFirebase.isSignedIn();
    }
}
