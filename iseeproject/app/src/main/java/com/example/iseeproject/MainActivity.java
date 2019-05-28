package com.example.iseeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaRouter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.btnLogin);
        Button signup = (Button)findViewById(R.id.btnSignup);

        //method will  be working when button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               routelogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routesignup();
            }
        });


    }

    private void routelogin()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void routesignup()
    {
        Intent intent = new Intent(MainActivity.this, createAccount.class);
        startActivity(intent);
    }



}



