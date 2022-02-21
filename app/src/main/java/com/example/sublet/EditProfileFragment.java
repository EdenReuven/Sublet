package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.sublet.model.Model;
import com.example.sublet.model.User;
import com.squareup.picasso.Picasso;


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

        /*Model.instance.getUser(Model.instance.getCurrentPostId(),user -> {
            name_et.setText(user.getUserName());
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
                    user.setUserName(name_et.getText().toString());
                    user.setPhone(phone_et.getText().toString());

                }
            });
        });*/

        User user = Model.instance.getCurrentUser();
        userName_et.setText(user.getUserName());
        fullName_et.setText(user.getFullName());
        phone_et.setText(user.getPhone());
        profileImg.setImageResource(R.drawable.woman);
        if(user.getProfileUrl()!=null) {
            Picasso.get()
                    .load(user.getProfileUrl())
                    .into(profileImg);
        }

        return view;
    }
}