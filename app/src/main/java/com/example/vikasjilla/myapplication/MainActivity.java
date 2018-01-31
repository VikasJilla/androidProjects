package com.example.vikasjilla.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.vikasjilla.myapplication.FCMClasses.NotificationIDService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, NotificationIDService.class);
        startService(intent);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("token is ",""+token);
    }
}
