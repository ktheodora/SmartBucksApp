package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class listActivity extends AppCompatActivity {

    ListView expenselistview;
    dbHandler dbhandler;


    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        expenselistview = findViewById(R.id.expenseLV);
        dbhandler = new dbHandler(this);
        ArrayList<Expenses>  explist = dbhandler.getExpenses();

        ArrayList<HashMap<String,String>> myMapList = new ArrayList<>();
        for(int i=0; i<explist.size();i++){
            HashMap<String,String> myMap = new HashMap<>();
            myMap.put("Date",explist.get(i).getExpenseTime());
            myMap.put("Amount",Double.toString(explist.get(i).getPrice()));
            myMap.put("Category",explist.get(i).getCategory());
            myMap.put("Payment_Method",explist.get(i).getPaymentMethod());

            myMapList.add(myMap);
        }

        ListAdapter adapter = new SimpleAdapter(listActivity.this,myMapList,R.layout.row,
                new String[]{"Date","Amount","Category","Payment_Method"},
                new int[]{R.id.textviewdate,R.id.textviewamount,R.id.textviewcategory,R.id.textviewpaymenttype});
        expenselistview.setAdapter(adapter);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            username = b.getString("username");
        }

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(listActivity.this, homePage.class);
                Bundle b = new Bundle();
                b.putString("username",username);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }



        });
    }
}
