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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.sublet.model.Model;
import java.io.InputStream;

public class SignUp_3Fragment extends Fragment {
    ImageButton camera_imgBtn, gallery_imgBtn;
    Button continue_Btn;
    SignUpViewModel viewModel;
    ImageView image_imageView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_3, container, false);
        camera_imgBtn = view.findViewById(R.id.signUp3_freg_camera_imgbtn);
        gallery_imgBtn = view.findViewById(R.id.signUp3_freg_gallery_imgbtn);
        image_imageView = view.findViewById(R.id.signUp3_freg_image_imgview);
        continue_Btn = view.findViewById(R.id.signUp3_freg_continue_btn);
        viewModel.setUser(SignUp_3FragmentArgs.fromBundle(getArguments()).getUserObj());

        continue_Btn.setOnClickListener(new View.OnClickListener() {
            //TODO: check validation
            @Override
            public void onClick(View v) {
                save();
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

    static final int PROFILE_IMAGE_CAPTURE = 1;
    static final int  PROFILE_IMAGE_PIC =2;
    Bitmap profileImageBitmap;

    private void openGallery() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
        pickPictureIntent.setType("image/+");
        startActivityForResult(pickPictureIntent,PROFILE_IMAGE_PIC);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, PROFILE_IMAGE_CAPTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                profileImageBitmap = (Bitmap) extras.get("data");
                image_imageView.setImageBitmap(profileImageBitmap);

            }
        } else if (requestCode == PROFILE_IMAGE_PIC) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri imgUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imgUri);
                    profileImageBitmap = BitmapFactory.decodeStream(imgStream);
                    image_imageView.setImageBitmap(profileImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void save() {
        continue_Btn.setEnabled(false);
        if(profileImageBitmap!=null){
            Model.instance.createUserWithEmailAndPassword(viewModel.getUser().getEmail(),viewModel.getUser().getPassword(),() -> {
                Model.instance.saveProfileImage(profileImageBitmap, viewModel.getUser().getUserName() + ".jpg", url -> {
                    viewModel.getUser().setProfileUrl(url);
                    Model.instance.addUser(viewModel.getUser(), () -> {
                        Model.instance.setCurrentUser(viewModel.getUser());
                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    });
                });
            });
        }
        else{
            Model.instance.createUserWithEmailAndPassword(viewModel.getUser().getEmail(), viewModel.getUser().getPassword(), new Model.createUserWithEmailAndPasswordListener() {
                @Override
                public void onComplete() {
                    Model.instance.addUser(viewModel.getUser(), () -> {
                        Model.instance.setCurrentUser(viewModel.getUser());
                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }
            });
        }
    }
}


