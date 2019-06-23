package com.example.iseeproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class updateDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button update, back;
    double Income = 0, Budget = 0,Rent =0,Bills =0,Insurance =0;
    String username, newTreshold ;
    dbHandler peopleDB;
    static String USERPREF = "USER"; // or other values
    private ImageButton menuBtn;
    Spinner spinner;
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


        Set<String> cats = peopleDB.getThresholds(username).keySet();
        List<String> categories= Arrays.asList(cats.toArray(new String[cats.size()]));

        spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> datAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categories);
        datAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(datAdapter);
        spinner.setOnItemSelectedListener(this);

        update=(Button)findViewById(R.id.finButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated(Income,Budget, Rent,Bills,Insurance);
                updateCats();
            }
        });

        back =(Button)findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomepage();
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
        finish();
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

    private void updated(double Income, double budget, double Rent, double Bills, double Insurance)
    {
        boolean b = false;
        EditText IncomeView   = (EditText)findViewById(R.id.entInc);
        //Only if value of view changes we update it, orelse we pass the initial again
        if (!TextUtils.isEmpty(IncomeView.getText())) {
            Income = Double.parseDouble(IncomeView.getText().toString());
            usr.setIncome(Income);
            b = true;
        }

        EditText BudgetView   = (EditText)findViewById(R.id.entBud);
        if (!TextUtils.isEmpty(BudgetView.getText())){
            budget = Double.parseDouble(BudgetView.getText().toString());
            usr.setBudget(budget);
            b = true;
        }

        EditText BillsView   = (EditText)findViewById(R.id.entBills);
        if (!TextUtils.isEmpty(BillsView.getText())){
            Bills = Double.parseDouble(BillsView.getText().toString());
            usr.setBills(Bills);
            b = true;
        }

        EditText RentView  = (EditText)findViewById(R.id.entRen);
        if (!TextUtils.isEmpty(RentView.getText())) {
            Rent = Double.parseDouble(RentView.getText().toString());
            usr.setRent(Rent);
            b = true;
        }

        EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
        if (!TextUtils.isEmpty(InsuranceView.getText())) {
            Insurance = Double.parseDouble(InsuranceView.getText().toString());
            usr.setInsurance(Insurance);
            b = true;
        }

        if (!b) {
            // if user didn't enter any details but pressed button
            Toast t = Toast.makeText(updateDetail.this,
                    "No values entered!", Toast.LENGTH_LONG);
            t.show();
        }
        else if ((Income) <= Rent + Bills + Insurance ) {
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
                Toast t = Toast.makeText(updateDetail.this,
                        "Changes Saved", Toast.LENGTH_LONG);
                t.show();

            }
        }

    }

    public void updateCats () {
        EditText entCat = (EditText) findViewById(R.id. entCat);
        EditText entThres = (EditText) findViewById(R.id.entThres);
        boolean b = false;
        dbHandler db = new dbHandler(this);
        if (!TextUtils.isEmpty(entCat.getText())) {
            if(TextUtils.isEmpty(entThres.getText())) {
                Toast t = Toast.makeText(updateDetail.this,
                        "Please enter threshold for new category", Toast.LENGTH_LONG);
                t.show();
            }
            else {//both values are given
                b = true;
            }
        }
        if (!TextUtils.isEmpty(entThres.getText())) {
            if(TextUtils.isEmpty(entCat.getText())) {
                Toast t = Toast.makeText(updateDetail.this,
                        "Please enter name for new category", Toast.LENGTH_LONG);
                t.show();
            }
            else {//both values are given
                b = true;
            }
        }

        if (b) {
            String newCat = entCat.getText().toString();
            if (!db.categoryExists(username,newCat)) {
                Double newThres = Double.parseDouble(entThres.getText().toString());
                db.addNewCategory(username, newCat, newThres);
                Toast t = Toast.makeText(updateDetail.this,
                        "Succesful addition of category " + newCat, Toast.LENGTH_LONG);
                t.show();
            }else {
                Toast t = Toast.makeText(updateDetail.this,
                        "Category name already exists" + newCat, Toast.LENGTH_LONG);
                t.show();
            }
        }

        EditText updatedThres = (EditText) findViewById(R.id.newThres);
        if (!TextUtils.isEmpty(updatedThres.getText())) {
            //only if a threshold is given
            String updatedCat = spinner.getSelectedItem().toString();
            Double upThres = Double.parseDouble(updatedThres.getText().toString());
            db.updateCategory(username, updatedCat, upThres);
            Toast t = Toast.makeText(updateDetail.this,
                    "Succesful update of "+ updatedCat +" threshold", Toast.LENGTH_LONG);
            t.show();
        }
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        EditText newThres = (EditText) findViewById(R.id.newThres);
        newTreshold = newThres.getText().toString();
        if (newTreshold.isEmpty()) {
            Toast.makeText(parent.getContext(), "Please enter threshold amount" , Toast.LENGTH_LONG).show();
        }
    }
}
