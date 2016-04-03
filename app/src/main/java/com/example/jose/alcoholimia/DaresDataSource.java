package com.example.jose.alcoholimia;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Jose on 3/28/2016.
 */

public class DaresDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_DARE, MySQLiteHelper.COLUMN_DRINKS};

    public DaresDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

//    public Dare createDare(String comment) {
//        ContentValues values = new ContentValues();
//
//        values.put(MySQLiteHelper.COLUMN_DARE, comment);
//        long insertId = database.insert(MySQLiteHelper.TABLE_DARES, null,
//                values);
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_DARES,
//                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Dare newDare = cursorToDare(cursor);
//        cursor.close();
//        return newDare;
//    }
//
//    public void deleteDare(Dare comment) {
//        long id = comment.getId();
//        System.out.println("Comment deleted with id: " + id);
//        database.delete(MySQLiteHelper.TABLE_DARES, MySQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//    }

    public ArrayList<Dare> getAllDares() {
        ArrayList<Dare> dares = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DARES, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Dare dare = cursorToDare(cursor);
            dares.add(dare);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return dares;
    }

    public ArrayList<String> getEasyDares() throws Exception {

        ArrayList<String> dares = null;
        Cursor cursor = database.rawQuery("SELECT DARE FROM DARES WHERE DRINKS = 1", null);

        if (cursor.moveToFirst()) {
            dares = new ArrayList<>();

            do {
                if (!cursor.isNull(0))
                    dares.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return dares;
    }

    private Dare cursorToDare(Cursor cursor) {
        Dare dare = new Dare();
        dare.setId(cursor.getLong(0));
        dare.setDare(cursor.getString(1));
        dare.setDrinks(cursor.getInt(2));
        return dare;
    }
}

