package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignUp_1Fragment extends Fragment {

    EditText fullName_et, userName_et, email_et, phone_et;
    Button continue_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_1, container, false);

        fullName_et=view.findViewById(R.id.signUp_freg_fullName_et);
        userName_et=view.findViewById(R.id.signUp_freg_userName_et);
        email_et=view.findViewById(R.id.signUp_freg_email_et);
        phone_et=view.findViewById(R.id.signUp_freg_phone_et);
        continue_btn=view.findViewById(R.id.signUp_freg_continue_btn);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement new fragment
            }
        });
        return view;
    }
}