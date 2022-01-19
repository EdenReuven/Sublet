package com.example.sublet;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sublet.model.Model;


public class LogInFragment extends Fragment {
    EditText userName_et,password_et;
    Button login_btn;
    TextView singUp_tv , forget_tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        userName_et = view.findViewById(R.id.loginFragment_userName_pText);
        password_et = view.findViewById(R.id.loginFragment_passwoed_pText);
        login_btn = view.findViewById(R.id.loginFragment_logIn_btn);
        singUp_tv = view.findViewById(R.id.loginFragment_signUp_TV);
        forget_tv = view.findViewById(R.id.loginFragment_forgetPassword_TV);

        login_btn.setOnClickListener(new View.OnClickListener() {
            //TODO: check validation
            @Override
            public void onClick(View v) {
                if(userName_et.getText().toString().length() == 0){
                    userName_et.setError("This field is require");
                    return;
                }
                if(password_et.getText().toString().length() == 0) {
                    password_et.setError("This field is require");
                    return;
                }
                if(!Model.instance.userExists(userName_et.getText().toString(),password_et.getText().toString())){
                    return;
                }else {
                    Model.instance.setCurrentUser(userName_et.getText().toString());
                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        singUp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(LogInFragmentDirections.actionLogInFragmentToSignUp1Fragment());
            }
        });

        return view;

    }

}