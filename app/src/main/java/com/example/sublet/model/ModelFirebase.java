package com.example.sublet.model;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.sublet.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    public interface GetAllPostsListener{
        void onComplete(List<Post> postList);
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ModelFirebase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }


    public void getAllPosts(Long postLastUpdate, GetAllPostsListener listener) {
        db.collection(Post.COLLECTION_NAME).whereGreaterThanOrEqualTo("updateDate",new Timestamp(postLastUpdate, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> postList = new LinkedList<Post>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Post post = Post.create(doc.getData());
                            if (post != null)
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
        db.collection(Post.COLLECTION_NAME).document(postId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Post post = null;
                        if (task.isSuccessful() && task.getResult() != null) {
                            post = Post.create(task.getResult().getData());
                        }
                        listener.onComplete(post);
                    }
                });
    }


    public void deletePost(String postId, Model.DeletePostsListener listener) {
        //TODO: POST ID
        db.collection(Post.COLLECTION_NAME).document(postId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection(User.COLLECTION_NAME).get()
                .addOnCompleteListener(task -> {
                    List<User> userList = new LinkedList<User>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            User user = User.create(doc.getData());
                            if (user != null)
                                userList.add(user);
                        }
                    }
                    listener.onComplete(userList);
                });
    }

    public void addUser(User newUser, Model.AddUserListener listener) {
        Map<String, Object> json = newUser.toJson();
        db.collection(User.COLLECTION_NAME)
                .document(newUser.getUserName())
                .set(json)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete();
                    }
                });
    }

    public void getUser(String userName, Model.GetUserListener listener) {
        db.collection(User.COLLECTION_NAME).document(userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user = null;
                        if (task.isSuccessful() && task.getResult() != null) {
                            user = User.create(task.getResult().getData());
                        }
                        listener.onComplete(user);
                    }
                });
    }

    public void createUserWithEmailAndPassword(String email, String password, Model.createUserWithEmailAndPasswordListener listener) {
        Log.d("TAG",email + password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onComplete();
//                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Log.d("TAG", "Add user is failed");
                    }
                });
    }

    public void signInWithEmailAndPassword(String email, String password, Model.signInWithEmailAndPasswordListener listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    listener.onComplete();
                }
                else{
                    Toast.makeText(MyApplication.getContext(), "The email or/and password are incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


    FirebaseStorage storage = FirebaseStorage.getInstance();
}
