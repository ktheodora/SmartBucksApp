package com.example.iseeproject;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class homePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpbtn;
    dbHandler peopleDB;
    String usr = "";
    static String USERPREF = "USER"; // or other values
    SharedPreferences sharedpreferences;
    LineChart lineChart;
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setSessionPreferences();

        peopleDB = new dbHandler(this);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            usr = b.getString("username");
        }

        User userr = peopleDB.getUser(usr);

        //Set values of Text Views in homePage

        TextView homeView = (TextView) findViewById(R.id.HomePage);
        String welcometext = "Welcome, " + userr.getName();
        homeView.setText(welcometext);

        TextView budgetView = (TextView) findViewById(R.id.budgetview);
        budgetView.setText(String.valueOf(userr.getBudget()));

        TextView incomeView = (TextView) findViewById(R.id.totalInc);
        incomeView.setText(String.valueOf(userr.getIncome()));

        TextView expensesView = (TextView) findViewById(R.id.TotalExpenses);
        //get sum of money spent in expenses
        double sum=0;
        if (peopleDB.expensesExist(userr)) {
            //if user has entered at least one expense
            List<Expenses> exp = peopleDB.getAllExpenses(userr);
            for (Expenses expense : exp) {
                sum+=expense.getPrice();
            }
        }
        expensesView.setText(String.valueOf(sum));

        TextView savingsView = (TextView) findViewById(R.id.SavingsView);
        //savings = budget - sum of expenses
        double savings = userr.getBudget() - sum ;
        savingsView.setText(String.valueOf(savings));

        //add expenses button setup

        enterExpbtn =(Button)findViewById(R.id.addExpenses);
        enterExpbtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //Intent myIntent = new Intent(homePage.this, enterExpenses.class);
                //starting line graph activitiy here for testing reasons
                Intent myIntent = new Intent(homePage.this, enterExpenses.class);
                Bundle b = new Bundle();
                b.putString("username",usr);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });

        lineChart = (LineChart)findViewById(R.id.lineChart);

        //we normally use
        //ArrayList<Entry> xyCoord = calculateYaxes(user);
        //but for testing purposes we are going to use hardcoded values
        ArrayList<Entry> yAxes = new ArrayList<>();

        yAxes.add(new Entry(10,0));
        yAxes.add(new Entry(50,1));
        yAxes.add(new Entry(40,2));
        yAxes.add(new Entry(60,3));
        yAxes.add(new Entry(20,4));

        LineDataSet lineDataSet = new LineDataSet(yAxes,"expenses per day");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet);
        //removes xaxes
        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);

        lineChart.setVisibleYRangeMaximum(8F, YAxis.AxisDependency.LEFT);

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        Description d = new Description();
        d.setText("Monday");
        d.setPosition(10,0);
        d.setTextAlign(Paint.Align.CENTER);
        lineChart.setDescription(d);

    }

    public void setSessionPreferences() {
        sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("username", usr);

        editor.apply();
    }

    //@Override







   @Override
    public boolean  onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu,menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        //  preparation code here
        return super.onPrepareOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(final MenuItem item){
        switch (item.getItemId()){

            case R.id.HomePage:
               startActivity(new Intent(this,homePage.class));
                return true;

            case R.id.Preferences:
                startActivity(new Intent(this,updateDetail.class));
                return true;

            case  R.id.item2:
                startActivity(new Intent(this,updateDetail.class));
                return true;

            case  R.id.logoutBtn:
                startActivity(new Intent(this,loginActivity.class));
                return true;

            case  R.id.item1:
                Toast.makeText(this,"We wil help you shortly",Toast.LENGTH_SHORT);
                return true;


                default:
                    return super.onOptionsItemSelected(item);
        }
    }







    public void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public ArrayList<Entry> calculateYaxes(User user) {
        ArrayList<Entry> xyCoord = new ArrayList<>();

        //setting up the calendar dates of this week
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.GERMANY).dayOfWeek();
        System.out.println(now.with(fieldISO, 1));

        //getting the expenses and their dates
        List<Expenses> expenses = peopleDB.getAllExpenses(user);
        double daysum=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        formatter = formatter.withLocale(Locale.GERMANY);
        LocalDate expdate;

        for (int i = 1; i <= 7; i++ )
        {
            //i presents monday for 1, tuesday for 2 etc
            LocalDate d = now.with(fieldISO, i);//get week date
            for (Expenses exp : expenses) {
                //search all expenses and add to the one of this day of the week
                expdate = LocalDate.parse( exp.getExpenseTime(), formatter);
                if (d.isEqual(expdate)) {
                    daysum += exp.getPrice();
                }
            }
            //after all expenses of the day are added,
            //add this as a new entry to the LineGraph
            float x = (float)daysum;
            float y = (float)(i-1);//because y axes start from 0
            xyCoord.add(new Entry(x,y));
            daysum = 0;//make daysum 0 again for the next day
        }
        return xyCoord;
    }


    //TODO functions for stats and graphs of homepage
    //public expProg

    public void calculate() {
        //TODO Seperate function for calculating extra expenses

        /*List<Expenses> exp = peopleDB.getAllExpenses(user);
        double foodcount, leiscount, medcount, billscount;
        double totalexpamount;
        for each(expense :exp){
        extra_exp += expense.getPrice();

        if (expense.getCategory() == "Food") {
            foodcount += expense.getPrice();
        } else if (expense.getCategory() == "Leisure") {
            leiscount += expense.getPrice();
        }
        totalexpamount += expense.getPrice();
    }

        double food perc = (foodcount / totalexpamount) * 100;
        ProgressView pg (ProgressView)findViewById(R.id.ProgViewFood);
        pg.set
            */
    }

}
