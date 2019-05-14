package com.example.iseeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class enterExpenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);

        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                LeisureEdit   = (EditText)findViewById(R.id.LeisureEdit);
                String leis_str = LeisureEdit.getText().toString();

                FoodEdit   = (EditText)findViewById(R.id.FoodEdit);
                String food_str = FoodEdit.getText().toString();

                MiscEdit   = (EditText)findViewById(R.id.MiscEdit);
                String misc_str = MiscEdit.getText().toString();


                // Start NewActivity.class
                Intent myIntent = new Intent(enterExpenses.this,
                        HomePage.class);
                Bundle b = new Bundle();
                b.putInt("leis_str", leis_str); //Your id
                b.putInt("food_str", food_str); //Your id
                b.putInt("misc_str", misc_str); //Your id
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
                startActivity(myIntent);
            }
        });
    }
}
