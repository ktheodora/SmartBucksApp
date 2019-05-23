package com.example.iseeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PWD = "password";
    private static final String KEY_INCOME = "income";
    private static final String KEY_RENT = "rent";
    private static final String KEY_BILLS = "bills";
    private static final String KEY_INSURANCE = "insurance";

    // Expenses Table Columns names
    private static final String KEY_PRICE = "price";
    private static final String KEY_CATEGORY = "category";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USN + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT, " + KEY_PWD + " TEXT ," + KEY_INCOME + " REAL, " +  KEY_RENT + " REAL, " +
                KEY_BILLS + " REAL, " + KEY_INSURANCE + " REAL " + ")";
        db.execSQL(CREATE_USER_TABLE);
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_USN + " TEXT PRIMARY KEY," + KEY_PRICE + " REAL,"
                + KEY_CATEGORY + " TEXT" + ")";
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

}
