package com.example.administrator.demo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyContentProvider extends ContentProvider {

    private MyDBHelper myDBHelper;
    private Context context;
    private static final String AUTHORITY = "com.example.administrator.demo";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri BOOK_URI = BASE_CONTENT_URI.buildUpon().appendPath(MyDBHelper.BOOK_TABLE_NAME).build();
    public static final Uri USER_URI = BASE_CONTENT_URI.buildUpon().appendPath(MyDBHelper.USER_TABLE_NAME).build();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    static {
        uriMatcher.addURI(AUTHORITY, MyDBHelper.BOOK_TABLE_NAME, BOOK_URI_CODE);
        uriMatcher.addURI(AUTHORITY, MyDBHelper.USER_TABLE_NAME, USER_URI_CODE);
    }

    protected static Uri buildBookUri(long id) {
        return ContentUris.withAppendedId(BOOK_URI, id);
    }

    protected static Uri buildUserUri(long id) {
        return ContentUris.withAppendedId(USER_URI, id);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        myDBHelper = new MyDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = myDBHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                cursor = sqLiteDatabase.query(MyDBHelper.BOOK_TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            case USER_URI_CODE:
                cursor = sqLiteDatabase.query(MyDBHelper.USER_TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = myDBHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                sqLiteDatabase.insert(MyDBHelper.BOOK_TABLE_NAME, null, contentValues);
                break;
            case USER_URI_CODE:
                 sqLiteDatabase.insert(MyDBHelper.USER_TABLE_NAME, null, contentValues);
                break;
            default:
                throw new SQLException("Unknown uri: " + uri);
        }
        context.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
