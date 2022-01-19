package com.example.sublet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

public class AddPost2Fragment extends Fragment {

    EditText description_et;
    ImageButton addPhoto_imgBtn;
    Button post_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_post2, container, false);
        description_et=view.findViewById(R.id.addPost2_frag_description_et);
        addPhoto_imgBtn=view.findViewById(R.id.addPost2_frag_photo_btnimg);
        post_btn=view.findViewById(R.id.addPost2_freg_post_btn);
        Post newPost = AddPost2FragmentArgs.fromBundle(getArguments()).getPostObj();

        newPost.setPostContent(description_et.getText().toString());
        //set image

        addPhoto_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : post + home page
                Model.instance.addPost(newPost);
                Navigation.findNavController(v).navigate(AddPost2FragmentDirections.actionAddPost2FragmentToHomePageFragment());
            }
        });

        return view;
    }
}