package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class createAccount extends AppCompatActivity {

    private Button finish;

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

        Password  = (EditText)findViewById(R.id.entPsWord);
        String pwd_str = UserName.getText().toString();

        Income   = (EditText)findViewById(R.id.entInc);
        String inc_str = UserName.getInt().toString();

        SumExp   = (EditText)findViewById(R.id.sumExp);
        String sum_str = UserName.getInt().toString();

        Rent  = (EditText)findViewById(R.id.entRent);
        String rent_str = UserName.getInt().toString();

        Bills   = (EditText)findViewById(R.id.entBills);
        String bill_str = UserName.getInt().toString();

        Insurance   = (EditText)findViewById(R.id.entIns);
        String ins_str = UserName.getInt().toString();


        // Start NewActivity.class
        Intent myIntent = new Intent(MainActivity.this,
                HomePage.class);
        Bundle b = new Bundle();
        b.putText("usr_str", usr_str); //Your id
        b.putText("pwd_str", pwd_str); //Your id
        b.putInt("inc_str", inc_str); //Your id
        b.putInt("sum_str", sum_str); //Your id
        b.putInt("rent_str", rent_str); //Your id
        b.putInt("bills_str", bills_str); //Your id
        b.putInt("ins_str", ins_str); //Your id
        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
        finish();
    }
}
