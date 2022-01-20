package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sublet.model.Model;
import com.example.sublet.model.Post;

public class EditPost2Fragment extends Fragment {
    EditText description_et;
    ImageButton addPhoto_imgBtn;
    Button post_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_post2, container, false);
        Post post = EditPost2FragmentArgs.fromBundle(getArguments()).getEditPostObj();

        description_et = view.findViewById(R.id.editPost2_frag_description_et);
        addPhoto_imgBtn = view.findViewById(R.id.editPost2_frag_photo_btnimg);
        post_btn = view.findViewById(R.id.editPost2_freg_post_btn);

        //setImage
        description_et.setText(post.getPostContent());

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setPostContent(description_et.getText().toString());
                //post.setImage

                Post updatePost = Model.instance.getPost(post.getPostId());
                updatePost.setFromDate(post.getFromDate());
                updatePost.setToDate(post.getToDate());
                updatePost.setLocation(post.getLocation());
                updatePost.setNumRoommate(post.getNumRoommate());
                updatePost.setPrice(post.getPrice());
                updatePost.setOverallPeople(post.getOverallPeople());
                updatePost.setNumOfBedroom(post.getNumOfBedroom());
                updatePost.setNumOfBathroom(post.getNumOfBathroom());
                updatePost.setPostContent(post.getPostContent());
                Navigation.findNavController(v).navigate(EditPost2FragmentDirections.actionEditPost2FragmentToHomePageFragment());

                //update image
                //TODO: update the post from list user post
            }
        });

        return view;
    }
}