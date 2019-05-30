package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createAccount extends AppCompatActivity {

    private Button finish;

    private EditText UserName;
    private EditText Password;
    private EditText Income;
    private EditText Rent;
    private EditText Bills;
    private EditText Insurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        finish=(Button)findViewById(R.id.finButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                routed();
            }
        });
    }

    private void routed(){

        UserName   = (EditText)findViewById(R.id.etName);

        Password  = (EditText)findViewById(R.id.etPassword);

        Income   = (EditText)findViewById(R.id.entInc);

        Rent  = (EditText)findViewById(R.id.entRen);

        Bills   = (EditText)findViewById(R.id.entBills);

        Insurance   = (EditText)findViewById(R.id.entIns);

         if (TextUtils.isEmpty(UserName.getText()) || TextUtils.isEmpty(Password.getText())
         || TextUtils.isEmpty(Income.getText()) || TextUtils.isEmpty(Rent.getText())
         || TextUtils.isEmpty(Bills.getText())|| TextUtils.isEmpty(Insurance.getText())) {
             Toast t = Toast.makeText(createAccount.this,
                     "All fields are required to proceed", Toast.LENGTH_LONG);
             t.show();
         }
         else if((Password.getText().toString()).length() < 6) {
             Toast t = Toast.makeText(createAccount.this,
                     "Password must contain at least 6 characters", Toast.LENGTH_LONG);
             t.show();
         }
         //TODO else if ("usr_str" == "already exists in database") {}
         else if (Integer.parseInt(Income.getText().toString()) <= Integer.parseInt(Rent.getText().toString())
                 + Integer.parseInt(Bills.getText().toString()) + Integer.parseInt(Insurance.getText().toString())) {
             //we have to ensure that income is always more than expenses
             //we cannot convert to int until we make sure that fields are not null
             Toast t = Toast.makeText(createAccount.this,
                     "Income cannot be equal or less to stable expenses", Toast.LENGTH_LONG);
             t.show();
         }
         else {
        // Start NewActivity.class
            Intent myIntent = new Intent(createAccount.this,
                    HomePage.class);
            Bundle b = new Bundle();
            b.putString("usr_str", UserName.getText().toString());
            b.putString("pwd_str", Password.getText().toString());
            b.putInt("income", Integer.parseInt(Income.getText().toString()));
            b.putInt("rent", Integer.parseInt(Rent.getText().toString()));
            b.putInt("bills", Integer.parseInt(Bills.getText().toString()));
            b.putInt("ins", Integer.parseInt(Insurance.getText().toString()));
            myIntent.putExtras(b); //Put your id to your next Intent
            startActivity(myIntent);
         }
    }
}
