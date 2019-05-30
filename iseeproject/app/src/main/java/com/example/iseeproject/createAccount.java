package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class createAccount extends AppCompatActivity {

    DBHandler peopleDB;
    private Button finish;
    private EditText UserName;
    private EditText Name;

    //private  EditText EmailAdress;
    private EditText Password;
    private EditText Income;
    private EditText Rent;
    private EditText Bills;
    private EditText Insurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        routed();

        peopleDB = new DBHandler(this);
        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if  (validate())
                addData();
            }
        });
    }




    private void routed(){

        UserName   = (EditText)findViewById(R.id.etName);

        Password  = (EditText)findViewById(R.id.etPassword);

        Income   = (EditText)findViewById(R.id.entInc);

        Rent  = (EditText)findViewById(R.id.entRen);

        Bills   = (EditText)findViewById(R.id.entBills);

        finish=(Button)findViewById(R.id.finButton);

        Insurance   = (EditText)findViewById(R.id.entIns);



    }

    public void addData(){
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = UserName.getText().toString();
                String surname = Name.getText().toString();
                String password = Password.getText().toString();
                String income = Income.getText().toString();
                String bills = Bills.getText().toString();
                String insurance = Insurance.getText().toString();
                String rents = Rent.getText().toString();
                double R = Double.parseDouble(rents);
                double i = Double.parseDouble(insurance);
                double inc = Double.parseDouble(income);
                double bill = Double.parseDouble(bills);

                User user1 = new User(name,password,surname,inc,R,bill,i);

                boolean insertData = peopleDB.addUser(user1);


                if (insertData == true){
                        Toast.makeText(createAccount.this,"Data Successfully Inserted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(createAccount.this,"Data not Successfully Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

        private boolean validate()
        {   Boolean result = false;

            if (TextUtils.isEmpty(UserName.getText()) || TextUtils.isEmpty(Password.getText())
                    || TextUtils.isEmpty(Income.getText()) || TextUtils.isEmpty(Rent.getText())
                    || TextUtils.isEmpty(Bills.getText())|| TextUtils.isEmpty(Insurance.getText())) {
                Toast t = Toast.makeText(createAccount.this,
                        "All fields are required to proceed", Toast.LENGTH_LONG);
                t.show();
            }
            else if((Password.getText().toString()).length() < 6) {
                Toast t = Toast.makeText(createAccount.this,
                        "Password must contain at least 6 characters", Toast.LENGTH_LONG);
                t.show();
            }
            else if (peopleDB.isUser(UserName.getText().toString())) {
                Toast t = Toast.makeText(createAccount.this,
                        "Username already taken", Toast.LENGTH_LONG);
                t.show();
            }
            else if (Double.parseDouble(Income.getText().toString()) <= Double.parseDouble(Rent.getText().toString())
                    + Double.parseDouble(Bills.getText().toString()) + Double.parseDouble(Insurance.getText().toString())) {
                //we have to ensure that income is always more than expenses
                //we cannot convert to int until we make sure that fields are not null
                Toast t = Toast.makeText(createAccount.this,
                        "Income cannot be equal or less to stable expenses", Toast.LENGTH_LONG);
                t.show();
            }

            else
            {
                result = true;
            }

            return result;
        }

}
