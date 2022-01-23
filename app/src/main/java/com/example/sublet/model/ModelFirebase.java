package com.example.sublet.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getAllPosts(Model.GetAllPostsListener listener) {
        db.collection(Post.COLLECTION_NAME).get()
        .addOnCompleteListener(task -> {
            List<Post> postList = new LinkedList<Post>();
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot doc : task.getResult()){
                    Post post = Post.create(doc.getData());
                    if(post != null)
                        postList.add(post);
                }
            }
            listener.onComplete(postList);
        });
    }

    public void addPost(Post newPost, Model.AddPostListener listener) {
        Map<String, Object> json = newPost.toJson();
        db.collection(Post.COLLECTION_NAME)
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
