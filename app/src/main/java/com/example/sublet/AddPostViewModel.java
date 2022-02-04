package com.example.sublet;

import androidx.lifecycle.ViewModel;

import com.example.sublet.model.Post;

public class AddPostViewModel extends ViewModel {
    Post newPost;

    public Post getNewPost() {
        return newPost;
    }

    public void setNewPost(Post newPost) {
        this.newPost = newPost;
    }
}
