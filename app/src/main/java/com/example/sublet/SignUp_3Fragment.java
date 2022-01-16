package com.example.sublet;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class SignUp_3Fragment extends Fragment {
    ImageButton addPhoto_imgBtn;
    Button continue_Btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_3, container, false);
        addPhoto_imgBtn = view.findViewById(R.id.signUp3_freg_addPhoto_imgbtn);
        continue_Btn = view.findViewById(R.id.signUp3_freg_continue_btn);

        continue_Btn.setOnClickListener(new View.OnClickListener(){
            //TODO: check validation
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        addPhoto_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Upload pictures
            }
        });
        return view;
    }
}