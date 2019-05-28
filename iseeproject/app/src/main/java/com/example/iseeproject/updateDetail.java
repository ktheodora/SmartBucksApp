package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            if (!TextUtils.isEmpty(IncomeView.getText())) {
                Income = Integer.parseInt(IncomeView.getText().toString());
            }

            EditText RentView  = (EditText)findViewById(R.id.entRent);
            if (!TextUtils.isEmpty(RentView.getText())) {
                Rent = Integer.parseInt(RentView.getText().toString());
            }

            EditText BillsView   = (EditText)findViewById(R.id.entBills);
            if (!TextUtils.isEmpty(BillsView.getText())){
                Bills = Integer.parseInt(BillsView.getText().toString());
            }

            EditText InsuranceView   = (EditText)findViewById(R.id.entIns);
            if (!TextUtils.isEmpty(InsuranceView.getText())) {
                Insurance = Integer.parseInt(InsuranceView.getText().toString());
            }

            int extra_inc = 0;
            EditText ExtraIncView   = (EditText)findViewById(R.id.entExtraInc);
            if (!TextUtils.isEmpty(ExtraIncView.getText())) {
                extra_inc = Integer.parseInt(ExtraIncView.getText().toString());
            }

            if ((Income + extra_inc) <= Rent + Bills + Insurance ) {
                Toast t = Toast.makeText(updateDetail.this,
                        "Total income cannot be equal or less to stable expenses", Toast.LENGTH_LONG);
                t.show();
            }
            else {
                // Start NewActivity.class
                Intent myIntent = new Intent(updateDetail.this,
                        HomePage.class);
                Bundle b = new Bundle();
                b.putInt("income", (Income + extra_inc));
                b.putInt("rent", Rent);
                b.putInt("bills", Bills);
                b.putInt("ins", Insurance);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
                finish();
                startActivity(myIntent);
            }

        }
}

