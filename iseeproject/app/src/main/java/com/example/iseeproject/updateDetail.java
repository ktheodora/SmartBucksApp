package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateDetail extends AppCompatActivity {
        private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        update=(Button)findViewById(R.id.updtbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updated();
            }
        });
    }

        private void updated()
        {
            EditText UserName   = (EditText)findViewById(R.id.entUsName);
            String usr_str = UserName.getText().toString();

            EditText Password  = (EditText)findViewById(R.id.entPsWord);
            String pwd_str = UserName.getText().toString();

            EditText Income   = (EditText)findViewById(R.id.entInc);
            String inc_str = UserName.getText().toString();

            EditText SumExp   = (EditText)findViewById(R.id.sumExp);
            String sum_str = UserName.getText().toString();

            EditText Rent  = (EditText)findViewById(R.id.entRent);
            String rent_str = UserName.getText().toString();

            EditText Bills   = (EditText)findViewById(R.id.entBills);
            String bill_str = UserName.getText().toString();

            EditText Insurance   = (EditText)findViewById(R.id.entIns);
            String ins_str = UserName.getText().toString();


            // Start NewActivity.class
            Intent myIntent = new Intent(updateDetail.this,
                    HomePage.class);
            Bundle b = new Bundle();
            b.putString("usr_str", usr_str); //Your id
            b.putString("pwd_str", pwd_str); //Your id
            b.putString("inc_str", inc_str); //Your id
            b.putString("sum_str", sum_str); //Your id
            b.putString("rent_str", rent_str); //Your id
            b.putString("bills_str", bill_str); //Your id
            b.putString("ins_str", ins_str); //Your id
            myIntent.putExtras(b); //Put your id to your next Intent
            startActivity(myIntent);
            finish();
            startActivity(myIntent);

        }
}

