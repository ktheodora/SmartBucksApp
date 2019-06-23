package com.example.iseeproject;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.itextpdf.text.pdf.parser.Line;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.content.Context;

public class lineGraph {

    //private LineChart lineChart;
    //private ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();


    ArrayList<String> xAxes = new ArrayList<>();

    dbHandler peopleDB ;
    User userr;

    public lineGraph(User user,dbHandler db) {
        peopleDB = db;
        userr = user;
    }

    public void setWeekGraphStyle(LineChart lineChart, ArrayList<ILineDataSet> lineDataSets) {

        ArrayList<Entry> xyCoord = calculateWeekAxes();
        if (!xyCoord.isEmpty()) {
            LineDataSet lineDataSet = new LineDataSet(xyCoord, "expenses per day of current week");
            lineDataSet.setDrawCircles(true);
            lineDataSet.setColor(Color.BLUE);

            lineDataSets.add(lineDataSet);
            //removes xaxes
            lineChart.setData(new LineData(lineDataSets));

            lineChart.setVisibleYRange(0, getMaxY(xyCoord), YAxis.AxisDependency.LEFT);

            //because of 7 days of week
            lineChart.setVisibleXRange(0, 6);

            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            /*Description d = new Description();
            d.setText("Tuesday");
            d.setPosition(1,10);
            d.setTextAlign(Paint.Align.CENTER);
            lineChart.setDescription(d);*/
        }

    }

    public void setMonthGraphStyle(LineChart lineChart, ArrayList<ILineDataSet> lineDataSets) {

        ArrayList<Entry> xyCoord = calculateMonthAxes();
        if (!xyCoord.isEmpty()) {
            LineDataSet lineDataSet = new LineDataSet(xyCoord, "expenses per day of current month");
            lineDataSet.setDrawCircles(true);
            lineDataSet.setColor(Color.RED);

            lineDataSets.add(lineDataSet);
            //removes xaxes
            lineChart.setData(new LineData(lineDataSets));

            lineChart.setVisibleYRange(0, getMaxY(xyCoord), YAxis.AxisDependency.LEFT);

            //because of 4 weeks of month
            lineChart.setVisibleXRange(0, 31);

            //lineChart.setVisibleYRangeMaximum(100F, YAxis.AxisDependency.LEFT);

            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);

        }
    }

    public void setCatGraphStyle(LineChart lineChart, ArrayList<ILineDataSet> lineDataSets) {

        ArrayList<Entry> xyCoord = calculateCatAxes();
        if (!xyCoord.isEmpty()) {
            LineDataSet lineDataSet = new LineDataSet(xyCoord, "expenses per category");
            lineDataSet.setDrawCircles(true);
            lineDataSet.setColor(Color.GREEN);

            lineDataSets.add(lineDataSet);
            //removes xaxes
            lineChart.setData(new LineData(lineDataSets));

            Map<String, Double> thresholds = peopleDB.getThresholds(userr.getUsername());

            //the graph has a maximum y axis range the biggest
            // threshold of the expenses categories
            double maxThres = 0.0;
            for (Map.Entry<String, Double> t : thresholds.entrySet()) {
                if (t.getValue() > maxThres) {
                    maxThres = t.getValue();
                }
            }
            float maxY = (float) maxThres;
            //if the user overcame the budget
            if (getMaxY(xyCoord) > maxY) {
                maxY = getMaxY(xyCoord);
            }
            lineChart.setVisibleYRange(0, maxY, YAxis.AxisDependency.LEFT);

            //because of number of categories
            lineChart.setVisibleXRange(0, thresholds.size());

            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            /*Description d = new Description();
            d.setText("Tuesday");
            d.setPosition(1,10);
            d.setTextAlign(Paint.Align.CENTER);
            lineChart.setDescription(d);*/
        }
    }

    public ArrayList<Entry> calculateWeekAxes() {
        ArrayList<Entry> xyCoord = new ArrayList<>();

        //setting up the calendar dates of this week
        LocalDate now = LocalDate.now();

        TemporalField fieldISO2 = WeekFields.of(Locale.GERMANY).dayOfWeek();
        System.out.println(now.with(fieldISO2, 1));

        //getting the expenses and their dates
        if (peopleDB.expensesExist(userr)) {
            List<Expenses> expenses = peopleDB.getAllExpenses(userr);
            double daysum = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formatter = formatter.withLocale(Locale.GERMANY);
            LocalDate expdate;

            for (int i = 1; i <= 7; i++) {
                //i presents monday for 1, tuesday for 2 etc
                LocalDate d = now.with(fieldISO2, i);//get week date
                for (Expenses exp : expenses) {
                    //search all expenses and add to the one of this day of the week
                    expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
                    if (d.isEqual(expdate)) {
                        daysum += exp.getPrice();
                    }
                }
                //after all expenses of the day are added,
                //add this as a new entry to the LineGraph
                float x = (float) (i - 1);//bc x starts from 1
                float y = (float) daysum;
                xyCoord.add(new Entry(x, y));
                daysum = 0;//make daysum 0 again for the next day
            }
        }
        return xyCoord;
    }

    public ArrayList<Entry> calculateMonthAxes() {
        ArrayList<Entry> xyCoord = new ArrayList<>();

        //setting up the calendar dates of this week
        LocalDate now = LocalDate.now();
        //creating a year month object containing information
        YearMonth yearMonthObject = YearMonth.of(2019, now.getMonth().getValue());

        //getting the expenses and their dates
        if (peopleDB.expensesExist(userr)) {
            List<Expenses> expenses = peopleDB.getAllExpenses(userr);
            double daysum = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formatter = formatter.withLocale(Locale.GERMANY);
            LocalDate expdate, d;

            for (int i = 1; i <= yearMonthObject.lengthOfMonth(); i++) {
                //i presents monday for 1, tuesday for 2 etc
                for (Expenses exp : expenses) {
                    //search all expenses and add to the one of this day of the month
                    d =  yearMonthObject.atDay(i);//get week date
                    d.format(formatter);
                    expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
                    if (d.isEqual(expdate)) {
                        daysum += exp.getPrice();
                    }
                }
                //after all expenses of the day are added,
                //add this as a new entry to the LineGraph
                float x = (float) (i - 1);//bc x starts from 1
                float y = (float) daysum;
                xyCoord.add(new Entry(x, y));
                daysum = 0;//make daysum 0 again for the next day
            }
        }
        return xyCoord;
    }

    public ArrayList<Entry> calculateCatAxes() {
        ArrayList<Entry> xyCoord = new ArrayList<>();

        //getting the expenses and their dates
        if (peopleDB.expensesExist(userr)) {
            Map<String, Double> map = peopleDB.getThresholds(userr.getUsername());
            List<Expenses> expenses = peopleDB.getAllExpenses(userr);
            double catsum = 0;
            int counter = 0;
            for (Map.Entry<String,Double> entry : map.entrySet()) {
                for (Expenses exp : expenses) {
                    //search all expenses and add to the one of this category
                    if (exp.getCategory().contentEquals(new StringBuffer(entry.getKey()))) {
                        catsum += exp.getPrice();
                    }
                }
                //after all expenses of the day are added,
                //add this as a new entry to the LineGraph
                float x = (float) (counter);//bc x starts from 1
                float y = (float) catsum;
                xyCoord.add(new Entry(x, y));
                catsum = 0;//make daysum 0 again for the next day
                counter++;
            }
        }
        return xyCoord;
    }

    //mini function for returning back the maximum y coord of every set of points
    public float getMaxY(ArrayList<Entry> xyCoord) {
        float maxY = 0;
        for ( Entry xy : xyCoord) {
            if (xy.getY() > maxY) {
                maxY =xy.getY();
            }
        }
        return (maxY + 10);
    }
}
