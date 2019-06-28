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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class dbHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SmartBucksData";

    // Contacts table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_EXPENSES = "expenses";
    private static String TABLE_CATEGORIES = "categories";

    // User Table Columns names
    private static final String KEY_USN = "username";
    private static final String KEY_PWD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_INCOME = "income";
    private static final String KEY_BUDGET= "budget";
    private static final String KEY_RENT = "rent";
    private static final String KEY_BILLS = "bills";
    private static final String KEY_INSURANCE = "insurance";

    // Expenses Table Columns names
    private static final String KEY_REALTIME = "expense_time";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CAT = "category";
    private static final String KEY_PAYMENT = "payment_method";

    private static final String KEY_THRES = "threshold";


    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USN + " TEXT PRIMARY KEY," + KEY_PWD + " TEXT ," + KEY_NAME + " TEXT,"
                +  KEY_EMAIL + " TEXT," + KEY_INCOME + " REAL, " +  KEY_BUDGET + " REAL," + KEY_RENT + " REAL, " +
                KEY_BILLS + " REAL, " + KEY_INSURANCE + " REAL " + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_REALTIME + " TEXT," + KEY_USN + " TEXT REFERENCES " + TABLE_USER +" (" + KEY_USN + ") , " + KEY_PRICE + " REAL,"
                + KEY_CAT + " TEXT," + KEY_PAYMENT + " TEXT )";
        db.execSQL(CREATE_EXPENSES_TABLE);

        //String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
         //       + KEY_USN + " TEXT PRIMARY KEY, "+ KEY_CAT1 + " REAL," + KEY_CAT2 + " REAL," + KEY_CAT3 + " REAL,"
          //      + KEY_CAT4 + " REAL )";
        //db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        // Creating tables again
        onCreate(db);
    }

    public String md5(final String s) {
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


    public boolean addUser(User usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USN, usr.getUsername());
        String encrypt = md5(usr.getPwd());
        values.put(KEY_PWD, encrypt);
        values.put(KEY_NAME , usr.getName());
        values.put(KEY_EMAIL , usr.getEmail());
        values.put(KEY_INCOME , usr.getIncome());
        values.put(KEY_BUDGET , usr.getBudget());
        values.put(KEY_RENT, usr.getRent());
        values.put(KEY_BILLS , usr.getBills());
        values.put(KEY_INSURANCE , usr.getInsurance());

        long result = db.insert(TABLE_USER,null,values);

        return (!(result == -1));

    }

    public void addExpenses(Expenses exp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //why comment addtime and username?
        values.put(KEY_REALTIME ,exp.getExpenseTime());
        values.put(KEY_USN, exp.getUsername());
        values.put(KEY_PRICE, exp.getPrice());
        values.put(KEY_CAT, exp.getCategory());
        values.put(KEY_PAYMENT,exp.getPaymentMethod());

        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }

    public void addCatThresholds(String username, double Budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        TABLE_CATEGORIES = username + "_categories";
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + KEY_CAT + " TEXT," + KEY_THRES + " REAL )";
        db.execSQL(CREATE_CATEGORIES_TABLE);
        ContentValues values = new ContentValues();
        //then moving on to the addition of the thresholds
        String[] categs = new String[] {"Leisure","Food","Services","Health","Miscellaneous"};
        //default threshold can change later
        double default_thres = Budget / categs.length;
        for ( String cat: categs) {
            values.put(KEY_CAT, cat);
            values.put(KEY_THRES, default_thres);
            db.insert(TABLE_CATEGORIES, null, values);
        }
        db.close(); // Closing database connection
    }

    //method to add a new expenses category
    public void addNewCategory(String username, String newCat, Double threshold) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        TABLE_CATEGORIES = username + "_categories";
        values.put(KEY_CAT, newCat);
        values.put(KEY_THRES, threshold);
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public void updateCategory(String username, String category, Double threshold) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        TABLE_CATEGORIES = username + "_categories";
        values.put(KEY_CAT, category);
        values.put(KEY_THRES, threshold);
        db.update(TABLE_CATEGORIES,values, KEY_CAT + " = ?",
                new String[]{category});
    }

    public Map<String,Double> getThresholds(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Double> thresholds = new HashMap<String, Double>();
        TABLE_CATEGORIES = username + "_categories";
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] {KEY_CAT, KEY_THRES}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                //if for the specific category we have set a value
                thresholds.put(cursor.getString(0), cursor.getDouble(1));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return thresholds;
    }

    public boolean categoryExists(String username ,String cat) {
        SQLiteDatabase db = this.getReadableDatabase();
        TABLE_CATEGORIES = username + "_categories";
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] {KEY_CAT, KEY_THRES}, KEY_CAT + "=?", new String[] {cat}, null, null, null);
        boolean result = (cursor.moveToFirst());
        //will return false if cursor is null
        cursor.close();
        return result;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_USN , KEY_PWD , KEY_NAME
                        , KEY_EMAIL, KEY_INCOME , KEY_BUDGET, KEY_RENT , KEY_BILLS,KEY_INSURANCE}, KEY_USN + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            User user = new User(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), Double.parseDouble(cursor.getString(4)),
                Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)),
                Double.parseDouble(cursor.getString(7)), Double.parseDouble(cursor.getString(8)));

        cursor.close();
        return user;

    }

    public boolean isUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {KEY_USN , KEY_PWD , KEY_NAME
                        ,KEY_EMAIL, KEY_INCOME , KEY_BUDGET, KEY_RENT , KEY_BILLS,KEY_INSURANCE}, KEY_USN + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor!=null && cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }


    public int updateUser(User usr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USN, usr.getUsername());
        values.put(KEY_PWD, usr.getPwd());
        values.put(KEY_NAME , usr.getName());
        values.put(KEY_EMAIL , usr.getEmail());
        values.put(KEY_INCOME , usr.getIncome());
        values.put(KEY_BUDGET , usr.getBudget());
        values.put(KEY_RENT, usr.getRent());
        values.put(KEY_BILLS , usr.getBills());
        values.put(KEY_INSURANCE , usr.getInsurance());

// updating row
        return db.update(TABLE_USER, values, KEY_USN + " = ?",
                new String[]{String.valueOf(usr.getUsername())});
    }

    public boolean expensesExist(User user) {
        //String selectQuery = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + KEY_USN + " = " + user.getUsername();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSES, new String[] {KEY_REALTIME,KEY_USN ,KEY_PRICE ,KEY_CAT ,KEY_PAYMENT}, KEY_USN + "=?",
                new String[] { user.getUsername() }, null, null, null, null);
        //Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor!=null && cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Expenses> getAllExpenses(User user) {
        ArrayList<Expenses> expList = new ArrayList<Expenses>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + KEY_USN + " = " + user.getUsername();
        Cursor cursor = db.query(TABLE_EXPENSES, new String[] {KEY_REALTIME,KEY_USN ,KEY_PRICE ,KEY_CAT,KEY_PAYMENT}, KEY_USN + "=?",
                new String[] { user.getUsername() }, null, null, KEY_REALTIME, null);

        //Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Expenses exp = new Expenses(cursor.getString(0),cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3)), cursor.getString(4),cursor.getString(5));
//                Expenses exp = new Expenses( cursor.getString(0),
//                        cursor.getString(1), Double.parseDouble(cursor.getString(2)),
//                        cursor.getString(3),  cursor.getString(4));
                Expenses exp = new Expenses();
                exp.setExpenseTime(cursor.getString(cursor.getColumnIndex(KEY_REALTIME)));
                exp.setPaymentMethod(cursor.getString(cursor.getColumnIndex(KEY_PAYMENT)));
                exp.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
                exp.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CAT)));
                exp.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USN)));
                //exp.setUsername(cursor.getString(0));
                expList.add(exp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return expList;
    }


    public ArrayList<Expenses> getSortedCategory(User user, String cate){
        ArrayList<Expenses> categoryList = new ArrayList<Expenses>();
        SQLiteDatabase db = this.getWritableDatabase();
        String cateQuery = " SELECT * FROM " + TABLE_EXPENSES + " WHERE " + KEY_USN +
                " = '" + user.getUsername() + "'" +" AND " + KEY_CAT + " = '" + cate + "'"  ;
        Cursor cursor = db.rawQuery(cateQuery,null);

//        while (cursor.moveToNext()){
//            Expenses expenses = new Expenses(cursor.getString(0),
//                    cursor.getString(1), Double.parseDouble(cursor.getString(2)),
//                    cursor.getString(3),  cursor.getString(4));
//            categoryList.add(expenses);
//        }

        if (cursor.moveToFirst()) {
            do {
                // Expenses exp = new Expenses(cursor.getString(0),cursor.getString(1),cursor.getString(2),Double.parseDouble(cursor.getString(3)), cursor.getString(4),cursor.getString(5));
                Expenses expenses = new Expenses();
                expenses.setExpenseTime(cursor.getString(cursor.getColumnIndex(KEY_REALTIME)));
                expenses.setPaymentMethod(cursor.getString(cursor.getColumnIndex(KEY_PAYMENT)));
                expenses.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
                expenses.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CAT)));
                expenses.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USN)));
                //exp.setUsername(cursor.getString(0));
                categoryList.add(expenses);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

}
