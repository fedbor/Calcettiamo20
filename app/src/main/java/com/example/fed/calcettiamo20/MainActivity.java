package com.example.fed.calcettiamo20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;


public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    private SoccerDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inizializza il db
        dbHelper = new SoccerDBHelper(this);
        db = dbHelper.getWritableDatabase();
    }



    //metodi associati ai buttons
    public void launchAddActivity(View v){
        Intent launchAdd = new Intent(this,AddActivity.class);
        startActivity(launchAdd);
    }

    public void launchSelectActivity(View v){
        Intent launchSelect = new Intent(this,SelectActivity.class);
        startActivity(launchSelect);
    }

    public void launchSearchActivity(View v){
        Intent launchSearch = new Intent(this,SearchActivity.class);
        startActivity(launchSearch);
    }
}
