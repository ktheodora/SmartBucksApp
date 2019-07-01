package com.example.iseeproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class menuHandler {
    String usr;
    Context ctx;
    static String USERPREF = "USER"; // or other values
    SharedPreferences sharedpreferences;
    dbHandler peopleDB;
    public menuHandler() {}
    public menuHandler(Context Ctx, String username) {
        usr = username;
        ctx = Ctx;
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.HomePage:
                goToHomePage();
                return true;

            case  R.id.item2:
                goToDetails();
                return true;

            case  R.id.logoutBtn:
                logout();
                return true;

            case  R.id.item12:
                goToFAQ();
                return true;
            case R.id.report:
                Permission();
                return true;
            default:
                return false;
        }
    }

    public void goToHomePage() {
        Intent myIntent = new Intent(ctx, homePage.class);
        Bundle b = new Bundle();
        b.putString("username",usr);

        myIntent.putExtras(b); //Put your id to your next Intent
        ctx.startActivity(myIntent);
    }

    public void goToDetails() {
        Intent myIntent = new Intent(ctx, updateDetail.class);
        Bundle b = new Bundle();
        b.putString("username",usr);

        myIntent.putExtras(b); //Put your id to your next Intent
        ctx.startActivity(myIntent);
    }

    public void goToFAQ() {
        Intent myIntent = new Intent(ctx, faqActivity.class);
        ctx.startActivity(myIntent);
    }

    public void showToast(String text) {
        Toast t = Toast.makeText(ctx,text,Toast.LENGTH_SHORT);
        t.show();
    }

    public void logout() {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        //then redirect to initial activity
        Intent myIntent = new Intent(ctx, mainActivity.class);
        ctx.startActivity(myIntent);
    }

    private void smartBucksReport(ArrayList<Expenses> expensesList) {

        if(expensesList.isEmpty()) {
            showToast("No expenses entered for this month. " +
                    "Smartbucks monthly report cannot be generated." +
                    "\nTo view all your expenses, click the Show Expenses button in the Homepage.");
        }
        else {
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
                peopleDB = new dbHandler(ctx);
                User userr = peopleDB.getUser(usr);
                expensesList = peopleDB.getAllExpenses(userr);
                myPdfDocument.addAuthor("SmartBucks App");
                Paragraph p3 = new Paragraph();
                p3.add("SmartBucks Monthly Report\n");
                p3.setExtraParagraphSpace(1);
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
                showToast(myFilename + ".pdf\nis saved to\n" + myFilePath);

            } catch (Exception e) {

                //if anything goes wrong ,get and show up exception
                showToast(e.getMessage());
            }
        }
    }

    //we need to sort by date the expenses and
    //show only the ones of the last month
    public ArrayList<Expenses> sortMonthExpenses(String username) {
        peopleDB = new dbHandler(ctx);
        User user = peopleDB.getUser(username);
        ArrayList<Expenses> allExpenses = new ArrayList<>();
        if (peopleDB.expensesExist(user)) {
            allExpenses = peopleDB.getAllExpenses(user);
            //setting up the calendar dates of this week
            LocalDate now = LocalDate.now();
            //creating a year month object containing information
            YearMonth yearMonthObject = YearMonth.of(2019, now.getMonth().getValue());
            LocalDate firstMonthDate = yearMonthObject.atDay(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            for(Iterator<Expenses> it = allExpenses.iterator(); it.hasNext();) {
                Expenses exp = it.next();
                LocalDate expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
                //if it is earlier than the current month
                if (expdate.isBefore(firstMonthDate) || !expdate.isEqual(firstMonthDate)) {
                    it.remove();
                }
            }
            //afterwards, sort by comparing with every day of the month
            //slow implementation but trustworthy
            /*LocalDate d;
            for (int i = 1; i <= yearMonthObject.lengthOfMonth(); i++) {
                //i presents monday for 1, tuesday for 2 etc
                for (Expenses exp : allExpenses) {
                    //search all expenses and add to the one of this day of the month
                    d = yearMonthObject.atDay(i);//get week date
                    d.format(formatter);
                    LocalDate expdate = LocalDate.parse(exp.getExpenseTime(), formatter);
                    if (d.isEqual(expdate)) {
                        monthlyExpenses.add(exp);
                    }
                }
            }*/
            //we don't need to sort like this because we sort from database
        }
        return allExpenses;

    }

    private void Permission() {
        int STORAGE_CODE = 1000;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //System OS >= Marshmellow (6.0), check if permission enabled
            if (ctx.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                //Permission not granted request it now
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions((Activity)ctx,permission, STORAGE_CODE);

            } else {
                //permission already granted
                smartBucksReport(sortMonthExpenses(usr));
            }

        }
    }
}