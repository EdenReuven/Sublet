package com.example.sublet;
import androidx.lifecycle.ViewModel;
import com.example.sublet.model.User;
import java.util.List;

public class LoginViewModel extends ViewModel {

    List<User> userListData ;

    public List<User> getUserListData() {
        return userListData;
    }

    public void setUserListData(List<User> userListData) {
        this.userListData = userListData;
    }
}
