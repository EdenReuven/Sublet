package com.example.sublet;

import androidx.lifecycle.ViewModel;
import com.example.sublet.model.User;

public class SignUpViewModel extends ViewModel {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
