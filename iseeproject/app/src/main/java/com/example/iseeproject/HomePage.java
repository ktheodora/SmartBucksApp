package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HomePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpenses;
    private Button logout;
    DBHandler peopleDB;
    String usr = ""; // or other values
    double Income = 0;
    double Rent =0;
    double Bills =0;
    double Insurance =0;
    double extra_exp = 0;
    String famous_cat = "Not Defined";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        peopleDB = new DBHandler(this);
        Bundle b = getIntent().getExtras();

        if(b != null) {
           usr = b.getString("usr_str");
           /* Income = b.getInt("income");
            Rent = b.getInt("rent");
            Bills = b.getInt("bills");
            Insurance = b.getInt("ins");
            if (b.containsKey("extra_exp")) {
                extra_exp = b.getInt("extra_exp");
            }
            if (b.containsKey("famous_cat")) {
                famous_cat = b.getString("famous_cat");
            }*/

        }

        User userr = peopleDB.getUser(usr);


        //TODO Seperate function for calculating extra expenses
        List<Expenses> exp = peopleDB.getAllExpenses(userr);
        double foodcount, leiscount, medcount, billscount;
        double totalexpamount;

        //Function
         /*
            public double calculate()
        {
            for each(expense :exp){
            extra_exp += expense.getPrice();

            if (expense.getCategory() == "Food") {
                foodcount += expense.getPrice();
            } else if (expense.getCategory() == "Leisure") {
                leiscount += expense.getPrice();
            }
            totalexpamount += expense.getPrice();
        }

            double food perc = (foodcount / totalexpamount) * 100;
            ProgressView pg (ProgressView)findViewById(R.id.ProgViewFood);
            pg.set


        }
        */

        TextView MText = (TextView)findViewById(R.id.TotalExpenses);
        double totalexp = userr.getRent() + userr.getBills() + userr.getInsurance() + extra_exp;
        MText.setText(String.valueOf(totalexp));

        TextView mmText = (TextView)findViewById(R.id.Savings);
        mmText.setText(String.valueOf(Income - totalexp));

        TextView mmmText = (TextView)findViewById(R.id.FamousCategText);
        mmmText.setText(famous_cat);


       /** edit =(Button)findViewById(R.id.edbtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        enterExpenses = (Button)findViewById(R.id.expButton);
        enterExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routed2();
            }
        });**/

        enterExpenses = (Button)findViewById(R.id.logoutBtn);
        enterExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, MainActivity.class);
                startActivity(myIntent);

                logout = (Button) findViewById(R.id.logoutBtn);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(HomePage.this, LoginActivity.class);
                        startActivity(myIntent);
                    }
                });
            }

            private void routed() {
                Intent myIntent = new Intent(HomePage.this, updateDetail.class);
                Bundle b = new Bundle();
                b.putString("username", usr);
        /*b.putInt("income", Income);
        b.putInt("rent", Rent);
        b.putInt("bills", Bills);
        b.putInt("ins", Insurance);*/
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);

            }

            private void routed2() {
                Intent myIntent = new Intent(HomePage.this, enterExpenses.class);
                Bundle b = new Bundle();
                b.putString("username",usr);
              /**  b.putString("username", usr);
                b.putInt("income", Income);
                b.putInt("rent", Rent);
                b.putInt("bills", Bills);
                b.putInt("ins", Insurance);
                b.putString("famous_cat", famous_cat);**/
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }

}
    }