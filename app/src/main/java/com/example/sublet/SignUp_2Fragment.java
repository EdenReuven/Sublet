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
import android.widget.Toast;

import com.example.sublet.model.User;


public class SignUp_2Fragment extends Fragment {

    EditText password_ed, confirmPassword_ed;
    Button continue_btn;
    boolean validOk;
    SignUpViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_2, container, false);
        password_ed = view.findViewById(R.id.signUp2_freg_password_et);
        confirmPassword_ed = view.findViewById(R.id.signUp2_freg_conPassword_et);
        continue_btn = view.findViewById(R.id.signUp3_freg_continue_btn);
        viewModel.setUser(SignUp_2FragmentArgs.fromBundle(getArguments()).getUserObj());

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validOk = true;
                CheckValid();
                if(!validOk)
                    return;
                continue_btn.setEnabled(false);
                viewModel.getUser().setPassword(password_ed.getText().toString());
                Navigation.findNavController(v).navigate(SignUp_2FragmentDirections.actionSignUp2FragmentToSignUp3Fragment(viewModel.getUser()));
            }
        });
        return view;
    }

    public void CheckValid(){
        EditText[] validArray = {password_ed,confirmPassword_ed};

        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                Toast.makeText(MyApplication.getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(!password_ed.getText().toString().equals(confirmPassword_ed.getText().toString())){
            Toast.makeText(MyApplication.getContext(), "The password is not match!", Toast.LENGTH_SHORT).show();
            validOk = false;
            return;
        }
        if (password_ed.getText().length() < 6){
            Toast.makeText(MyApplication.getContext(), "The password must contain at least 6 characters ", Toast.LENGTH_SHORT).show();
            validOk = false;
            return;
        }
    }
}