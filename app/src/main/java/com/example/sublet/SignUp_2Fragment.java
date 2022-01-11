package com.example.sublet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignUp_2Fragment extends Fragment {

    EditText password_ed, confirmPassword_ed;
    Button continue_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_2, container, false);
        password_ed = view.findViewById(R.id.signUp2_freg_password_et);
        confirmPassword_ed = view.findViewById(R.id.signUp2_freg_conPassword_et);
        continue_btn = view.findViewById(R.id.signUp3_freg_continue_btn);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(SignUp_2FragmentDirections.actionSignUp2FragmentToSignUp3Fragment());
            }
        });
        return view;
    }
}