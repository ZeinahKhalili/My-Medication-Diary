package com.example.MyMedicationDiary;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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


public class notmed extends BroadcastReceiver implements meddialnop.meddialnopL{
    public int ID;
    public String I;
String medname,mednop,username;
    String URL=Constants.UU.URL;

int mednop2;
String mednop3;
boolean over=false;
    @Override
    public void onReceive(final Context context, Intent intent) {
        I=intent.getStringExtra("ID");
        ID=Integer.parseInt(I);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID);

        medname=intent.getStringExtra("medname");
        mednop=intent.getStringExtra("mednop");
        username=intent.getStringExtra("username");
        mednop2=Integer.parseInt(mednop);
        mednop2=mednop2-1;
        if(mednop2<=5){
            over=true;
        }
        mednop3=Integer.toString(mednop2);
        Toast.makeText(context, medname, Toast.LENGTH_SHORT).show();

        new Connection().execute();



        }



    @Override
    public void onPositiveClicked() {

    }

    @Override
    public void onNegativeClicked() {

    }


    public class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL  + "editnop.php";
            try{
            java.net.URL url = new URL(host);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                    + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(medname, "UTF-8")
                + "&&" + URLEncoder.encode("mednop", "UTF-8") + "=" + URLEncoder.encode(mednop3, "UTF-8");

                writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }



            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (
        MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {

        }
    }
}
