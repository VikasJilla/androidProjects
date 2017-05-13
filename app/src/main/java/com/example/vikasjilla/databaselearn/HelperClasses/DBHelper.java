package com.example.vikasjilla.databaselearn.HelperClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vikas Jilla on 13-05-2017.
 */


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Employee.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppConstants.TABLE_NAME + " (" +
                    AppConstants.COLUMNNAME_NAME + " TEXT," +
                    AppConstants.COLUMNANAME_RANDOMNUMBER + " INTEGER," +
                    AppConstants.COLUMNNAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppConstants.TABLE_NAME;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("DBHelper","in onCreate");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }
}
