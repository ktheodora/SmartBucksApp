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
        }
        TextView mText = (TextView)findViewById(R.id.TotalExpenses);
        int stableexp = Rent + Bills + Insurance;
        mText.setText(String.valueOf(stableexp));

        TextView mmText = (TextView)findViewById(R.id.Savings);
        mmText.setText(String.valueOf(Income - stableexp));

        edit =(Button)findViewById(R.id.edbtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routed();
            }
        });

        enterExpenses = (Button)findViewById(R.id.expButton);

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
}
