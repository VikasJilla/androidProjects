package com.example.vikasjilla.mypasswords;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vikasjilla.mypasswords.Helper.AppConstants;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onResume(){
        super.onResume();
        invalidateUI();
    }
    public void invalidateUI(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!preferences.getString(AppConstants.USER_NAME,"").isEmpty() && !preferences.getString(AppConstants.PASSWORD,"").isEmpty()){
            findViewById(R.id.userNameField).setVisibility(View.GONE);
            findViewById(R.id.confirmPasswordField).setVisibility(View.GONE);
//            findViewById(R.id.statusLabel).setVisibility(View.GONE);
            Button actionButton = ((Button)findViewById(R.id.actionButton));
            actionButton.setText("Sign in");
            actionButton.setTextColor(Color.parseColor("#22E00E"));
            actionButton.setTag(2);
        }
    }
    public void save(View v){
        Log.e(LOG_TAG,"tag value is "+v.getTag());
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        String passwordString = passwordField.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(Integer.parseInt(v.getTag().toString()) ==  2){
            Log.e(LOG_TAG,"tag value inside is "+v.getTag());
            if(passwordString.equals(preferences.getString(AppConstants.PASSWORD,"")))
            {
                startActivity(new Intent(this,ViewAndAddPasswords.class));
            }
            return;
        }
        EditText userNameField = (EditText) findViewById(R.id.userNameField);
        EditText confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);
        TextView statusLabel = (TextView) findViewById(R.id.statusLabel);
        String username = userNameField.getText().toString();
        String confirmPasswordString = confirmPasswordField.getText().toString();
        String message = "";
        if(username.isEmpty() || passwordString.isEmpty() || confirmPasswordString.isEmpty()){
            message = "The Fields can\'t be empty.";
        }else if(!confirmPasswordString.equals(passwordString)){
            message = "Password's didn't match.";
        }
        statusLabel.setText(message);
        if(!message.isEmpty()){
            statusLabel.setTextColor(Color.RED);
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstants.USER_NAME,username);
        editor.putString(AppConstants.PASSWORD,passwordString);
        editor.commit();
        invalidateUI();
        statusLabel.setText("saved!");
    }
}
