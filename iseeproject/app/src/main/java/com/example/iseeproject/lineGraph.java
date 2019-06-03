package com.example.iseeproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class lineGraph extends AppCompatActivity {

    LineChart lineChart;

    ArrayList<String> xAxes = new ArrayList<>();
    ArrayList<Entry> yAxes = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        lineChart = (LineChart)findViewById(R.id.lineChart);

        xAxes.add("Monday");
        xAxes.add("Tuesday");
        xAxes.add("Wednesday");
        xAxes.add("Thursday");
        xAxes.add("Friday");

        yAxes.add(new Entry(10,0));
        yAxes.add(new Entry(50,1));
        yAxes.add(new Entry(40,2));
        yAxes.add(new Entry(60,3));
        yAxes.add(new Entry(20,4));

        String[] xaxes = new String[xAxes.size()];

        for(int i=0;i<xAxes.size();i++)
        {
            xaxes[i] = xAxes.get(i).toString();
        }

        LineDataSet lineDataSet = new LineDataSet(yAxes,"values");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet);
        //removes xaxes
        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);



    }
}
