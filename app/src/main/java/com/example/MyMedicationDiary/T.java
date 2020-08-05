package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class T extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent t=getIntent();
        String tipText=t.getStringExtra("tipText");
        String broughtBy=t.getStringExtra("broughtBy");
        Toast.makeText(getApplicationContext(),tipText,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),broughtBy,Toast.LENGTH_LONG).show();


        tipsD dialogFragment = new tipsD();
        Bundle bundle = new Bundle();
        bundle.putString("tipText",tipText);
        bundle.putString("broughtBy",broughtBy);

        dialogFragment.setArguments(bundle);

        dialogFragment.show(getSupportFragmentManager(),"dialog");


    }

}
