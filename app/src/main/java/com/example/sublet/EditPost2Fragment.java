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

import java.util.List;

public class EditPost2Fragment extends Fragment {
    EditText description_et;
    ImageButton addPhoto_imgBtn;
    Button post_btn;
    boolean validOk;
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

                List<Post> postUserList = Model.instance.getCurrentUser().getPostList(); //edit data in list
                for(int i =0;i<postUserList.size();i++){
                    if(postUserList.get(i).getPostId().equals(post.getPostId())){
                        postUserList.get(i).setFromDate(post.getFromDate());
                        postUserList.get(i).setToDate(post.getToDate());
                        postUserList.get(i).setLocation(post.getLocation());
                        postUserList.get(i).setNumRoommate(post.getNumRoommate());
                        postUserList.get(i).setPrice(post.getPrice());
                        postUserList.get(i).setOverallPeople(post.getOverallPeople());
                        postUserList.get(i).setNumOfBedroom(post.getNumOfBedroom());
                        postUserList.get(i).setNumOfBathroom(post.getNumOfBathroom());
                        postUserList.get(i).setPostContent(post.getPostContent());
                    }
                }
                validOk = true;
                CheckValid();
                if(!validOk)
                    return;
                Navigation.findNavController(v).navigate(EditPost2FragmentDirections.actionEditPost2FragmentToHomePageFragment());

                //update image
                //TODO: update the post from list user post
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

}