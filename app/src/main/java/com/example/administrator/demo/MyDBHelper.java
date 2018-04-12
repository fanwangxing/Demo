package com.example.administrator.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fan.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String BOOK_NAME = "bookname";
    private static final String BOOK_NUM = "booknum";
    private static final String USER_NAME = "username";
    private static final String USER_NUM = "usernum";
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String booktable = "CREATE TABLE " + BOOK_TABLE_NAME + "( "+ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+BOOK_NAME+" TEXT NOT NULL, "+ BOOK_NUM +" INTEGER );";
        String usertable = "CREATE TABLE " + USER_TABLE_NAME + "( "+ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_NAME+" TEXT NOT NULL, "+ USER_NUM +" INTEGER );";
        sqLiteDatabase.execSQL(booktable);
        sqLiteDatabase.execSQL(usertable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ BOOK_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
