package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
        private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
            edit =(Button)findViewById(R.id.edbtn);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    routed();
                }
            });

        Bundle b = getIntent().getExtras();
        String usr = 0; // or other values
        int Rent =0;
        int Bills =0;
        int Insurance =0;
        if(b != null)
            usr = b.getText("usr_str");
            Income = b.getInt("inc_str")
            SumExp = b.getInt("sum_str");
            Rent = b.getInt("rent_str");
            Bills = b.getInt("bill_str");
            Insurance = b.getInt("ins_str");

        int TotalExp = SumExp + Rent + Bills + Insurance;
        mText = (TextView)findViewById(R.id.TotalExpenses);
        mText.setText(TotalExp.toString());

        mmText = (TextView)findViewById(R.id.Savings);
        mmText.setText((Income - TotalExp).toString())



    }

    private void routed()
    {
        Intent intent = new Intent(HomePage.this,updateDetail.class);
        startActivity(intent);

    }
}
