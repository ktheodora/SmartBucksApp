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
    dbHandler peopleDB;
    User usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        peopleDB = new dbHandler(this);

        Bundle b = getIntent().getExtras();
        //we pass all the arguments cause they need to be passed again on the homepage
        //in order to be displayed
        if (b != null)
            username = b.getString("username");

        User usr = peopleDB.getUser(username);

        update=(Button)findViewById(R.id.finButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated(Income,Rent,Bills,Insurance);
            }
        });
    }

<<<<<<< HEAD
        private void updated()
        {
<<<<<<< HEAD
            UserName   = (EditText)findViewById(R.id.entUsName);
            String usr_str = UserName.getText().toString();

            Password  = (EditText)findViewById(R.id.entPsWord);
            String pwd_str = UserName.getText().toString();

            Income   = (EditText)findViewById(R.id.entInc);
            String inc_str = UserName.getText().toString();
=======
    //TODO Double parsed Text Views
    //TODO Fix beackend to correspond with frontend
    private void updated(double Income,  double Rent, double Bills, double Insurance)
    {
        EditText IncomeView   = (EditText)findViewById(R.id.entInc);
        //Only if value of view changes we update it, orelse we pass the initial again
        if (!TextUtils.isEmpty(IncomeView.getText())) {
            Income = Double.parseDouble(IncomeView.getText().toString());
            usr.setIncome(Income);
        }
>>>>>>> devel/theo

        EditText BillsView   = (EditText)findViewById(R.id.entBills);
        if (!TextUtils.isEmpty(BillsView.getText())){
            Bills = Double.parseDouble(BillsView.getText().toString());
            usr.setBills(Bills);
        }

        EditText RentView  = (EditText)findViewById(R.id.entRen);
        if (!TextUtils.isEmpty(RentView.getText())) {
            Rent = Double.parseDouble(RentView.getText().toString());
            usr.setRent(Rent);
        }

        EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
        if (!TextUtils.isEmpty(InsuranceView.getText())) {
            Insurance = Double.parseDouble(InsuranceView.getText().toString());
            usr.setInsurance(Insurance);
        }

        if ((Income) <= Rent + Bills + Insurance ) {
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

=======
            EditText IncomeView   = (EditText)findViewById(R.id.entInc);
            //Only if value of view changes we update it, orelse we pass the initial again
            if (!TextUtils.isEmpty(IncomeView.getText())) {
                Income = Integer.parseInt(IncomeView.getText().toString());
            }

            EditText RentView  = (EditText)findViewById(R.id.entRent);
            if (!TextUtils.isEmpty(RentView.getText())) {
                Rent = Integer.parseInt(RentView.getText().toString());
            }

            EditText BillsView   = (EditText)findViewById(R.id.entBills);
            if (!TextUtils.isEmpty(BillsView.getText())){
                Bills = Integer.parseInt(BillsView.getText().toString());
            }

            EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
            if (!TextUtils.isEmpty(InsuranceView.getText())) {
                Insurance = Integer.parseInt(InsuranceView.getText().toString());
            }

            int extra_inc = 0;
            EditText ExtraIncView   = (EditText)findViewById(R.id.entExtraInc);
            if (!TextUtils.isEmpty(ExtraIncView.getText())) {
                extra_inc = Integer.parseInt(ExtraIncView.getText().toString());
            }

>>>>>>> 508499a... final basic prototype working version - small adjustments made

<<<<<<< HEAD
            // Start NewActivity.class
            Intent myIntent = new Intent(updateDetail.this,
                    HomePage.class);
            Bundle b = new Bundle();
<<<<<<< HEAD
            b.putInt("usr_str", usr_str); //Your id
            b.putInt("pwd_str", pwd_str); //Your id
            b.putInt("inc_str", inc_str); //Your id
            b.putInt("sum_str", sum_str); //Your id
            b.putInt("rent_str", rent_str); //Your id
            b.putInt("bills_str", bills_str); //Your id
            b.putInt("ins_str", ins_str); //Your id
=======
            b.putInt("income", (Income + extra_inc));
            b.putInt("rent", Rent);
            b.putInt("bills", Bills);
            b.putInt("ins", Insurance);
>>>>>>> 508499a... final basic prototype working version - small adjustments made
            myIntent.putExtras(b); //Put your id to your next Intent
            startActivity(myIntent);
            finish();
            startActivity(myIntent);
=======
                // Start NewActivity.class
                Intent myIntent = new Intent(updateDetail.this,
                        homePage.class);
                Bundle b = new Bundle();
                b.putString("username", username);
>>>>>>> devel/theo

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
            }
        }

    }
}
