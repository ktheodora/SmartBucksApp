package com.example.iseeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
>>>>>>> devel/theo

public class enterExpenses extends AppCompatActivity {
    dbHandler peopleDB;
    String username;
    EditText datepick, amount;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class enterExpenses extends AppCompatActivity {

    int Income ;
    int Rent ;
    int Bills ;
    int Insurance ;
    String famous_cat = "Not Defined";
    int extra_exp = 0;
>>>>>>> 508499a... final basic prototype working version - small adjustments made
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);
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

        //TODO Fix spinner

<<<<<<< HEAD
<<<<<<< HEAD
        button = (Button) findViewById(R.id.button4);
=======
        //we set the button behaviour
        Button confirmbtn = (Button) findViewById(R.id.confirmBtn);
>>>>>>> devel/theo

        Button backbtn = (Button) findViewById(R.id.backBtn);


        confirmbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //getting values of the parameters of the new expense

                String category = "";
                //TODO Ensure that only one button is checked at a time
                RadioButton rb1 = (RadioButton) findViewById(R.id.leisurebutton);
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
                }

                amount = (EditText) findViewById(R.id.amountText);

                //TODO check if payment method is also selected
                if (TextUtils.isEmpty(datepick.getText()) || category.equals("") || TextUtils.isEmpty(amount.getText()) ){
                    Toast t = Toast.makeText(enterExpenses.this,
                            "All fields must be given", Toast.LENGTH_LONG);
                    t.show();
                }
                else {
                    User user = peopleDB.getUser(username);
                    //get sum of money spent in expenses
                    List<Expenses> exp = peopleDB.getAllExpenses(user);
                    double sum=0;
                    for (Expenses expense : exp) {
                        sum+=expense.getPrice();
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

                        //TODO Take payment method from spinner
                        //adding temporarily a default chosen payment method
                        String payment_method = "CASH";

<<<<<<< HEAD
                MiscEdit   = (EditText)findViewById(R.id.MiscEdit);
                String misc_str = MiscEdit.getText().toString();
=======
        Bundle b = getIntent().getExtras();
        //we pass all the arguments cause they need to be passed again on the homepage
        //in order to be displayed
        Income = b.getInt("income");
        Rent = b.getInt("rent");
        Bills = b.getInt("bills");
        Insurance = b.getInt("ins");
        if (b.containsKey("famous_cat")) {
            famous_cat = b.getString("famous_cat");
        }
        if (b.containsKey("extra_exp")) {
            extra_exp = b.getInt("extra_exp");
        }

        Button button1 = (Button) findViewById(R.id.submitBtn);

        Button button2 = (Button) findViewById(R.id.backBtn);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                int leis = 0, food = 0, misc = 0, extra_expen = 0;
                String famous_categ = "";
                EditText LeisureEdit = (EditText)findViewById(R.id.LeisureEdit);
                if (LeisureEdit.getText().toString() != "") {
                    leis = Integer.parseInt(LeisureEdit.getText().toString());
                    extra_expen+= leis;
                }

                EditText FoodEdit   = (EditText)findViewById(R.id.FoodEdit);
                if (FoodEdit.getText().toString() != "") {
                    food = Integer.parseInt(FoodEdit.getText().toString());
                    extra_expen+= food;
                }

                EditText MiscEdit   = (EditText)findViewById(R.id.MiscEdit);
                if (MiscEdit.getText().toString() != "") {
                    misc = Integer.parseInt(LeisureEdit.getText().toString());
                    extra_expen+= misc;
                }
                //now finding out for which category we have the most expenses so far
                //we can only find current value of category because we don't have previous data
                if ((leis > food) && (leis > misc)) {
                    famous_categ = "Leisure";
                }
                else if(food > misc) {
                    famous_categ = "Food";
                }
                else if (misc > food) {
                    famous_categ = "Miscelaneous";
                }
                else {
                    famous_categ = "Equal Category Expenses";
                }
>>>>>>> 508499a... final basic prototype working version - small adjustments made


                if (Income < extra_exp + extra_expen + Rent + Bills + Insurance) {
                    Toast t = Toast.makeText(enterExpenses.this,
                            "Expenses entered overcome savings", Toast.LENGTH_LONG);
                    t.show();
                }
                else {
                    // Start NewActivity.class
                    Intent myIntent = new Intent(enterExpenses.this,
                            HomePage.class);
                    Bundle b = new Bundle();
                    b.putInt("income", Income);
                    b.putInt("rent", Rent);
                    b.putInt("bills", Bills);
                    b.putInt("ins", Insurance);
                    b.putInt("extra_exp", (extra_exp + extra_expen)); //adding previous and new expenses
                    b.putString("famous_cat", famous_categ);
                    myIntent.putExtras(b); //Put your id to your next Intent
                    startActivity(myIntent);
                    finish();
=======
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
>>>>>>> devel/theo
                }
            }
        });

<<<<<<< HEAD
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(enterExpenses.this,
                        HomePage.class);
                Bundle b = new Bundle();
<<<<<<< HEAD
                b.putInt("leis_str", leis_str); //Your id
                b.putInt("food_str", food_str); //Your id
                b.putInt("misc_str", misc_str); //Your id
                myIntent.putExtras(b); //Put your id to your next Intent
=======
                b.putInt("income", Income);
                b.putInt("rent", Rent);
                b.putInt("bills", Bills);
                b.putInt("ins", Insurance);
                b.putInt("extra_exp", extra_exp);
                b.putString("famous_cat", famous_cat);
                myIntent.putExtras(b);
>>>>>>> 508499a... final basic prototype working version - small adjustments made
=======
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(enterExpenses.this,
                        homePage.class);
                Bundle b = getIntent().getExtras();
                username = b.getString("username");
                myIntent.putExtras(b);
>>>>>>> devel/theo
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        datepick.setText(sdf.format(myCalendar.getTime()));
    }
}
