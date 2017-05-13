package com.example.vikasjilla.databaselearn;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vikasjilla.databaselearn.HelperClasses.AppConstants;
import com.example.vikasjilla.databaselearn.HelperClasses.DBHelper;

public class DisplayAllDetails extends AppCompatActivity {
    String[] projection = {
            AppConstants.COLUMNANAME_RANDOMNUMBER,
            AppConstants.COLUMNNAME_NAME,
            AppConstants.COLUMNNAME_PASSWORD
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_details);
        displayAllAccounts();
    }
    private void displayAllAccounts()
    {
//        // Filter results WHERE "title" = 'My Title'
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = { "My Title" };
//
//// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(
                AppConstants.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        cursor.moveToFirst();
        String result = "";
        while(!cursor.isAfterLast()){
            Log.e("in display details","result is "+result);
                int randomNumber = cursor.getInt(cursor.getColumnIndex(AppConstants.COLUMNANAME_RANDOMNUMBER));
                String userName = cursor.getString(cursor.getColumnIndex(AppConstants.COLUMNNAME_NAME));
                String password = cursor.getString(cursor.getColumnIndex(AppConstants.COLUMNNAME_PASSWORD));
            result = result + randomNumber+"  "+userName+" "+password+"\n";
            cursor.moveToNext();
        }
        ((TextView)findViewById(R.id.displayDetailsLabel)).setText(result);
        database.close();
    }
}
