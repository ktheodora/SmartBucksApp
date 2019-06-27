package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class listActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView expenselistview;
    dbHandler dbhandler;
    String usr = "";
    User userr;
    String username;
    SearchView sv;
    String selectedCategory;

    Spinner spinner12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbhandler = new dbHandler(this);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            usr = b.getString("username");
        }

            userr = dbhandler.getUser(usr);



        expenselistview = findViewById(R.id.expenseLV);
        dbhandler = new dbHandler(this);
        ArrayList<Expenses>  explist = dbhandler.getAllExpenses(userr);
        filteredExpenses(explist);



//        ArrayList<HashMap<String,String>> myMapList = new ArrayList<>();
//
//
//
//
//
//
//        //sv=(SearchView) findViewById(R.id.search_view);
//
//
//
//
//        for(int i=0; i<explist.size();i++){
//            HashMap<String,String> myMap = new HashMap<>();
//            myMap.put("Date",explist.get(i).getExpenseTime());
//            myMap.put("Amount",Double.toString(explist.get(i).getPrice()));
//            myMap.put("Category",explist.get(i).getCategory());
//            myMap.put("Payment_Method",explist.get(i).getPaymentMethod());
//
//            myMapList.add(myMap);
//        }
//
//        final ListAdapter adapter = new SimpleAdapter(listActivity.this,myMapList,R.layout.row,
//                new String[]{"Date","Amount","Category","Payment_Method"},
//                new int[]{R.id.textviewdate,R.id.textviewamount,R.id.textviewcategory,R.id.textviewpaymenttype});
//        expenselistview.setAdapter(adapter);


     /*   sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ((SimpleAdapter) adapter).getFilter().filter(newText);
                 return false;
            }
        });*/



        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(listActivity.this, homePage.class);
                Bundle b = new Bundle();
                b.putString("username",usr);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }





        });

        dbhandler = new dbHandler(this);
        Set<String> cats1 = dbhandler.getThresholds(usr).keySet();
        //we add all of the categories but first the option to choose to view every expense
        List<String> categories1= new ArrayList<String>();
        categories1.add("All");
        categories1.addAll(cats1);

        spinner12 = (Spinner)findViewById(R.id.spinner123);
        ArrayAdapter<String> datAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categories1);
        datAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner12.setAdapter(datAdapter1);
//        spinner12.setSelected(false);
//        spinner12.setSelection(0,true);
        spinner12.setOnItemSelectedListener(this);

        spinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                dbhandler = new dbHandler(getApplicationContext());
                ArrayList<Expenses>  cateList;
                if (selectedCategory.equals("All")) {
                    cateList = dbhandler.getAllExpenses(userr);
                }
                else {
                    cateList = dbhandler.getSortedCategory(userr, selectedCategory);
                }
                filteredExpenses(cateList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void filteredExpenses(final ArrayList<Expenses> explist){

        ArrayList<HashMap<String,String>> myMapList = new ArrayList<>();

        for(int i=0; i<explist.size();i++){
            HashMap<String,String> myMap = new HashMap<>();
            myMap.put("Date",explist.get(i).getExpenseTime());
            myMap.put("Amount",Double.toString(explist.get(i).getPrice()));
            myMap.put("Category",explist.get(i).getCategory());
            myMap.put("Payment_Method",explist.get(i).getPaymentMethod());

            myMapList.add(myMap);
        }

        final ListAdapter adapter = new SimpleAdapter(listActivity.this,myMapList,R.layout.row,
                new String[]{"Date","Amount","Category","Payment_Method"},
                new int[]{R.id.textviewdate,R.id.textviewamount,R.id.textviewcategory,R.id.textviewpaymenttype});
        expenselistview.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner123) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast t = Toast.makeText(listActivity.this,
                "Select one Category", Toast.LENGTH_LONG);
        t.show();
    }
}
