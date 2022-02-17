package com.example.sublet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.sublet.model.Model;

public class EditPost2Fragment extends Fragment {
    EditText description_et;
    ImageButton addPhoto_imgBtn;
    Button post_btn;
    boolean validOk;
    ProgressBar progressBar;
    EditPostViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_post2, container, false);
        viewModel.setPostToEdit(EditPost2FragmentArgs.fromBundle(getArguments()).getEditPostObj());

        progressBar = view.findViewById(R.id.edit_progressBar);
        progressBar.setVisibility(View.GONE);
        description_et = view.findViewById(R.id.editPost2_frag_description_et);
        addPhoto_imgBtn = view.findViewById(R.id.editPost2_frag_gallery_btnimg);
        post_btn = view.findViewById(R.id.editPost2_freg_post_btn);

        //setImage
        description_et.setText(viewModel.getPostToEdit().getPostContent());

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getPostToEdit().setPostContent(description_et.getText().toString());
                //post.setImage
                saveEditPost();
//                List<Post> postUserList = Model.instance.getCurrentUser().getPostList(); //edit data in list
//                for(int i =0;i<postUserList.size();i++){
//                    if(postUserList.get(i).getPostId().equals(post.getPostId())){
//                        postUserList.get(i).setFromDate(post.getFromDate());
//                        postUserList.get(i).setToDate(post.getToDate());
//                        postUserList.get(i).setLocation(post.getLocation());
//                        postUserList.get(i).setNumRoommate(post.getNumRoommate());
//                        postUserList.get(i).setPrice(post.getPrice());
//                        postUserList.get(i).setOverallPeople(post.getOverallPeople());
//                        postUserList.get(i).setNumOfBedroom(post.getNumOfBedroom());
//                        postUserList.get(i).setNumOfBathroom(post.getNumOfBathroom());
//                        postUserList.get(i).setPostContent(post.getPostContent());
//                    }
//                }

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

    private void saveEditPost(){
        progressBar.setVisibility(View.VISIBLE);
        post_btn.setEnabled(false);
        Model.instance.getPostById(viewModel.getPostToEdit().getPostId(),post1 -> {

            post1.setFromDate(viewModel.getPostToEdit().getFromDate());
            post1.setToDate(viewModel.getPostToEdit().getToDate());
            post1.setLocation(viewModel.getPostToEdit().getLocation());
            post1.setNumRoommate(viewModel.getPostToEdit().getNumRoommate());
            post1.setPrice(viewModel.getPostToEdit().getPrice());
            post1.setOverallPeople(viewModel.getPostToEdit().getOverallPeople());
            post1.setNumOfBedroom(viewModel.getPostToEdit().getNumOfBedroom());
            post1.setNumOfBathroom(viewModel.getPostToEdit().getNumOfBathroom());
            post1.setPostContent(viewModel.getPostToEdit().getPostContent());

            validOk = true;
            CheckValid();
            if(!validOk)
                return;

            Model.instance.addPost(post1,() -> {
                Navigation.findNavController(description_et).navigate(EditPost2FragmentDirections.actionEditPost2FragmentToHomePageFragment());
            });

        });
    }

}