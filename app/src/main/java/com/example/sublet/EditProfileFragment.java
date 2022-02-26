package com.example.sublet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sublet.model.Model;
import com.example.sublet.model.User;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class EditProfileFragment extends Fragment {
    ImageView profileImg;
    EditText userName_et, phone_et , fullName_et;
    ImageButton camera_imgBtn , gallery_imgBtn;
    Button save_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        profileImg=view.findViewById(R.id.editProfile_frag_profileImg_img);
        userName_et=view.findViewById(R.id.editProfile_freg_userName_et);
        fullName_et=view.findViewById(R.id.editProfile_freg_fullname_et);
        camera_imgBtn=view.findViewById(R.id.editProfile_freg_camera_btn);
        gallery_imgBtn=view.findViewById(R.id.editProfile_freg_gallery_btn);
        save_btn=view.findViewById(R.id.editProfile_freg_save_btn);
        phone_et=view.findViewById(R.id.editProfile_freg_phone_et);

        User user = Model.instance.getCurrentUser();
        userName_et.setText(user.getNickName());
        fullName_et.setText(user.getFullName());
        phone_et.setText(user.getPhone());
        profileImg.setImageResource(R.drawable.woman);
        if(user.getProfileUrl()!=null) {
            Picasso.get()
                    .load(user.getProfileUrl())
                    .into(profileImg);
        }
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_btn.setEnabled(false);
                if(editProfileImageBitmap!=null){
                    Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user1 -> {
                        user1.setNickName(userName_et.getText().toString());
                        user1.setFullName(fullName_et.getText().toString());
                        user1.setPhone(phone_et.getText().toString());
                        Model.instance.saveProfileImage(editProfileImageBitmap,userName_et.getText().toString() + ".jpg",url -> {
                            user1.setProfileUrl(url);
                            Model.instance.UpdateProfile(user1,() -> {
                                Model.instance.setCurrentUser(user1);
                                Navigation.findNavController(v).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToHomePageFragment());
                            });
                        });
                    });
                }else{
                    Model.instance.getUser(Model.instance.getCurrentUser().getUserName(),user1 -> {
                        user1.setNickName(userName_et.getText().toString());
                        user1.setFullName(fullName_et.getText().toString());
                        user1.setPhone(phone_et.getText().toString());
                        Model.instance.UpdateProfile(user1,() -> {
                            Model.instance.setCurrentUser(user1);
                            Navigation.findNavController(v).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToHomePageFragment());
                        });
                    });
                }
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

    static final int EDIT_PROFILE_IMAGE_CAPTURE = 1;
    static final int  EDIT_PROFILE_IMAGE_PIC =2;
    Bitmap editProfileImageBitmap;

    private void openGallery() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
        pickPictureIntent.setType("image/+");
        startActivityForResult(pickPictureIntent,EDIT_PROFILE_IMAGE_PIC);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, EDIT_PROFILE_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                editProfileImageBitmap = (Bitmap) extras.get("data");
                profileImg.setImageBitmap(editProfileImageBitmap);

            }
        } else if (requestCode == EDIT_PROFILE_IMAGE_PIC) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri imgUri = data.getData();
                    final InputStream imgStream = getContext().getContentResolver().openInputStream(imgUri);
                    editProfileImageBitmap = BitmapFactory.decodeStream(imgStream);
                    profileImg.setImageBitmap(editProfileImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to select image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}