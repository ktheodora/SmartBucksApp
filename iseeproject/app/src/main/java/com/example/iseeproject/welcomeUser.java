package com.example.iseeproject;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class welcomeUser extends AppCompatActivity {
    private DrawerLayout mDrawerlayout ;
    private ActionBarDrawerToggle mToggle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.welcomeUseractivity);
    }
}
