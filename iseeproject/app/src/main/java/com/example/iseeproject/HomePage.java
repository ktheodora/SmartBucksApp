package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
        private Button edit;
    private Button enterExpenses;
    String usr = ""; // or other values
    int Income = 0;
    int Rent =0;
    int Bills =0;
    int Insurance =0;
    int extra_exp = 0;
    String famous_cat = "Not Defined";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            usr = b.getString("usr_str");
            Income = b.getInt("income");
            Rent = b.getInt("rent");
            Bills = b.getInt("bills");
            Insurance = b.getInt("ins");
            if (b.containsKey("extra_exp")) {
                extra_exp = b.getInt("extra_exp");
            }
            if (!(b.getString("famous_cat ") == null)) {
                famous_cat = b.getString("famous_cat");
            }

        }
        TextView mText = (TextView)findViewById(R.id.TotalExpenses);
        int totalexp = Rent + Bills + Insurance + extra_exp;
        mText.setText(String.valueOf(totalexp));

        TextView mmText = (TextView)findViewById(R.id.Savings);
        mmText.setText(String.valueOf(Income - totalexp));

        TextView mmmText = (TextView)findViewById(R.id.FamousCategText);
        mmText.setText(famous_cat);


        edit =(Button)findViewById(R.id.edbtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routed();
            }
        });

        enterExpenses = (Button)findViewById(R.id.expButton);
        enterExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routed2();
            }
        });
    }

    private void routed()
    {
        Intent myIntent = new Intent(HomePage.this,updateDetail.class);
        Bundle b = new Bundle();
        b.putInt("income", Income);
        b.putInt("rent", Rent);
        b.putInt("bills", Bills);
        b.putInt("ins", Insurance);
        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);

    }

    private void routed2()
    {
        Intent myIntent = new Intent(HomePage.this,enterExpenses.class);
        Bundle b = new Bundle();
        b.putInt("income", Income);
        b.putInt("rent", Rent);
        b.putInt("bills", Bills);
        b.putInt("ins", Insurance);
        b.putString("famous_cat",famous_cat);
        startActivity(myIntent);

    }
}
