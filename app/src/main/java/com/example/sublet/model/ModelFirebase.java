package com.example.sublet.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getAllPosts(Model.GetAllPostsListener listener) {

    }

    public void addPost(Post newPost, Model.AddPostListener listener) {

    }

    public void getPostById(String postId, Model.GetPostByIdListener listener) {

    }

    public void deletePost(String postId, Model.DeletePostsListener listener) {

    }

    public void getPost(int pos, Model.GetPostsListener listener) {

    }
}
