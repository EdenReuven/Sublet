package com.example.sublet;

import androidx.lifecycle.ViewModel;

import com.example.sublet.model.Post;

public class EditPostViewModel extends ViewModel {
    Post postToEdit;

    public Post getPostToEdit() {
        return postToEdit;
    }

    public void setPostToEdit(Post postToEdit) {
        this.postToEdit = postToEdit;
    }
}
