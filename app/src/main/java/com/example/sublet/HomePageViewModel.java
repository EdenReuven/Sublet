package com.example.sublet;
import androidx.lifecycle.ViewModel;
import com.example.sublet.model.Post;
import java.util.List;

public class HomePageViewModel extends ViewModel {

    List<Post> dataPost;
    public List<Post> getDataPost() {
        return dataPost;
    }

    public void setDataPost(List<Post> dataPost) {
        this.dataPost = dataPost;
    }
}
