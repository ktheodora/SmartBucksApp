package com.example.iseeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView infoView;
    private Button login;
    private int attempts = 3;
    String username, password, info;
    dbHandler db;
    private Button back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new dbHandler(this);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        infoView = (TextView) findViewById(R.id.tvinfo);
        login = (Button)findViewById(R.id.login);
        back = (Button)findViewById(R.id.backbtn);

        info = "No of attempts left: " + attempts;
        infoView.setText(info);

        //method will  be working when button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SINCE NAME IS EDIT TEXT USER WILL  get whatever is entered through getText and
                // then it is converted to String same done for password
                if(TextUtils.isEmpty(Password.getText()) || TextUtils.isEmpty(Name.getText())) {
                    Toast t = Toast.makeText(loginActivity.this,
                            "Please provide all fields", Toast.LENGTH_LONG);
                    t.show();
                }
                else {
                    String password = Password.getText().toString();
                    String username = Name.getText().toString();

                    if (!(db.isUser(username))) {
                        Toast t = Toast.makeText(loginActivity.this,
                                "Username doesn't exist", Toast.LENGTH_LONG);
                        t.show();
                    }
                    else if (checkPwd(username,password)){
                        //if username is correct then go to homepage
                        Intent myIntent  = new Intent(loginActivity.this, homePage.class);
                        Bundle b = new Bundle();
                        b.putString("username",username);
                        myIntent.putExtras(b); //Put your id to your next Intent
                        startActivity(myIntent);
                    }
                    else {//reduce number of attempts
                        checkAttempts();
                    }

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeback();
            }
        });

    }

    public boolean checkPwd(String username, String password) {

        User user = db.getUser(username);
        String encrypt = db.md5(password);
        return (user.getPwd()).equals(encrypt);

    }

    public void checkAttempts() {
        if (attempts > 0) {
            attempts--;
            info = "No of attempts left: " + attempts;
            infoView.setText(info);
            Toast t = Toast.makeText(loginActivity.this,
                    "Password and Username don't match", Toast.LENGTH_LONG);
            t.show();
        }
        else {
            //TODO close button for 10 mins
            login.setEnabled(false);
        }
    }

    private void routeback()
    {
        Intent intent = new Intent(loginActivity.this, mainActivity.class);
        startActivity(intent);
    }



}



