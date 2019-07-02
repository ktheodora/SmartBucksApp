package com.example.iseeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class mainActivity extends AppCompatActivity {

    Boolean loginstate;
    int attempts;
    int backCount = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            loginstate = b.getBoolean("state");
            attempts = b.getInt("attempts");
        }

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
        Intent intent = new Intent(mainActivity.this, loginActivity.class);
        if (loginstate != null) {
            Bundle b = new Bundle();
            b.putBoolean("state",loginstate);
            b.putInt("attempts",attempts);
            intent.putExtras(b); //Put your id to your next Intent
        }
        startActivity(intent);
    }

    private void routesignup()
    {
        Intent intent = new Intent(mainActivity.this, createAccount.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        backCount ++;
        if (backCount == 1) {
            Toast t = Toast.makeText(mainActivity.this,"Press again to exit",Toast.LENGTH_SHORT);
            t.show();
        }
        else if (backCount > 1) {
            Toast t = Toast.makeText(mainActivity.this,"Exiting app. See ya!",Toast.LENGTH_SHORT);
            t.show();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        }

    }

}



