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

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPost2Fragment extends Fragment {

    EditText description_et;
    ImageView image_imageView;
    ImageButton gallery_imgBtn, camera_imgBtn;
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
        image_imageView=view.findViewById(R.id.editPost2_frag_image_imgview);
        gallery_imgBtn=view.findViewById(R.id.editPost2_frag_gallery_btnimg);
        camera_imgBtn=view.findViewById(R.id.editPost2_frag_photo_btnimg);
        post_btn = view.findViewById(R.id.editPost2_freg_post_btn);

        viewModel.setNewPost(AddPost2FragmentArgs.fromBundle(getArguments()).getPostObj());

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

        camera_imgBtn.setOnClickListener(v -> {
            openCamera();
        });

        gallery_imgBtn.setOnClickListener(v -> {
            openGallery();
        });

        return view;
    }

    static final int REQUEST_IMAGE_CAPTURE=1;
    static final int REQUEST_IMAGE_PIC=2;

    private void openCamera() {
        Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
    }

    Bitmap imageBitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(resultCode== Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                image_imageView.setImageBitmap(imageBitmap);

            }
        }else if (requestCode == REQUEST_IMAGE_PIC){
            if (resultCode == Activity.RESULT_OK){
                try {
                    final Uri imgUri =data.getData();
                    final InputStream imgStream =getContext().getContentResolver().openInputStream(imgUri);
                    imageBitmap = BitmapFactory.decodeStream(imgStream);
                    image_imageView.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void openGallery() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
        pickPictureIntent.setType("image/+");
        startActivityForResult(pickPictureIntent,REQUEST_IMAGE_PIC);
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
        if(imageBitmap!=null){
            Model.instance.saveimage(imageBitmap, Model.instance.getCurrentUser().getUserName() + ".jpg", new Model.SaveImageListener() {
                @Override
                public void onComplete(String url) {
                    viewModel.getNewPost().setPostImgUrl(url);
                    Model.instance.addPost(viewModel.getNewPost(),()->{
                        Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user -> {
                            user.getPostList().add(viewModel.getNewPost().getPostId());
                            Model.instance.addUser(user, () -> {}); // add the post to list post of the current user
                        });
                        Navigation.findNavController(description_et).navigate(AddPost2FragmentDirections.actionAddPost2FragmentToHomePageFragment());
                    });

                }
            });
        }else{
            Model.instance.addPost(viewModel.getNewPost(),()->{
                Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user -> {
                    user.getPostList().add(viewModel.getNewPost().getPostId());
                    Model.instance.addUser(user, () -> {}); // add the post to list post of the current user
                });
                Navigation.findNavController(description_et).navigate(AddPost2FragmentDirections.actionAddPost2FragmentToHomePageFragment());
            });
        }

    }
}