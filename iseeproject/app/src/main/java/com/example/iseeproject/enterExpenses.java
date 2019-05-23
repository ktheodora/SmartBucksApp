package com.example.iseeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD

public class enterExpenses extends AppCompatActivity {

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

<<<<<<< HEAD
        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                LeisureEdit   = (EditText)findViewById(R.id.LeisureEdit);
                String leis_str = LeisureEdit.getText().toString();

                FoodEdit   = (EditText)findViewById(R.id.FoodEdit);
                String food_str = FoodEdit.getText().toString();

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
                }
            }
        });

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
                startActivity(myIntent);
                finish();
            }
        });
    }
}
