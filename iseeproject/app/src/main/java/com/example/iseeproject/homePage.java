package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class homePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpbtn;
    dbHandler peopleDB;
    String usr = ""; // or other values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        peopleDB = new dbHandler(this);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            usr = b.getString("username");
        }

        User userr = peopleDB.getUser(usr);

        //Set values of Text Views in homePage

        TextView budgetView = (TextView) findViewById(R.id.budgetview);
        budgetView.setText(String.valueOf(userr.getBudget()));

        TextView incomeView = (TextView) findViewById(R.id.totalInc);
        incomeView.setText(String.valueOf(userr.getIncome()));

        TextView expensesView = (TextView) findViewById(R.id.TotalExpenses);
        //get sum of money spent in expenses
        List<Expenses> exp = peopleDB.getAllExpenses(userr);
        double sum=0;
        for (Expenses expense : exp) {
            sum+=expense.getPrice();
        }
        expensesView.setText(String.valueOf(sum));

        TextView savingsView = (TextView) findViewById(R.id.SavingsView);
        //savings = budget - sum of expenses
        double savings = userr.getBudget() - sum ;
        expensesView.setText(String.valueOf(savings));

        //add expenses button setup

        enterExpbtn =(Button)findViewById(R.id.addExpenses);
        enterExpbtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent myIntent = new Intent(homePage.this, enterExpenses.class);
                Bundle b = new Bundle();
                b.putString("username",usr);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu, menu);
        return true;
    }

    /*
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            /*case R.id.homePage:
                goToHomePage();
                return true;
            case R.id.Preferences:
                showHelp();
                return true;
            case R.id.EnterDetails:
                return true;
            case R.id.logoutBtn:
                return true;
                case R.id.help
            default:
                return super.onOptionsItemSelected(item);
        }

        }
    }
    */



    //TODO functions for stats and graphs of homepage
    //public expProg

    public void calculate() {
        //TODO Seperate function for calculating extra expenses
        /*List<Expenses> exp = peopleDB.getAllExpenses(user);
        double foodcount, leiscount, medcount, billscount;
        double totalexpamount;
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
            */
    }

}
