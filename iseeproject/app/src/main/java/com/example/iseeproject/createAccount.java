package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createAccount extends AppCompatActivity {

    private Button finish;

    private EditText UserName;

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

        UserName   = (EditText)findViewById(R.id.entUsName);
        String usr_str = UserName.getText().toString();

        EditText Password  = (EditText)findViewById(R.id.entPsWord);
        String pwd_str = UserName.getText().toString();

        EditText Income   = (EditText)findViewById(R.id.entInc);
        int inc_str = Integer.parseInt(Income.getText().toString());

        EditText SumExp   = (EditText)findViewById(R.id.sumExp);
        int sum_str = Integer.parseInt(SumExp.getText().toString());

        EditText Rent  = (EditText)findViewById(R.id.entRent);
        int rent_str = Integer.parseInt(Rent.getText().toString());

        EditText Bills   = (EditText)findViewById(R.id.entBills);
        int bill_str = Integer.parseInt(Bills.getText().toString());

        EditText Insurance   = (EditText)findViewById(R.id.entIns);
        int ins_str = Integer.parseInt(Insurance.getText().toString());


        // Start NewActivity.class
        Intent myIntent = new Intent(createAccount.this,
                HomePage.class);
        Bundle b = new Bundle();
        b.putString("usr_str", usr_str); //Your id
        b.putString("pwd_str", pwd_str); //Your id
        b.putInt("inc_str", inc_str); //Your id
        b.putInt("sum_str", sum_str); //Your id
        b.putInt("rent_str", rent_str); //Your id
        b.putInt("bills_str", bill_str); //Your id
        b.putInt("ins_str", ins_str); //Your id
        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
        finish();
    }
}
