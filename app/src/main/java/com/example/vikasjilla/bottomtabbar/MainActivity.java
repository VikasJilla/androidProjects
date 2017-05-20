package com.example.vikasjilla.bottomtabbar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView drawerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setDividerHeight(2);
        drawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,getResources().getStringArray(R.array.drawerList)));
    }
}
