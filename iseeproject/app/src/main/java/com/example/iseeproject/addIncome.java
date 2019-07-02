package com.example.iseeproject;

import android.content.Intent;
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

public class addIncome extends AppCompatActivity {

    Button okbtn, backbtn;
    EditText extraInc;
    dbHandler peopleDB;
    String username;
    User usr;
    private ImageButton menuBtn;
    menuHandler MenuHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        //TODO Write code
        okbtn = (Button) (findViewById(R.id.btnAdd));
        extraInc = (EditText)(findViewById(R.id.extraInc));
        peopleDB = new dbHandler(this);

        Bundle b = getIntent().getExtras();
        //we pass all the arguments cause they need to be passed again on the homepage
        //in order to be displayed
        if (b != null)
            username = b.getString("username");

        usr = peopleDB.getUser(username);

        MenuHandler = new menuHandler(addIncome.this, username);
        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popup = new PopupMenu(addIncome.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return MenuHandler.onMenuItemClick(item);
                    }
                });
                popup.inflate(R.menu.drawermenu);
                popup.show();
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validate()) {
                   addData();
               }
            }
        });
        backbtn = (Button) (findViewById(R.id.backButton));
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(addIncome.this, homePage.class);
                Bundle b = new Bundle();
                b.putString("username",username);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });

    }

    private boolean validate() {
        boolean result = false;
        if (TextUtils.isEmpty(extraInc.getText())) {
            Toast t = Toast.makeText(addIncome.this,
                    "You must enter a value!", Toast.LENGTH_LONG);
            t.show();
            result = false;
        }
        else if ((Integer.parseInt(extraInc.getText().toString())) == 0) {
            Toast t = Toast.makeText(addIncome.this,
                    "Zero value not valid", Toast.LENGTH_LONG);
            t.show();
            result = false;
        }
        else {
            result = true;
        }
        return result;
    }
    private void addData() {
        double curr_income = usr.getIncome();
        double extra_inc = Double.parseDouble(extraInc.getText().toString());
        usr.setIncome(curr_income + extra_inc);
        peopleDB.updateUser(usr);
        Toast t = Toast.makeText(addIncome.this,
                "Additional income successfully added", Toast.LENGTH_LONG);
        t.show();
        Intent myIntent = new Intent(addIncome.this, addIncome.class);
        Bundle b = new Bundle();
        b.putString("username",username);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }
}
