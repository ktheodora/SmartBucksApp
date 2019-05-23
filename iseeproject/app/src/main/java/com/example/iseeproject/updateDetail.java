package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class updateDetail extends AppCompatActivity {
        private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        update=(Button)findViewById(R.id.updtbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updated();
            }
        });
    }

        private void updated()
        {
<<<<<<< HEAD
            UserName   = (EditText)findViewById(R.id.entUsName);
            String usr_str = UserName.getText().toString();

            Password  = (EditText)findViewById(R.id.entPsWord);
            String pwd_str = UserName.getText().toString();

            Income   = (EditText)findViewById(R.id.entInc);
            String inc_str = UserName.getText().toString();

            SumExp   = (EditText)findViewById(R.id.sumExp);
            String sum_str = UserName.getText().toString();

            Rent  = (EditText)findViewById(R.id.entRent);
            String rent_str = UserName.getText().toString();

            Bills   = (EditText)findViewById(R.id.entBills);
            String bill_str = UserName.getText().toString();

            Insurance   = (EditText)findViewById(R.id.entIns);
            String ins_str = UserName.getText().toString();

=======
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

>>>>>>> 508499a... final basic prototype working version - small adjustments made

            // Start NewActivity.class
            Intent myIntent = new Intent(updateDetail.this,
                    HomePage.class);
            Bundle b = new Bundle();
<<<<<<< HEAD
            b.putInt("usr_str", usr_str); //Your id
            b.putInt("pwd_str", pwd_str); //Your id
            b.putInt("inc_str", inc_str); //Your id
            b.putInt("sum_str", sum_str); //Your id
            b.putInt("rent_str", rent_str); //Your id
            b.putInt("bills_str", bills_str); //Your id
            b.putInt("ins_str", ins_str); //Your id
=======
            b.putInt("income", (Income + extra_inc));
            b.putInt("rent", Rent);
            b.putInt("bills", Bills);
            b.putInt("ins", Insurance);
>>>>>>> 508499a... final basic prototype working version - small adjustments made
            myIntent.putExtras(b); //Put your id to your next Intent
            startActivity(myIntent);
            finish();
            startActivity(myIntent);

        }
}

