package com.example.sublet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sublet.model.Model;

public class DialogPopUp extends DialogFragment {
    Button ok_btn,cancel_btn;
    ProgressBar progressBar;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pop_up,container,false);

        ok_btn = view.findViewById(R.id.popUp_ok_btn);
        cancel_btn = view.findViewById(R.id.popUp_oancel_btn);
        progressBar = view.findViewById(R.id.popUp_progressBar);
        progressBar.setVisibility(View.GONE);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Model.instance.deletePost(Model.instance.getCurrentPostId(), () -> {
                    getDialog().dismiss();
                    Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user -> {
                        user.getPostList().remove(Model.instance.getCurrentPostId());
                        Model.instance.addUser(user,() -> {});
                    });
                    NavHostFragment.findNavController(getParentFragment()).navigateUp();
                });
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                NavHostFragment.findNavController(getParentFragment()).navigateUp();
            }
        });

        return view;
    }
}
