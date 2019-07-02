package com.example.iseeproject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.example.iseeproject.homePage.PREFS_NAME;


public class listActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView expenselistview;
    dbHandler dbhandler;
    String usr = "";
    User userr;
    String username;
    SearchView sv;
    String selectedCategory;
    private ImageButton menuBtn;
    menuHandler MenuHandler;
    EditText datepickFrom, datepickTo;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date1,date2;
    ArrayList<Expenses>  cateList;

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
        MenuHandler = new menuHandler(listActivity.this, usr);
        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popup = new PopupMenu(listActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return MenuHandler.onMenuItemClick(item);
                    }
                });
                popup.inflate(R.menu.drawermenu);
                popup.show();
            }
        });

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuHandler.goToHomePage();
            }

        });

        dbhandler = new dbHandler(this);

        expenselistview = findViewById(R.id.expenseLV);


        Set<String> cats1 = dbhandler.getThresholds(usr).keySet();
        //we add all of the categories but first the option to choose to view every expense
        final List<String> categories1= new ArrayList<String>();
        categories1.add("All");
        categories1.addAll(cats1);

        spinner12 = (Spinner)findViewById(R.id.spinner123);
        ArrayAdapter<String> datAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categories1);
        datAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner12.setAdapter(datAdapter1);
//        spinner12.setSelected(false);
//        spinner12.setSelection(0,true);
        spinner12.setOnItemSelectedListener(this);

//we set the calendar view in the ui
        myCalendar = Calendar.getInstance();

        datepickFrom = (EditText) findViewById(R.id.selectDate1);
        datepickTo = (EditText) findViewById(R.id.selectDate2);
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(datepickFrom);
            }

        };

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(datepickTo);
            }

        };

        datepickFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // TODO Auto-generated method stub
                 DatePickerDialog mDatePicker = new DatePickerDialog(listActivity.this, date1, myCalendar
                         .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                 mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                 mDatePicker.show();
            }
        }

        );

        datepickTo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  // TODO Auto-generated method stub
                   DatePickerDialog mDatePicker = new DatePickerDialog(listActivity.this, date2, myCalendar
                     .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                   mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                   mDatePicker.show();
              }
        }

        );

        Button go = (Button) findViewById(R.id.goBtn);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategory = spinner12.getSelectedItem().toString();
                dbhandler = new dbHandler(getApplicationContext());
                if (selectedCategory.equals("All")) {
                    cateList = dbhandler.getAllExpenses(userr);
                }
                else {
                    cateList = dbhandler.getSortedCategory(userr, selectedCategory);
                }
                //now we check the date and filter the list
                String dateFrom = datepickFrom.getText().toString();
                String dateTo = datepickTo.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                LocalDate dFrom = null , dTo = null;
                String strDateRegEx = "\\d{2}-\\d{2}-\\d{4}";
                //we check if user has selected a date from the calendar
                if(dateFrom.matches(strDateRegEx)) {
                   dFrom = LocalDate.parse(dateFrom, formatter);
                }
                if(dateTo.matches(strDateRegEx)) {
                    dTo = LocalDate.parse(dateTo, formatter);
                }
                for(Expenses exp : cateList) {
                    LocalDate expDate = LocalDate.parse(exp.getExpenseTime(), formatter);
                    //if it is before the earliest date the user chooses
                    if (dFrom != null && (expDate.isBefore(dFrom)) ) {
                        cateList.remove(exp);
                    }
                    //if it is after the latest day the user chooses
                    if (dTo != null && (expDate.isAfter(dTo))) {
                        cateList.remove(exp);
                    }
                    //if it is inside the date boundaries, keep it in the list
                }
                //if in the end none of the expenses fit the selected dates or categories
                if (cateList.isEmpty()) {
                    MenuHandler.showToast("No results for the selected criteria");
                }
                //if it's not empty will show results orelse delete current expenses results
                filteredExpenses(cateList);
            }
        });

        expenselistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showDeleteDialog(position);
                //MenuHandler.showToast("You clicked an expense of position" + String.valueOf(position));
                return true;
            }
        });


    }

    private void updateLabel(EditText datepick) {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        datepick.setText(sdf.format(myCalendar.getTime()));
    }

    //Not working :( Solution: Changed date format to MM-dd-yyyy so that's sorted from the db
    public ArrayList<Expenses> sortedExpenses(ArrayList<Expenses> explist) {
        ArrayList<Expenses> sorted = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate oldestDate = LocalDate.now();
        Expenses oldestExp = explist.get(0);
        //O(n^2) comparison complexity
        for (Iterator<Expenses> it = explist.iterator(); it.hasNext(); ) {
            for(Expenses exp : explist) {
                LocalDate expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
                //if it is earlier than the current month
                if (expdate.isBefore(oldestDate)) {
                    oldestDate =expdate;
                    oldestExp = exp;
                }
            }
            sorted.add(oldestExp);
            explist.remove(oldestExp);
        }
        //now append the ones who are equal to today(most recent ones)(if they exist)
        if (!explist.isEmpty()) {
            sorted.addAll(explist);
        }
        return sorted;
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

    public void showDeleteDialog(final int pos) {
        AlertDialog deleteAlert = new AlertDialog.Builder(this)
                .setTitle("Remove Expense")
                .setMessage("Are you sure you want to delete this expense?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        //choosing from the current category list
                        Expenses exp = cateList.get(pos);
                        if(dbhandler.removeExpense(exp)) {
                            MenuHandler.showToast("Succesful removal of expense");
                        }
                        else {
                            MenuHandler.showToast("Database issue, unable to remove expense");
                        }
                        Intent myIntent = new Intent(listActivity.this, listActivity.class);
                        Bundle b = new Bundle();
                        b.putString("username",usr);
                        myIntent.putExtras(b); //Put your id to your next Intent
                        startActivity(myIntent);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();

    }
}
