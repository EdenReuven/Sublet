package com.example.sublet.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getAllPosts(Model.GetAllPostsListener listener) {

    }

    public void addPost(Post newPost, Model.AddPostListener listener) {
        Map<String, Object> json = newPost.toJson();
        db.collection("post")
                .document(newPost.getPostId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void getPostById(String postId, Model.GetPostByIdListener listener) {

    }

    public void deletePost(String postId, Model.DeletePostsListener listener) {

    }

    public void getPost(int pos, Model.GetPostsListener listener) {

    }

    public void getAllUsers() {

    }


    public void addUser(User newUser) {

    }
}
