package com.example.sublet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;

public class HomePageActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.home_navhost);
        navController = navHost.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.base_menu,menu);
        return true;
    }
}