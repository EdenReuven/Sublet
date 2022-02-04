package com.example.sublet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

public class AddPost2Fragment extends Fragment {

    EditText description_et;
    ImageButton addPhoto_imgBtn;
    Button post_btn;
    ProgressBar progressBar;
    boolean validOk;
    AddPostViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_post2, container, false);
        progressBar = view.findViewById(R.id.add_progressBar);
        progressBar.setVisibility(View.GONE);
        description_et=view.findViewById(R.id.editPost2_frag_description_et);
        addPhoto_imgBtn=view.findViewById(R.id.editPost2_frag_photo_btnimg);
        post_btn = view.findViewById(R.id.editPost2_freg_post_btn);

        viewModel.setNewPost(AddPost2FragmentArgs.fromBundle(getArguments()).getPostObj());

        addPhoto_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validOk=true;
                CheckValid();
                if(!validOk)
                    return;

                post_btn.setEnabled(false);
                viewModel.getNewPost().setPostContent(description_et.getText().toString());
                //set image

                saveNewPost();
            }
        });

        return view;
    }

    public void CheckValid(){
        EditText[] validArray = {description_et}; //TODO: add image to list validation

        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                validArray[i].setError("This field is require");
                return;
            }
        }
    }

    private void saveNewPost(){
        progressBar.setVisibility(View.VISIBLE);
        post_btn.setEnabled(false);
        Model.instance.addPost(viewModel.getNewPost(),()->{
            //Model.instance.getCurrentUser().getPostList().add(newPost);
            //Model.instance.addPostToCurrentUser(newPost);
            Navigation.findNavController(description_et).navigate(AddPost2FragmentDirections.actionAddPost2FragmentToHomePageFragment());
        });
    }
}