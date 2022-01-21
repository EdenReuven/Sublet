package com.example.sublet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.sublet.model.User;

public class SignUp_1Fragment extends Fragment {

    EditText fullName_et, userName_et, email_et, phone_et;
    Button continue_btn;
    User user;
    boolean validOk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_1, container, false);

        fullName_et=view.findViewById(R.id.signUp_freg_fullName_et);
        userName_et=view.findViewById(R.id.signUp_freg_userName_et);
        email_et=view.findViewById(R.id.signUp_freg_email_et);
        phone_et=view.findViewById(R.id.signUp_freg_phone_et);
        continue_btn=view.findViewById(R.id.signUp3_freg_continue_btn);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validOk = true;
                CheckValid();
                if(!validOk)
                    return;
                user= new User();
                user.setFullName(fullName_et.getText().toString());
                user.setUserName(userName_et.getText().toString());
                user.setEmail(email_et.getText().toString());
                user.setPhone(phone_et.getText().toString());

                Navigation.findNavController(v).navigate(SignUp_1FragmentDirections.actionSignUp1FragmentToSignUp2Fragment(user));
            }
        });
        return view;
    }

    public void CheckValid(){
        EditText[] validArray = {fullName_et,userName_et,email_et,phone_et};

        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                validArray[i].setError("This field is require");
                return;
            }
        }
    }

}