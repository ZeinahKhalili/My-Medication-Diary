package com.example.MyMedicationDiary;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class BroadcastManagertips extends BroadcastReceiver {
    String result;
    String tipText,broughtBy;
    int counter=0;
    public String count;
    //Context con;
    @Override
    public void onReceive(Context context, Intent intent) {
       Random R = new Random();


        int min=0, max=10;

        counter= min + (max - min) * R.nextInt();

         count=Integer.toString(counter);
/*
        Intent i = new Intent(context, T.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
*/

      //  new Connection(context,intent).execute();
    }




}
