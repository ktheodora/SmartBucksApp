package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class createAccount extends AppCompatActivity {

    dbHandler peopleDB;
    private Button finish;
    private EditText UserName;
    private EditText Name;

    private  EditText EmailAdress;
    private EditText Password;
    private EditText Income;
    private EditText Budget;
    private EditText Rent;
    private EditText Bills;
    private EditText Insurance;
    private Map<String, Double> defThres;

    String username ,name, password , email ;
    double inc ,bud ,rent ,ins, bill ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        routed();

        peopleDB = new dbHandler(this);
        defThres = new HashMap<String, Double>();
        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            if  (validate())
                addData();
            }
        });
    }




    private void routed(){

        UserName   = (EditText)findViewById(R.id.etUsname);

        Password  = (EditText)findViewById(R.id.etPassword);

        EmailAdress =  (EditText)findViewById(R.id.entEmail);

        Name = (EditText)findViewById(R.id.entfullname);

        Income   = (EditText)findViewById(R.id.entInc);

        Budget  = (EditText)findViewById(R.id.entbudget);

        Rent  = (EditText)findViewById(R.id.entRen);

        Bills   = (EditText)findViewById(R.id.entBills);

        Insurance   = (EditText)findViewById(R.id.entIns);

        finish=(Button)findViewById(R.id.finButton);

    }


    public void addData(){
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = UserName.getText().toString();
                name = Name.getText().toString();
                password = Password.getText().toString();
                email = EmailAdress.getText().toString();

                User user1 = new User(username,password,name,email, inc, bud,rent,bill,ins);

                boolean insertData = peopleDB.addUser(user1);

                if (insertData){
                    peopleDB.addCatThresholds(username);
                    Toast.makeText(createAccount.this,"Data Successfully Inserted",Toast.LENGTH_SHORT).show();
                    //start new activity
                    Intent myIntent = new Intent(createAccount.this,
                            homePage.class);
                    Bundle b = new Bundle();
                    b.putString("username", username);
                    myIntent.putExtras(b); //Put your id to your next Intent
                    startActivity(myIntent);
                    finish();
                }
                else{
                    Toast.makeText(createAccount.this,"Data not Successfully Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        private boolean validate() {
        Boolean result = false;
        String usnReg = "^[a-zA-Z0-9_]*$";
        //String emailReg = "^(.+)@(.+)$";
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            String emailReg = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        // email regex is [word(inc. dot, underscore, minus symbols] @ [word] . [word (2-6 chars long)]


            if (TextUtils.isEmpty(UserName.getText()) || TextUtils.isEmpty(Name.getText())
                    || TextUtils.isEmpty(Password.getText()) || TextUtils.isEmpty(EmailAdress.getText())
                    || TextUtils.isEmpty(Income.getText()) || TextUtils.isEmpty(Rent.getText())
                    || TextUtils.isEmpty(Bills.getText())|| TextUtils.isEmpty(Insurance.getText())
                    || TextUtils.isEmpty(Budget.getText())) {
                Toast t = Toast.makeText(createAccount.this,
                        "All fields are required to proceed", Toast.LENGTH_LONG);
                t.show();
            }
            else if(!(UserName.getText().toString().matches(usnReg))) {
                //checking if username has a valid form
                Toast t = Toast.makeText(createAccount.this,
                        "Username must contain only alphanumeric or underscore characters", Toast.LENGTH_LONG);
                t.show();
                //to overcome database problems
            }
            else if (peopleDB.isUser(UserName.getText().toString())) {
                Toast t = Toast.makeText(createAccount.this,
                        "Username already taken", Toast.LENGTH_LONG);
                t.show();
            }
            else if (!(VALID_EMAIL_ADDRESS_REGEX .matcher(EmailAdress.getText().toString()).find())) {
                Toast t = Toast.makeText(createAccount.this,
                        "Not a valid email form", Toast.LENGTH_LONG);
                t.show();
            }
            else if((Password.getText().toString()).length() < 6) {
                Toast t = Toast.makeText(createAccount.this,
                        "Password must contain at least 6 characters", Toast.LENGTH_LONG);
                t.show();
            }
            else {
                //check income, budget and stable expenses added

                inc = Double.parseDouble(Income.getText().toString());
                bud = Double.parseDouble(Budget.getText().toString());
                rent = Double.parseDouble(Rent.getText().toString());
                ins = Double.parseDouble(Insurance.getText().toString());
                bill = Double.parseDouble(Bills.getText().toString());

                if((inc - (rent + bill + ins)) < bud) {
                    //we have to ensure that budget is less than income minus stable expenses
                    Toast t = Toast.makeText(createAccount.this,
                            "Budget cannot be less than income - stable expenses", Toast.LENGTH_LONG);
                    t.show();
                }
                else{
                    result = true;
                }
            }
            return result;
        }

}
