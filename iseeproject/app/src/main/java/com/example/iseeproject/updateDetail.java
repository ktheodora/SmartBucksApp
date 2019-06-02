package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateDetail extends AppCompatActivity {
    private Button update;
    double Income = 0;
    double Rent =0;
    double Bills =0;
    double Insurance =0;
    String username ;
    DBHandler peopleDB;
    User usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        peopleDB = new DBHandler(this);

        Bundle b = getIntent().getExtras();
        //we pass all the arguments cause they need to be passed again on the homepage
        //in order to be displayed
        username = b.getString("username");
        User usr = peopleDB.getUser(username);

        update=(Button)findViewById(R.id.updtbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated(Income,Rent,Bills,Insurance);
            }
        });
    }

    //TODO Double parsed Text Views
    //TODO Fix beackend to correspond with frontend
        private void updated(double Income,  double Rent, double Bills, double Insurance)
        {
            EditText IncomeView   = (EditText)findViewById(R.id.entInc);
            //Only if value of view changes we update it, orelse we pass the initial again
            if (!TextUtils.isEmpty(IncomeView.getText())) {
                Income = Integer.parseInt(IncomeView.getText().toString());
                usr.setIncome(Income);

            }

            EditText RentView  = (EditText)findViewById(R.id.entRent);
            if (!TextUtils.isEmpty(RentView.getText())) {
                Rent = Integer.parseInt(RentView.getText().toString());
                usr.setRent(Rent);
            }

            EditText BillsView   = (EditText)findViewById(R.id.entBills);
            if (!TextUtils.isEmpty(BillsView.getText())){
                Bills = Integer.parseInt(BillsView.getText().toString());
                usr.setBills(Bills);
            }

            EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
            if (!TextUtils.isEmpty(InsuranceView.getText())) {
                Insurance = Integer.parseInt(InsuranceView.getText().toString());
                usr.setInsurance(Insurance);
            }

            double extra_inc = 0;
            EditText ExtraIncView   = (EditText)findViewById(R.id.entExtraInc);
            if (!TextUtils.isEmpty(ExtraIncView.getText())) {
                extra_inc = Integer.parseInt(ExtraIncView.getText().toString());
                usr.setIncome(usr.getIncome()+extra_inc);
            }

            if ((Income + extra_inc) <= Rent + Bills + Insurance ) {
                Toast t = Toast.makeText(updateDetail.this,
                        "Total income cannot be equal or less to stable expenses", Toast.LENGTH_LONG);
                t.show();
            }
            else {
                int isUpdated = peopleDB.updateUser(usr);
                if (isUpdated == 0) {
                    Toast t = Toast.makeText(updateDetail.this,
                            "Problem with Database Update", Toast.LENGTH_LONG);
                    t.show();
                }
                else {


                    // Start NewActivity.class
                    Intent myIntent = new Intent(updateDetail.this,
                            HomePage.class);
                    Bundle b = new Bundle();
                    b.putString("username", username);

                    myIntent.putExtras(b); //Put your id to your next Intent
                    startActivity(myIntent);
                    finish();
                }
            }

        }
}

