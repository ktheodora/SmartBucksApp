package com.example.iseeproject;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class homePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpbtn, weekbtn, catbtn, monbtn;
    private ImageButton menuBtn;
    dbHandler peopleDB;
    String usr = "";
    User userr;
    static String USERPREF = "USER"; // or other values
    SharedPreferences sharedpreferences;
    LineChart lineChart1,lineChart2,lineChart3;
    ArrayList<ILineDataSet> lineDataSets1 = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets2 = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets3 = new ArrayList<>();
    lineGraph lg;
    PieChartView pieChartView;
    int choice;


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

        userr = peopleDB.getUser(usr);

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

            ArrayList<Expenses> exp = peopleDB.getAllExpenses(userr);
            for (Expenses expense : exp) {
                sum+=expense.getPrice();

            }
        }
        else{
            expensesView.setText(String.valueOf(sum));
        }

        if(sum>userr.getBudget()){

           // final TextView budget = (TextView) findViewById(R.id.budgetview);
            expensesView.setText(String.valueOf(sum));
            final Animation anim = new AlphaAnimation(0.0f,1.0f);
            anim.setDuration(5);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);

            budgetView.startAnimation(anim);


            AlertDialog.Builder bx1 = new AlertDialog.Builder(homePage.this);
            bx1.setCancelable(true);
            bx1.setTitle("Alert!Spendings getting more than Budget");

            bx1.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    anim.cancel();

                }
            });

            AlertDialog alertDialog = bx1.create();
            alertDialog.show();

//            Toast t = Toast.makeText(homePage.this,
//                    "Be careful! You are overcoming threshold for" , Toast.LENGTH_LONG);
//            t.show();

        }


        TextView savingsView = (TextView) findViewById(R.id.SavingsView);
        //savings = budget - sum of expenses
        double savings = userr.getBudget() - sum ;


        savingsView.setText(String.valueOf(savings));

        //add expenses button setup
        Button showExp = (Button) findViewById(R.id.showExpenses);
        showExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homePage.this, listActivity.class);
                Bundle b = new Bundle();
               b.putString("username",usr);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });

        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popup = new PopupMenu(homePage.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.HomePage:
                                goToHomepage();
                                return true;

                            case R.id.Preferences:
                                showToast("Preferences under construction");
                                return true;

                            case  R.id.item2:
                                goToDetails();
                                return true;

                            case  R.id.logoutBtn:
                                logout();
                                return true;

                            case  R.id.item12:
                                showToast("FAQ under construction");
                                return true;
                            case R.id.report:
                                //smartBucksReport();
                                Permission();

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.drawermenu);
                popup.show();
            }
        });

        enterExpbtn =(Button)findViewById(R.id.addExpenses);
        enterExpbtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goToExpenses();
            }
        });

        lineChart1 = (LineChart)findViewById(R.id.lineChartWeek);
        lineChart2 = (LineChart)findViewById(R.id.lineChartMon);
        pieChartView = findViewById(R.id.chart);

        lg = new lineGraph(userr,peopleDB);
        lg.setWeekGraphStyle(lineChart1,lineDataSets2);
        lg.setMonthGraphStyle(lineChart2,lineDataSets3);
        lg.setCatGraphStyle(pieChartView);

    }

    private void smartBucksReport() {

        Document myPdfDocument = new Document();
        //pdf filename
        String myFilename = "SmartBucks" + new SimpleDateFormat("ddMMYYYY",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf path
        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + myFilename + ".pdf";

        try {
            //Create instance of PdfWriter class and open pdf
            PdfWriter.getInstance(myPdfDocument, new FileOutputStream(myFilePath));
            myPdfDocument.open();
            //get transactions from databaase
            peopleDB = new dbHandler(this);

            ArrayList<Expenses> expensesList = peopleDB.getAllExpenses(userr);
            myPdfDocument.addAuthor("Pawan Kumar");
            Paragraph p3 = new Paragraph();
            p3.add("SmartBucks Report\n");
            try {
                myPdfDocument.add(p3);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            PdfPTable table = new PdfPTable(4);

            table.addCell("Date");
            table.addCell("Amount");
            table.addCell("Category");
            table.addCell("Payment_Method");


            for (int i = 0; i < expensesList.size(); i++) {

                table.addCell(expensesList.get(i).getExpenseTime());
                table.addCell(Double.toString(expensesList.get(i).getPrice()));
                table.addCell(expensesList.get(i).getCategory());
                table.addCell(expensesList.get(i).getPaymentMethod());

            }

            try {
                myPdfDocument.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            myPdfDocument.addCreationDate();
            myPdfDocument.close();
            Toast.makeText(this, myFilename + ".pdf\nis saved to\n" + myFilePath, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            //if anything goes wrong ,get and show up exception
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void Permission() {
        int STORAGE_CODE = 1000;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //System OS >= Marshmellow (6.0), check if permission enabled
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                //Permission not granted request it now
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, STORAGE_CODE);

            } else {
                //permission already granted
                smartBucksReport();

            }

        }
    }

    //methods for redirecting

    public void goToHomepage() {
        Intent myIntent = new Intent(homePage.this, homePage.class);
        Bundle b = new Bundle();
        b.putString("username",usr);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }

    public void goToDetails() {
        Intent myIntent = new Intent(homePage.this, updateDetail.class);
        Bundle b = new Bundle();
        b.putString("username",usr);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }


    public void showToast(String text) {
        Toast t = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        t.show();
    }

    public void logout() {
        SharedPreferences sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        //then redirect to initial activity
        Intent myIntent = new Intent(homePage.this, mainActivity.class);
        startActivity(myIntent);
    }

    public void goToExpenses() {
        Intent myIntent = new Intent(homePage.this, enterExpenses.class);
        Bundle b = new Bundle();
        b.putString("username",usr);

        myIntent.putExtras(b); //Put your id to your next Intent
        startActivity(myIntent);
    }


    public void setSessionPreferences() {
        sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("username", usr);

        editor.apply();
    }



}
