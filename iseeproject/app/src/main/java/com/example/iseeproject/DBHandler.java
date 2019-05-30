package com.example.iseeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SmartBucksData";

    // Contacts table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_EXPENSES = "expenses";

    // User Table Columns names
    private static final String KEY_USN = "username";
    private static final String KEY_NAME = "name";
    private static final String KEY_PWD = "password";
    private static final String KEY_INCOME = "income";
    private static final String KEY_RENT = "rent";
    private static final String KEY_BILLS = "bills";
    private static final String KEY_INSURANCE = "insurance";

    // Expenses Table Columns names
    private static final String KEY_PRICE = "price";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_PAYMENT = "payment_method";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //TODO Declare types of categories and payment methods for expenses

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USN + " TEXT PRIMARY KEY," + KEY_PWD + " TEXT ," + KEY_NAME + " TEXT,"
                + KEY_INCOME + " REAL, " +  KEY_RENT + " REAL, " +
                KEY_BILLS + " REAL, " + KEY_INSURANCE + " REAL " + ")";
        db.execSQL(CREATE_USER_TABLE);



        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_USN + " TEXT PRIMARY KEY REFERENCES " + TABLE_USER +" (" + KEY_USN + ") , " + KEY_PRICE + " REAL,"
                + KEY_CATEGORY + " TEXT," + KEY_TIMESTAMP + " DATE, "+ KEY_PAYMENT + " Text )";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        // Creating tables again
        onCreate(db);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //TODO convert-md5-back-to-normal method



    public boolean addUser(User usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USN, usr.getUsername());
        values.put(KEY_PWD, md5(usr.getPwd()));
        values.put(KEY_NAME , usr.getName());
        values.put(KEY_INCOME , usr.getIncome());
        values.put(KEY_RENT, usr.getRent());
        values.put(KEY_BILLS , usr.getBills());
        values.put(KEY_INSURANCE , usr.getInsurance());

        long result = db.insert(TABLE_USER,null,values);
        if (result == -1){
            return false;
        }
        else
        {
            return true;
        }

    }

    public void addExpenses(Expenses exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USN, exp.getUsername());
        values.put(KEY_PRICE, exp.getPrice());
        values.put(KEY_CATEGORY, exp.getCategory());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
        values.put(KEY_TIMESTAMP,df.format(exp.getTime()));

        values.put(KEY_PAYMENT,exp.getPaymentMethod());


        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_USN , KEY_PWD , KEY_NAME
                        , KEY_INCOME , KEY_RENT , KEY_BILLS,KEY_INSURANCE}, KEY_USN + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            User user = new User(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), Double.parseDouble(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)),
                    Double.parseDouble(cursor.getString(6)));


        return user;

    }

    public boolean isUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_USN , KEY_PWD , KEY_NAME
                        , KEY_INCOME , KEY_RENT , KEY_BILLS,KEY_INSURANCE}, KEY_USN + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor != null) {
            return true;
        }
        else{
            return false;
        }


    }



    public List<Expenses> getAllExpenses(User user) throws ParseException {
        List<Expenses> expList = new ArrayList<Expenses>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSES + "WHERE username =" + user.getUsername();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String expdate = cursor.getString(2);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
                Date edate = df.parse(expdate);
                Expenses exp = new Expenses(cursor.getString(0),Double.parseDouble(cursor.getString(1)), edate,cursor.getString(3), cursor.getString(4));
                exp.setUsername(cursor.getString(0));
                //TODO set price and category
// Adding contact to list
                expList.add(exp);
            } while (cursor.moveToNext());
        }
// return contact list
        return expList;
    }



    public int updateUser(User usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USN, usr.getUsername());
        values.put(KEY_PWD, md5(usr.getPwd()));
        values.put(KEY_NAME , usr.getName());
      //  values.put(KEY_SURNAME , usr.getSurname());
        values.put(KEY_INCOME , usr.getIncome());
        values.put(KEY_RENT, usr.getRent());
        values.put(KEY_BILLS , usr.getBills());
        values.put(KEY_INSURANCE , usr.getInsurance());
// updating row
        return db.update(TABLE_USER, values, KEY_USN + " = ?",
                new String[]{String.valueOf(usr.getUsername())});
    }

}
