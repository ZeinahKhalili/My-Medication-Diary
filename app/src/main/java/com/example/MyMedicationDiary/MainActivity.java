package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;


public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 1000;

        public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        getSupportActionBar().hide();

         username=SaveSharedPreference.getUserName(getApplicationContext());

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent  = new Intent(MainActivity.this,SecondActivity.class);

                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent  = new Intent(MainActivity.this,Home.class);
                    intent.putExtra("username",username);
                    intent.putExtra("activity","main");
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIMEOUT);

        }
    }



}