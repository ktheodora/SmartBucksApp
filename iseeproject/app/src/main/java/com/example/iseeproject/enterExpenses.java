package com.example.iseeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java. util. Date;
import java. sql. Timestamp;
import java.util.Locale;

public class enterExpenses extends AppCompatActivity {
    DBHandler peopleDB;
    String username;

    EditText datepick;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);
        peopleDB = new DBHandler(this);

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

        Button confirmbtn = (Button) findViewById(R.id.confirmBtn);

        Button backbtn = (Button) findViewById(R.id.backBtn);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //getting values of the parameters of the new expense
                String additionTime = peopleDB.getCurrDate();
                String expenseTime = datepick.getText().toString();
                //TODO retrieve the rest of the values

                //creating the expense instance and adding it to the database
                Expenses exp = new Expenses();

                //TODO fix expenses price check
                if (Income < extra_exp + extra_expen + Rent + Bills + Insurance) {
                    Toast t = Toast.makeText(enterExpenses.this,
                            "Expenses entered overcome savings", Toast.LENGTH_LONG);
                    t.show();
                }
                else {
                    // Start NewActivity.class
                    Intent myIntent = new Intent(enterExpenses.this,
                            HomePage.class);

                      // peopleDB = new DBHandler(this);
                        Bundle b = getIntent().getExtras();
                        username =b.getString("username");

                    myIntent.putExtras(b); //Put your id to your next Intent
                    startActivity(myIntent);
                    finish();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(enterExpenses.this,
                        HomePage.class);
                Bundle b = getIntent().getExtras();
                username = b.getString("username");
                myIntent.putExtras(b);
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
