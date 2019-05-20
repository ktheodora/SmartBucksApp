package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class enterExpenses extends AppCompatActivity {

    int Income ;
    int Rent ;
    int Bills ;
    int Insurance ;
    String famous_cat = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);

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

        Button button = (Button) findViewById(R.id.submitBtn);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                int leis = 0, food = 0, misc = 0;
                int extra_exp = 0;
                String famous_cat = "";
                EditText LeisureEdit = (EditText)findViewById(R.id.LeisureEdit);
                if (LeisureEdit.getText().toString() != "") {
                    leis = Integer.parseInt(LeisureEdit.getText().toString());
                    extra_exp+= leis;
                }

                EditText FoodEdit   = (EditText)findViewById(R.id.FoodEdit);
                if (FoodEdit.getText().toString() != "") {
                    food = Integer.parseInt(FoodEdit.getText().toString());
                    extra_exp+= food;
                }

                EditText MiscEdit   = (EditText)findViewById(R.id.MiscEdit);
                if (MiscEdit.getText().toString() != "") {
                    misc = Integer.parseInt(LeisureEdit.getText().toString());
                    extra_exp+= misc;
                }
                //now finding out for which category we have the most expenses so far
                //we can only find current value of category because we don't have previous data
                if ((leis > food) && (leis > misc)) {
                    famous_cat = "Leisure";
                }
                else if((food > leis) && (food > misc)) {
                    famous_cat = "Food";
                }
                else if((misc > leis) && (misc > food)) {
                    famous_cat = "Miscelaneous";
                }
                //in case of same values in a category, the string remails the same
                // with the last time that we got it from the bundle

                // Start NewActivity.class
                Intent myIntent = new Intent(enterExpenses.this,
                        HomePage.class);
                Bundle b = new Bundle();
                b.putInt("income", Income);
                b.putInt("rent", Rent);
                b.putInt("bills", Bills);
                b.putInt("ins", Insurance);
                b.putInt("extra_exp", extra_exp);
                b.putString("famous_cat", famous_cat);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
                startActivity(myIntent);
            }
        });
    }
}
