package com.example.vikasjilla.mypasswords.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vikas Jilla on 20-05-2017.
 */


public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Passwords";
    public static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppConstants.TABLE_NAME + " (" +
                    AppConstants.COLUMN_SITENAME + " TEXT," +
                    AppConstants.COLUMN_PASSWORD + " TEXT)";
   public DBHelper(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
