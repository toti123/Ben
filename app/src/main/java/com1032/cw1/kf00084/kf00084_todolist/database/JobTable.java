package com1032.cw1.kf00084.kf00084_todolist.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Kastriot on 04/05/2015.
 */
public class JobTable {

    // Database table
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TASK
            + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(JobTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }

}
