package com.example.sublet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

//TODO: add menu for profile - edit profile, lesson6 02:01:00
//TODO: option to navigate with UI and back to home fragment, lesson6 02:17:00

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!super.onOptionsItemSelected(item)){
            switch(item.getItemId()){
                case android.R.id.home:
                    navController.navigateUp();
                    return true;
//                case R.id.menu_add:
//                    navController.navigate(R.id.action_global_addPostFragment);
//                    return true;
//                case R.id.menu_map:
//                    navController.navigate(R.id.action_global_mapFragment);
//                    return true;
            }
        }else{
            return true;
        }
        return false;
    }
}
