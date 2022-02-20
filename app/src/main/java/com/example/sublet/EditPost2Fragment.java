package com.example.sublet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sublet.model.Model;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class EditPost2Fragment extends Fragment {
    EditText description_et;
    ImageButton camera_imgBtn , gallery_imgBtn;
    Button post_btn;
    boolean validOk;
    ProgressBar progressBar;
    EditPostViewModel viewModel;
    ImageView image_imgView;

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
        camera_imgBtn = view.findViewById(R.id.editPost2_frag_camera_btnimg);
        gallery_imgBtn = view.findViewById(R.id.editPost2_frag_gallery_btnimg);
        image_imgView = view.findViewById(R.id.editPost2_frag_picture_imgview);
        post_btn = view.findViewById(R.id.editPost2_freg_post_btn);
        description_et.setText(viewModel.getPostToEdit().getPostContent());
        image_imgView.setImageResource(R.drawable.room);
        if(viewModel.getPostToEdit().getPostImgUrl() !=null) {
            Picasso.get()
                    .load(viewModel.getPostToEdit().getPostImgUrl())
                    .into(image_imgView);
        }

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getPostToEdit().setPostContent(description_et.getText().toString());
                saveEditPost();
            }
        });

        camera_imgBtn.setOnClickListener(v -> {
            openCamera();
        });

        gallery_imgBtn.setOnClickListener(v -> {
            openGallery();
        });

        return view;
    }

    static final int EDIT_POST_IMAGE_CAPTURE = 1;
    static final int  EDIT_POST_IMAGE_PIC =2;
    Bitmap editPostImageBitmap;

    private void openGallery() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
        pickPictureIntent.setType("image/+");
        startActivityForResult(pickPictureIntent,EDIT_POST_IMAGE_PIC);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, EDIT_POST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_POST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                editPostImageBitmap = (Bitmap) extras.get("data");
                image_imgView.setImageBitmap(editPostImageBitmap);

            }
        } else if (requestCode == EDIT_POST_IMAGE_PIC) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri imgUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imgUri);
                    editPostImageBitmap = BitmapFactory.decodeStream(imgStream);
                    image_imgView.setImageBitmap(editPostImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void saveEditPost(){
        progressBar.setVisibility(View.VISIBLE);
        post_btn.setEnabled(false);

        if(editPostImageBitmap!=null){
            Model.instance.saveimage(editPostImageBitmap, Model.instance.getCurrentUser().getUserName() + ".jpg", new Model.SaveImageListener() {
                @Override
                public void onComplete(String url) {
                    viewModel.getPostToEdit().setPostImgUrl(url);
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
                        post1.setPostImgUrl(url);

                        validOk = true;
                        CheckValid();
                        if(!validOk)
                            return;

                        Model.instance.addPost(post1,() -> {
                            Navigation.findNavController(description_et).navigate(EditPost2FragmentDirections.actionEditPost2FragmentToHomePageFragment());
                        });

                    });
                }
            });
        }else{
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

    public void CheckValid(){
        EditText[] validArray = {description_et}; //TODO: add image to list validation
        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                Toast.makeText(MyApplication.getContext(), "All Fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}