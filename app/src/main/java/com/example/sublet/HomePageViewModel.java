package com.example.sublet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sublet.model.Model;
import com.example.sublet.model.Post;
import java.util.List;

public class HomePageViewModel extends ViewModel {

    LiveData <List<Post>> dataPost;
    public HomePageViewModel(){
        dataPost= Model.instance.getAll();
    }
    public LiveData<List<Post>> getDataPost() {
        return dataPost;
    }

}
