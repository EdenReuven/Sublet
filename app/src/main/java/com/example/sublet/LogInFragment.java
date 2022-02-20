package com.example.sublet;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import com.example.sublet.model.Model;
import com.example.sublet.model.User;
import java.util.List;

public class LogInFragment extends Fragment {
    EditText email_et,password_et;
    Button login_btn;
    TextView singUp_tv , forget_tv ;
    LoginViewModel viewModel;
    boolean ok;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        email_et = view.findViewById(R.id.loginFragment_email_pText);
        password_et = view.findViewById(R.id.loginFragment_passwoed_pText);
        login_btn = view.findViewById(R.id.loginFragment_logIn_btn);
        singUp_tv = view.findViewById(R.id.loginFragment_signUp_TV);
        forget_tv = view.findViewById(R.id.loginFragment_forgetPassword_TV);

        Model.instance.getAllUsers(userList -> {
            viewModel.setUserListData(userList);
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                if(email.length()==0 || password.length()==0) {
                    Toast.makeText(MyApplication.getContext(), "Email and password are required!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Model.instance.signInWithEmailAndPasswordListener(email, password, () -> {
                        login_btn.setEnabled(false);
                        singUp_tv.setEnabled(false);
                        forget_tv.setEnabled(false);
                        for (int i = 0; i < viewModel.getUserListData().size(); i++) {
                            if (viewModel.getUserListData().get(i).getEmail().equals(email)) {
                                Model.instance.getUser(viewModel.getUserListData().get(i).getUserName(), user -> {
                                    Model.instance.setCurrentUser(user);
                                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    getActivity().finish();
                                });
                            }
                        }
                        email_et.setText("");
                        password_et.setText("");
                    });
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

    @Override
    public void onStart() {
        super.onStart();
        //TODO :  Check if user is signed in (non-null) and update UI accordingly.
        /*https://firebase.google.com/docs/auth/android/password-auth*/
    }
}