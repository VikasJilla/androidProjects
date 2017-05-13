package com.example.vikasjilla.databaselearn;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vikasjilla.databaselearn.HelperClasses.AppConstants;
import com.example.vikasjilla.databaselearn.HelperClasses.DBHelper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showList(View v){
        //show the list of accounts and their passwords
        Intent in = new Intent(this,DisplayAllDetails.class);
        startActivity(in);
    }

    public void sign_in(View v){
        //show his Id and Password
    }

    public void sign_up(View v){
        Log.e(LOG_TAG,"in sign up");
        //save the values to db
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        EditText userNameField = (EditText)findViewById(R.id.fieldName);
        EditText passwordField = (EditText)findViewById(R.id.fieldPassword);
        if(userNameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()){
            Log.e(LOG_TAG,"returning as fields are empty");
            db.close();
            return;
        }
        int randomInt = 1;
        ContentValues cv = new ContentValues();
        cv.put(AppConstants.COLUMNANAME_RANDOMNUMBER,randomInt);
        cv.put(AppConstants.COLUMNNAME_NAME,userNameField.getText().toString());
        cv.put(AppConstants.COLUMNNAME_PASSWORD,passwordField.getText().toString());
        db.insert(AppConstants.TABLE_NAME,null,cv);
        db.close();
        ((TextView)findViewById(R.id.statusLabel)).setText("Successfully saved, see the list for details");
        Log.e(LOG_TAG,"successfullyInserted");
    }
}
