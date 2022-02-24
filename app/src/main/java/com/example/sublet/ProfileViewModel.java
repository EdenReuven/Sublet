package com.example.sublet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

import java.util.LinkedList;
import java.util.List;

public class ProfileViewModel extends ViewModel{
    LiveData<List<Post>> data;
    LiveData<List<Post>> dataForProfile;
    public ProfileViewModel(){
        data = Model.instance.getAll();
    }


    public LiveData<List<Post>> getData() {
        dataForProfile = data;
        for(int i=0;i<data.getValue().size();i++){

            if(!data.getValue().get(i).getPostId().contains("-"+Model.instance.getCurrentUser().getUserName())
                || data.getValue().get(i).isDeleted())
                dataForProfile.getValue().remove(data.getValue().get(i));
        }

        Model.instance.refreshPostList();
        return dataForProfile;
    }
}
