package com.example.sublet;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sublet.model.Model;
import com.example.sublet.model.User;
import java.util.List;

public class SignUp_1Fragment extends Fragment {

    EditText fullName_et, userName_et, email_et, phone_et;
    Button continue_btn;
    SignUpViewModel viewModel;
    boolean validOk;
    List<User> users;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up_1, container, false);

        fullName_et=view.findViewById(R.id.signUp_freg_fullName_et);
        userName_et=view.findViewById(R.id.signUp_freg_userName_et);
        email_et=view.findViewById(R.id.signUp_freg_email_et);
        phone_et=view.findViewById(R.id.signUp_freg_phone_et);
        continue_btn=view.findViewById(R.id.signUp3_freg_continue_btn);
        Model.instance.getAllUsers(new Model.GetAllUsersListener() {
            @Override
            public void onComplete(List<User> userList) {
                users=userList;
            }
        });
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validOk = true;
                CheckValid();
                if(!validOk)
                    return;
                continue_btn.setEnabled(false);
                viewModel.user = new User();
                viewModel.getUser().setFullName(fullName_et.getText().toString());
                viewModel.getUser().setUserName(userName_et.getText().toString());
                viewModel.getUser().setEmail(email_et.getText().toString());
                viewModel.getUser().setPhone(phone_et.getText().toString());
                viewModel.getUser().setNickName(userName_et.getText().toString());

                Navigation.findNavController(v).navigate(SignUp_1FragmentDirections.actionSignUp1FragmentToSignUp2Fragment(viewModel.getUser()));
            }
        });
        return view;
    }

    public void CheckValid(){

        EditText[] validArray = {fullName_et,userName_et,email_et,phone_et};
        for(int i=0;i<validArray.length;i++){
            if(validArray[i].getText().toString().length() == 0){
                validOk = false;
                Toast.makeText(MyApplication.getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!email_et.getText().toString().contains("@gmail.com")){
            validOk = false;
            Toast.makeText(MyApplication.getContext(), "The email field is incorrect!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i=0 ; i< users.size();i++) {
            if (users.get(i).getEmail().equals(email_et.getText().toString()) ){
                validOk = false;
                Toast.makeText(MyApplication.getContext(), "This email already exist!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

}