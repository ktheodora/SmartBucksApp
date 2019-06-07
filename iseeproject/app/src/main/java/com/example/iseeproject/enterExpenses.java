package com.example.iseeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class enterExpenses extends AppCompatActivity  implements AdapterView.OnItemSelectedListener
{
    dbHandler peopleDB;
    String username;
    EditText datepick, amount;
    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);



        Spinner spinner= (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Cash");
        categories.add("Card  ");
        categories.add("Online");


        // Creating adapter for spinner
        ArrayAdapter<String> datAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        datAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(datAdapter);
        peopleDB = new dbHandler(this);

        Bundle b = getIntent().getExtras();
        if (b != null)
            username = b.getString("username");

        User usr = peopleDB.getUser(username);

        //we set the calendar view in the ui
        myCalendar = Calendar.getInstance();

        datepick = (EditText) findViewById(R.id.selectDate);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        datepick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(enterExpenses.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }






        });




        //we set the button behaviour
        Button confirmbtn = (Button) findViewById(R.id.confirmBtn);
        Button backbtn = (Button) findViewById(R.id.backBtn);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //getting values of the parameters of the new expense

                String category = "";
                //TODO Ensure that only one button is checked at a time




                /*RadioButton rb1 = (RadioButton) findViewById(R.id.leisurebutton);
                RadioButton rb2 = (RadioButton) findViewById(R.id.foodbutton);
                RadioButton rb3 = (RadioButton) findViewById(R.id.billbutton);
                RadioButton rb4 = (RadioButton) findViewById(R.id.miscbutton);

                if (rb1.isChecked()) {
                    category = "LEISURE";
                }
                else if (rb2.isChecked()) {
                    category = "FOOD";
                }
                else if (rb3.isChecked()) {
                    category = "BILLS";
                }
                else if (rb4.isChecked()) {
                    category = "MISCELLANEOUS";
                }*/







                    amount = (EditText) findViewById(R.id.amountText);



                    //TODO check if payment method is also selected
                    if (TextUtils.isEmpty(datepick.getText())  || TextUtils.isEmpty(amount.getText()) ){
                        Toast t = Toast.makeText(enterExpenses.this,
                                "All fields must be given", Toast.LENGTH_LONG);
                        t.show();
                }
                else {
                    User user = peopleDB.getUser(username);
                    //get sum of money spent in expenses
                    double sum = 0;
                    if (peopleDB.expensesExist(user)) {
                        List<Expenses> exp = peopleDB.getAllExpenses(user);
                        for (Expenses expense : exp) {
                            sum += expense.getPrice();
                        }
                    }
                    double expAmount = Double.parseDouble(amount.getText().toString());
                    //if current expense price sumed with the already existing expenses is higher than budget
                    //then show toast message
                    if (user.getBudget() <= sum + expAmount) {
                        Toast t = Toast.makeText(enterExpenses.this,
                                "Expenses entered overcome savings", Toast.LENGTH_LONG);
                        t.show();
                    }
                    else {//move on with the addtion of the expense to the database
                        String additionTime = peopleDB.getCurrDate();
                        String expenseTime = datepick.getText().toString();


                        //adding temporarily a default chosen payment method
                        String payment_method = "CASH";

                        //creating the expense instance and adding it to the database
                        Expenses newExpense = new Expenses(additionTime,expenseTime,username,expAmount,category,payment_method);

                        peopleDB.addExpenses(newExpense);

                        // Start NewActivity.class
                        Intent myIntent = new Intent(enterExpenses.this,
                                homePage.class);

                        Bundle b = new Bundle();
                        b.putString("username",username);
                        myIntent.putExtras(b); //Put your id to your next Intent
                        startActivity(myIntent);
                    }
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(enterExpenses.this,
                        homePage.class);
                Bundle b = getIntent().getExtras();
                username = b.getString("username");
                myIntent.putExtras(b);
                startActivity(myIntent);
                finish();
            }
        });



    }


    public void onRadioButtonClicked( View v  ){

       // String category = "";
        RadioButton rb1 = (RadioButton) findViewById(R.id.leisurebutton);
        RadioButton rb2 = (RadioButton) findViewById(R.id.foodbutton);
        RadioButton rb3 = (RadioButton) findViewById(R.id.billbutton);
        RadioButton rb4 = (RadioButton) findViewById(R.id.miscbutton);

        boolean checked = ((RadioButton ) v).isChecked();
        switch(v.getId()){

            case R.id.leisurebutton:
                if (checked)
                    rb1.setTypeface(null, Typeface.BOLD_ITALIC);
                //set the other two radio buttons text style to default
                rb2.setTypeface(null, Typeface.NORMAL);

                rb3.setTypeface(null, Typeface.NORMAL);
                rb4.setTypeface(null, Typeface.NORMAL);
                break;


            case R.id.foodbutton:
                if(checked)
                    rb2.setTypeface(null, Typeface.BOLD_ITALIC);
                rb1.setTypeface(null, Typeface.NORMAL);
                rb3.setTypeface(null, Typeface.NORMAL);
                rb4.setTypeface(null, Typeface.NORMAL);
                break;

            case R.id.billbutton:
                if(checked)
                    rb3.setTypeface(null, Typeface.BOLD_ITALIC);
                rb1.setTypeface(null, Typeface.NORMAL);
                rb2.setTypeface(null, Typeface.NORMAL);
                rb4.setTypeface(null, Typeface.NORMAL);
                break;

            case R.id.miscbutton:
                if(checked)
                    rb4.setTypeface(null, Typeface.BOLD_ITALIC);

                rb1.setTypeface(null, Typeface.NORMAL);
                rb2.setTypeface(null, Typeface.NORMAL);
                rb3.setTypeface(null, Typeface.NORMAL);


        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.welcomeUseractivity) {
            return true;
        }

        return enterExpenses.super.onOptionsItemSelected(item);
    }




    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        datepick.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast t = Toast.makeText(enterExpenses.this,
                "Select one Category", Toast.LENGTH_LONG);
        t.show();
    }
}
