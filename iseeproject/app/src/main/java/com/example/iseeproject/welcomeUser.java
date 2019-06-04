package com.example.iseeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class welcomeUser extends AppCompatActivity {
    private DrawerLayout mDrawerlayout ;
    private ActionBarDrawerToggle mToggle ;
    TextView userView, pwdView;
    Button loginbtn;
    int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.welcomeUseractivity);

        SharedPreferences sharedpreferences = getSharedPreferences("USER", Context.MODE_PRIVATE);
        final String username = sharedpreferences.getString("username","");
        //if there is no previous session, redirect to main activity
        if (username.equals("")) {
            Intent intent = new Intent(welcomeUser.this, mainActivity.class);
            startActivity(intent);
        }
        else {//if session is stored, build welcome user activity

            userView = (TextView) findViewById(R.id.welcomeUser);
            String wbText = "Welcome back, " + username;
            userView.setText(wbText);

            pwdView = (TextView) findViewById(R.id.etPassword);

            loginbtn =(Button)findViewById(R.id.login);
            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(pwdView.getText())) {
                        Toast t = Toast.makeText(welcomeUser.this,
                                "Please provide password", Toast.LENGTH_LONG);
                        t.show();
                    }
                    else {
                        String password = pwdView.getText().toString();
                        if (checkPwd(username,password)){
                            //if username is correct then go to homepage
                            Intent myIntent  = new Intent(welcomeUser.this, homePage.class);
                            Bundle b = new Bundle();
                            b.putString("username",username);
                            myIntent.putExtras(b); //Put your id to your next Intent
                            startActivity(myIntent);
                        }
                        else {
                            if (attempts < 3) {
                                Toast t = Toast.makeText(welcomeUser.this,
                                        "Password and Username don't match", Toast.LENGTH_LONG);
                                t.show();
                                attempts++;
                            }
                            else {
                                //TODO close button for 10 mins
                                loginbtn.setEnabled(false);
                            }
                        }

                    }
                }
            });
        }
    }

    public boolean checkPwd(String username, String password) {
        dbHandler db = new dbHandler(this);
        User user = db.getUser(username);

        return (user.getPwd()).equals(password);

    }
}
