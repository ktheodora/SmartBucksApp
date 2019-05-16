package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class enterExpenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expenses);

        Button button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                EditText LeisureEdit = (EditText)findViewById(R.id.LeisureEdit);
                String leis_str = LeisureEdit.getText().toString();

                EditText FoodEdit   = (EditText)findViewById(R.id.FoodEdit);
                String food_str = FoodEdit.getText().toString();

                EditText MiscEdit   = (EditText)findViewById(R.id.MiscEdit);
                String misc_str = MiscEdit.getText().toString();


                // Start NewActivity.class
                Intent myIntent = new Intent(enterExpenses.this,
                        HomePage.class);
                Bundle b = new Bundle();
                b.putString("leis_str", leis_str); //Your id
                b.putString("food_str", food_str); //Your id
                b.putString("misc_str", misc_str); //Your id
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
                startActivity(myIntent);
            }
        });
    }
}
