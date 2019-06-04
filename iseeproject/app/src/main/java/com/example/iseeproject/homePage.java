package com.example.iseeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class homePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpbtn;
    dbHandler peopleDB;
    String usr = "";
    static String USERPREF = "USER"; // or other values
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setSessionPreferences();

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
                //Intent myIntent = new Intent(homePage.this, enterExpenses.class);
                //starting line graph activitiy here for testing reasons
                Intent myIntent = new Intent(homePage.this, lineGraph.class);
                Bundle b = new Bundle();
                b.putString("username",usr);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });


    }

    public void setSessionPreferences() {
        sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("username", usr);

        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu, menu);
        return true;
    }


    public void gotoHomepage(){
        Intent intent = new Intent(homePage.this,homePage.class);
       startActivity(intent);
    }

    public void logoutActivity(){
        Intent intent = new Intent(homePage.this,loginActivity.class);
        startActivity(intent);
    }

    public void enterDetails(){
        Intent intent = new Intent(homePage.this,updateDetail.class);
        startActivity(intent);
    }

    public boolean showHelp(){
        return true;
    }

    public void preferences(){
        Intent intent = new Intent(homePage.this,updateDetail.class);
    }

    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu,menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.HomePage:
                gotoHomepage();
                return true;

            case R.id.Preferences:
                preferences();
                return true;

            case  R.id.updateAccount:
                enterDetails();
                return true;

            case  R.id.logoutBtn:
                logoutActivity();
                return true;

                default:
                    return super.onContextItemSelected(item);
        }
    }







    public void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }


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
