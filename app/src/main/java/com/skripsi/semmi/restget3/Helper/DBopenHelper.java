package com.skripsi.semmi.restget3.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by semmi on 22/12/2015.
 */
public class DBopenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "username";

    // define all field in the user table

    public static final String[] allColumns = {USER_ID,USER_NAME};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USER + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT" +
                    ")";

    public DBopenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
        onCreate(db);
    }
}
