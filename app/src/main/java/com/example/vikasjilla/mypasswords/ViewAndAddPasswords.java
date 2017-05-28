package com.example.vikasjilla.mypasswords;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.vikasjilla.mypasswords.Helper.AppConstants;
import com.example.vikasjilla.mypasswords.Helper.DBHelper;

import java.util.ArrayList;

public class ViewAndAddPasswords extends AppCompatActivity {

    private static final String LOG_TAG = "ViewAndAddPasswords";
    ArrayList<String> accntList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_add_passwords);
        Spinner accntListSpinner = (Spinner) findViewById(R.id.accountsSpinner);
        accntListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(LOG_TAG,"itemSelected at index "+i+" value is "+accntList.get(i));
                getPassword(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        invalidateUI();
    }
    public void invalidateUI(){
        DBHelper dbHelper = new DBHelper(this);
        accntList.clear();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AppConstants.TABLE_NAME,new String[]{AppConstants.COLUMN_SITENAME},null,null,null,null,null);
        Spinner accntListSpinner = (Spinner) findViewById(R.id.accountsSpinner);//.setVisibility(View.GONE);
        if(cursor.getCount() == 0){
            TextView passwordState = (TextView) findViewById(R.id.passwordsState);
            passwordState.setText("No accounts are added!Click 'Add' to add new account.");
            passwordState.setTextColor(Color.RED);
            accntListSpinner.setVisibility(View.GONE);
        }
        else{
            accntListSpinner.setVisibility(View.VISIBLE);
            Log.e(LOG_TAG,"we have list");
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String accntName = cursor.getString(0);
                Log.e(LOG_TAG,"accntName "+accntName);
                accntList.add(accntName);
                cursor.moveToNext();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,accntList);
            accntListSpinner.setAdapter(adapter);
        }
        database.close();
    }
    public void showAlert(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View customAlertView = getLayoutInflater().inflate(R.layout.account_save_layout,null);
        final EditText accountField = (EditText) customAlertView.findViewById(R.id.accountNameField);
        final EditText passwordField = (EditText) customAlertView.findViewById(R.id.passwordAlertField);
        customAlertView.findViewById(R.id.saveAlertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOG_TAG,"in dismiss save alert.");
                saveAccount(accountField.getText().toString(),passwordField.getText().toString());
                dialog.hide();
            }
        });
        customAlertView.findViewById(R.id.cancelAlertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOG_TAG,"in dismiss cancel alert.");
                dialog.hide();
            }
        });
        dialog.setView(customAlertView);
        dialog.show();
    }
    void saveAccount(String accntName,String password){
        if(accntName.isEmpty() || password.isEmpty()){
            Log.e(LOG_TAG,"fields shouldnot be empty");
            return;
        }
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConstants.COLUMN_SITENAME,accntName);
        contentValues.put(AppConstants.COLUMN_PASSWORD,password);
        database.insert(AppConstants.TABLE_NAME,null,contentValues);
        database.close();
        invalidateUI();
    }
    public void getPassword(int index){
        if(accntList.size() < index)return;
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String selection =  AppConstants.COLUMN_SITENAME + "=?";
        Cursor cursor = database.query(AppConstants.TABLE_NAME,new String[]{AppConstants.COLUMN_PASSWORD},selection,new String[]{accntList.get(index)},null,null,null);
        cursor.moveToFirst();
        String password = cursor.getString(0);
        Log.e(LOG_TAG,"password is "+password);
        database.close();
        TextView textView = (TextView) findViewById(R.id.passwordLabel);
        textView.setText(String.format("Password for %s account is %s.",accntList.get(index),password));
    }
}
