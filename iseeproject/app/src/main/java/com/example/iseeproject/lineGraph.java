package com.example.iseeproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class lineGraph extends AppCompatActivity {

    LineChart lineChart;

    ArrayList<String> xAxes = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

    dbHandler peopleDB;
    String usr = ""; // or other values


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        peopleDB = new dbHandler(this);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            usr = b.getString("username");
        }

        User user = peopleDB.getUser(usr);

        lineChart = (LineChart)findViewById(R.id.lineChart);

        /*xAxes.add("Monday");
        xAxes.add("Tuesday");
        xAxes.add("Wednesday");
        xAxes.add("Thursday");
        xAxes.add("Friday");

        String[] xaxes = new String[xAxes.size()];

        for(int i=0;i<xAxes.size();i++)
        {
            xaxes[i] = xAxes.get(i).toString();
        }*/

        //ArrayList<Entry> xyCoord = calculateYaxes(user);
        ArrayList<Entry> yAxes = new ArrayList<>();


        yAxes.add(new Entry(10,0));
        yAxes.add(new Entry(50,1));
        yAxes.add(new Entry(40,2));
        yAxes.add(new Entry(60,3));
        yAxes.add(new Entry(20,4));

        LineDataSet lineDataSet = new LineDataSet(yAxes,"expenses");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet);
        //removes xaxes
        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);


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
}
