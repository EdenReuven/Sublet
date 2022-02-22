package com.example.sublet.model;

import android.graphics.Bitmap;
import android.net.Uri;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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
        Model.instance.getPostById(postId,post -> {
            post.setDeleted(true);
            db.collection(Post.COLLECTION_NAME).document(postId).set(post.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    listener.onComplete();
                }
            });
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


    public void UpdateProfile(User user, Model.UpdateProfileListener listener) {
        Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user1 -> {
            user1.setUserName(user.getUserName());
            user1.setFullName(user.getFullName());
            user1.setPhone(user.getPhone());
            user1.setProfileUrl(user.getProfileUrl());

            db.collection(User.COLLECTION_NAME).document(user1.getUserName()).set(user1.toJson()).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    listener.onComplete();
                }
            });
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
    public void saveImage(Bitmap imageBitmap, String imageName, Model.SaveImageListener listener) {
        StorageReference storageRef =storage.getReference();
        StorageReference postImgRef = storageRef.child("/Posts/" +imageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = postImgRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                postImgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });

    }

    public void saveProfileImage(Bitmap profileImageBitmap, String imageName, Model.saveProfileImageListener listener) {
        StorageReference storageRef =storage.getReference();
        StorageReference profileImgRef = storageRef.child("/Profile/" +imageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileImgRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profileImgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });

    }
}
