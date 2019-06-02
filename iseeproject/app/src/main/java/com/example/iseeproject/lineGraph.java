package com.example.iseeproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class lineGraph extends AppCompatActivity {

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        //the program is just for an example as there wasn't any real data available.
        ArrayList<String> xAxes = new ArrayList<>();
        ArrayList<Entry> yAxessin = new ArrayList<>();
        ArrayList<Entry> yAxescos = new ArrayList<>();
        double x=0;
        int numDataPoints = 1000;
        for (int i=0; i<numDataPoints;i++)
        {
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAxessin.add(new Entry(sinFunction,i));
            yAxescos.add(new Entry(cosFunction,i));
            xAxes.add(i, String.valueOf(x));

        }
        String[] xaxes = new String[xAxes.size()];
        for(int i=0; i<xAxes.size();i++) {
            xaxes[i]  = xAxes.get(i).toString();
        }
        ArrayList < LineDataSet > lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAxescos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAxescos, "sin");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(xaxes,lineDataSets));
        lineChart.setVisibleXRangeMaximum(65f);



    }
}
