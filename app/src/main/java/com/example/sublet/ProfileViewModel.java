package com.example.sublet;

import androidx.lifecycle.ViewModel;

import com.example.sublet.model.Post;

import java.util.List;

public class ProfileViewModel extends ViewModel{
    List<Post> data;

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }
}
