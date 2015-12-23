package com.skripsi.semmi.restget3.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.skripsi.semmi.restget3.Helper.DBopenHelper;

/**
 * Created by semmi on 22/12/2015.
 */
public class UserProvider extends ContentProvider {

    private static final String AUTHORITY = "com.skripsi.semmi.restget3";
    private static final String BASE_PATH = "user";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int USER = 1;
    private static final int USER_ID = 2;


    private SQLiteDatabase database;


    private static final UriMatcher uriMATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        // yang pertama kalau buat kaya select all
        uriMATCHER.addURI(AUTHORITY,BASE_PATH,USER);

        // yang kedua misal ada pake wildcard
        // kaya select * where id = 1
        uriMATCHER.addURI(AUTHORITY,BASE_PATH + "/#",USER_ID);
    }


    @Override
    public boolean onCreate() {

        DBopenHelper helper = new DBopenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(DBopenHelper.TABLE_USER,DBopenHelper.allColumns,selection,null,null,null,DBopenHelper.USER_ID);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Long id = database.insert(DBopenHelper.TABLE_USER,null,values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBopenHelper.TABLE_USER,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBopenHelper.TABLE_USER,values,selection,selectionArgs);
    }
}
