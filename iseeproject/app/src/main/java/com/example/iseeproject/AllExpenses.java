package com.example.iseeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AllExpenses extends AppCompatActivity {
    ListView showallexpenselistview;
    dbHandler peopledb;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);
        showallexpenselistview = findViewById(R.id.list);


        Bundle b = getIntent().getExtras();
        if (b != null)
            username = b.getString("username");


        peopledb = new dbHandler(this);

        User userr = peopledb.getUser(username);

        peopledb = new dbHandler(this);
        ArrayList<Expenses> explist = peopledb.getAllExpenses(userr);

        ArrayList<HashMap<String,String>> myMapList = new ArrayList<>();


        for(int i=0; i<explist.size();i++){
            HashMap<String,String> myMap = new HashMap<>();
            myMap.put("Date",explist.get(i).getExpenseTime());
            myMap.put("Amount",Double.toString(explist.get(i).getPrice()));
            myMap.put("Category",explist.get(i).getCategory());
            myMap.put("Payment_Method",explist.get(i).getPaymentMethod());

            myMapList.add(myMap);
        }

        final ListAdapter adapter = new SimpleAdapter(AllExpenses.this,myMapList,R.layout.row,
                new String[]{"Date","Amount","Category","Payment_Method"},
                new int[]{R.id.textviewdate,R.id.textviewamount,R.id.textviewcategory,R.id.textviewpaymenttype});
        showallexpenselistview.setAdapter(adapter);
    }
}
