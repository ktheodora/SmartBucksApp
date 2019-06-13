package com.example.iseeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class updateDetail extends AppCompatActivity {
    private Button update;
    double Income = 0;
    double Rent =0;
    double Bills =0;
    double Insurance =0;
    String username ;
    dbHandler peopleDB;
    static String USERPREF = "USER"; // or other values
    private ImageButton menuBtn;
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

        usr = peopleDB.getUser(username);

        update=(Button)findViewById(R.id.finButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated(Income,Rent,Bills,Insurance);
            }
        });

        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popup = new PopupMenu(updateDetail.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.HomePage:
                                goToHomepage();
                                return true;

                            case R.id.Preferences:
                                showToast("Preferences under construction");
                                return true;

                            case  R.id.item2:
                                goToDetails();
                                return true;

                            case  R.id.logoutBtn:
                                logout();
                                return true;

                            case  R.id.item12:
                                showToast("FAQ under construction");
                                return true;
                            case R.id.report:
                                showToast("Report under construction");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.drawermenu);
                popup.show();
            }
        });

    }

    public void goToHomepage() {
        Intent myIntent = new Intent(updateDetail.this, homePage.class);
        Bundle b = new Bundle();
        b.putString("username",username);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }

    public void goToDetails() {
        Intent myIntent = new Intent(updateDetail.this, updateDetail.class);
        Bundle b = new Bundle();
        b.putString("username",username);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }

    public void showToast(String text) {
        Toast t = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        t.show();
    }

    public void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        //then redirect to initial activity
        Intent myIntent = new Intent(updateDetail.this, mainActivity.class);
        startActivity(myIntent);
    }


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
                // Start NewActivity.class
                Intent myIntent = new Intent(updateDetail.this,
                        homePage.class);
                Bundle b = new Bundle();
                b.putString("username", username);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
            }
        }

    }
}
