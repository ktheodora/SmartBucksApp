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
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class homePage extends AppCompatActivity {
      //  private Button edit;
    private Button enterExpbtn, weekbtn, catbtn, monbtn,allexpense;
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
    menuHandler MenuHandler;


    public static final String PREFS_NAME =
            "Test";
    public CheckBox check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setSessionPreferences();

        peopleDB = new dbHandler(this);
        Bundle b = getIntent().getExtras();


        if (b != null) {
            usr = b.getString("username");
            if (b.containsKey("createAccount")) {
                //Show dialog box with app rules
                AlertDialog.Builder bx1 = new AlertDialog.Builder(homePage.this);
                bx1.setTitle("Welcome to the SmartBucks App!");
                bx1.setMessage("\n-To enter new expense/income source" +
                        ", click on the 'Add Expenses/Income' buttons located on the homepage." +
                        "\n-To update your details/expenses categories," +
                        " navigate to Menu -> Update Details.");
                bx1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                AlertDialog alertDialog = bx1.create();
                alertDialog.show();
            }
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

        TextView stableExpenses =(TextView) findViewById(R.id.StableExpenses);
        Double stableSum = userr.getBills() + userr.getRent() + userr.getInsurance();
        stableExpenses.setText(String.valueOf(stableSum));

        TextView expensesView = (TextView) findViewById(R.id.TotalExpenses);

        //get sum of money spent in expenses this month
        double sum=0;
        if (peopleDB.expensesExist(userr)) {
        LocalDate now = LocalDate.now();
        //creating a year month object containing information
        YearMonth yearMonthObject = YearMonth.of(2019, now.getMonth().getValue());
        LocalDate firstMonthDate = yearMonthObject.atDay(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        ArrayList<Expenses> allExpenses = peopleDB.getAllExpenses(userr);
        for (Expenses exp : allExpenses) {
            LocalDate expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
            //if it is in the current month
            if (expdate.isAfter(firstMonthDate) || expdate.isEqual(firstMonthDate)) {
                sum += exp.getPrice();
            }
        }
            //if user has entered at least one expense
        }
        expensesView.setText(String.valueOf(sum));

        if(sum>userr.getBudget()){

            showDialog();

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

        Button addIncome = (Button) findViewById(R.id.addIncome);
        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homePage.this, addIncome.class);
                Bundle b = new Bundle();
                b.putString("username",usr);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });

        MenuHandler = new menuHandler(homePage.this, usr);

        menuBtn  = (ImageButton) findViewById(R.id.menuLines);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PopupMenu popup = new PopupMenu(homePage.this, v);
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

        enterExpbtn =(Button)findViewById(R.id.addExpenses);
        enterExpbtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent myIntent = new Intent(homePage.this, enterExpenses.class);
                Bundle b = new Bundle();
                b.putString("username",usr);
                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
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



    @SuppressWarnings("deprecation")
    private void showDialog(){
        AlertDialog.Builder adb= new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.dialog_content, null);
        check = (CheckBox) eulaLayout.findViewById(R.id.check_box1);
        adb.setView(eulaLayout);
        adb.setTitle("Alert");
        adb.setMessage(Html.fromHtml("Alert!Spendings getting more than Budget "));
        adb.setPositiveButton("Ok", new

                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String checkBoxResult = "NOT checked";
                        if (check.isChecked())

                            checkBoxResult = "checked";
                        SharedPreferences settings =

                                getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor

                                editor = settings.edit();
                        editor.putString("noshow", checkBoxResult);
                        // Commit the edits!

                        //  sunnovalthesis();

                        editor.commit();
                        return;
                    } });

        adb.setNegativeButton("Cancel", new

                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface

                                                dialog, int which) {
                        String checkBoxResult = "NOT checked";
                        if (check.isChecked())

                            checkBoxResult = "checked";
                        SharedPreferences settings =

                                getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor =

                                settings.edit();
                        editor.putString("noshow",

                                checkBoxResult);
                        // Commit the edits!

                        //  sunnovalthesis();

                        editor.commit();
                        return;
                    } });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String noshow = settings.getString("noshow", "NOT checked");
        if (noshow != "checked" ) adb.show();

    }


    public void setSessionPreferences() {
        sharedpreferences = getSharedPreferences(USERPREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("username", usr);

        editor.apply();
    }

}
