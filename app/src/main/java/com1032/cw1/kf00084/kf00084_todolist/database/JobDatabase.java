package com1032.cw1.kf00084.kf00084_todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kastriot on 04/05/2015.
 */
public class JobDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todotable.db";

    private static final int DATABASE_VERSION = 1;



    public JobDatabase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    // Method is called during creation of the database

    @Override

    public void onCreate(SQLiteDatabase database) {

        JobTable.onCreate(database);

    }



    // Method is called during an upgrade of the database,

    // e.g. if you increase the database version

    @Override

    public void onUpgrade(SQLiteDatabase database, int oldVersion,

                          int newVersion) {

        JobTable.onUpgrade(database, oldVersion, newVersion);

    }

}
