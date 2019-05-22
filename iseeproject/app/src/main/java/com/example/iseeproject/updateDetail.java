package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateDetail extends AppCompatActivity {
        private Button update;
    int Income = 0;
    int Rent =0;
    int Bills =0;
    int Insurance =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            Income = b.getInt("income");
            Rent = b.getInt("rent");
            Bills = b.getInt("bills");
            Insurance = b.getInt("ins");
        }

        update=(Button)findViewById(R.id.updtbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updated(Income, Rent, Bills, Insurance);
            }
        });
    }

        private void updated(int Income,  int Rent, int Bills, int Insurance)
        {
            EditText IncomeView   = (EditText)findViewById(R.id.entInc);
            //Only if value of view changes we update it, orelse we pass the initial again
            if (IncomeView.getText().toString() != "") {
                Income = Integer.parseInt(IncomeView.getText().toString());
            }

            EditText RentView  = (EditText)findViewById(R.id.entRent);
            if (RentView.getText().toString() != "") {
                Rent = Integer.parseInt(RentView.getText().toString());
            }

            EditText BillsView   = (EditText)findViewById(R.id.entBills);
            if (BillsView.getText().toString() != "") {
                Bills = Integer.parseInt(BillsView.getText().toString());
            }

            EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
            if (InsuranceView.getText().toString() != "") {
                Insurance = Integer.parseInt(InsuranceView.getText().toString());
            }

            int stableexp = Rent + Bills + Insurance ;

            // Start NewActivity.class
            Intent myIntent = new Intent(updateDetail.this,
                    HomePage.class);
            Bundle b = new Bundle();
            b.putInt("income", Income);
            b.putInt("rent", Rent);
            b.putInt("bills", Bills);
            b.putInt("stableexp", stableexp);
            b.putInt("ins", Insurance);
            myIntent.putExtras(b); //Put your id to your next Intent
            startActivity(myIntent);
            finish();
            startActivity(myIntent);

        }
}

