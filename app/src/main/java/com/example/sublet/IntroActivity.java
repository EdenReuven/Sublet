package com.example.sublet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.sublet.model.Model;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Model.instance.executor.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Model.instance.isSignedIn() && Model.instance.getCurrentUser() != null){
                Model.instance.mainThread.post(() -> {
                    toHomePageActivity();
                });
            }else {
                Model.instance.mainThread.post(() -> {
                    toLoginActivity();
                });
            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
        finish();
    }

    private void toHomePageActivity() {
        Intent intent = new Intent(this,HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}