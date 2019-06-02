package com.example.iseeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView info;
    private Button login;
    private int counter = 3;
    private Button back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        info = (TextView) findViewById(R.id.tvinfo);
        login = (Button)findViewById(R.id.login);
        back = (Button)findViewById(R.id.backbtn);


        info.setText("No Of Attempts Remaining : 3");
        //method will  be working when button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SINCE NAME IS EDIT TEXT USER WILL  get whatever is entered through getText and
                // then it is converted to String same done for password
                validate(Name.getText().toString(),Password.getText().toString() );
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeback();
            }
        });


    }

    private void validate(String userName, String userPassword)
    {
        if( (userName.equals("Pawan")) && (userPassword.equals("1234")) )
        {   //Switching from one Activity to another
            Intent intent1 = new Intent(loginActivity.this, homePage.class);
            Bundle b = new Bundle();
            b.putString("usr_str", userName); //Your id
            intent1.putExtras(b);
            startActivity(intent1);

        }

        else{
            counter-=1;
            String temp = "No of attempts is : ";
            temp += Integer.toString(counter);
            info.setText(temp);

            if(counter == 0)
            {
                login.setEnabled(false); //Disabling the button
            }
        }
    }

    private void routeback()
    {
        Intent intent = new Intent(loginActivity.this, mainActivity.class);
        startActivity(intent);
    }



}



