package com.example.jose.alcoholimia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jose on 3/28/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DARES = "DARES";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_DARE = "DARE";
    public static final String COLUMN_DRINKS = "DRINKS";

    private static final String DATABASE_NAME = "dares.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_DARES + "( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DARE + " TEXT NOT NULL, " +
                    COLUMN_DRINKS + " INTEGER NOT NULL " +
                    ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("INSERT INTO " + TABLE_DARES + " VALUES (1, 'EASY1', 1);");
        database.execSQL("INSERT INTO " + TABLE_DARES + " VALUES (2, 'EASY2', 1);");
        database.execSQL("INSERT INTO " + TABLE_DARES + " VALUES (3, 'EASY3', 1);");
        database.execSQL("INSERT INTO " + TABLE_DARES + " VALUES (4, 'EASY4', 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DARES);
        onCreate(db);
    }

}
