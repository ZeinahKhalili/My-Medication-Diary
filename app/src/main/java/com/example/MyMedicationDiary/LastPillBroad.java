package com.example.MyMedicationDiary;

import android.annotation.SuppressLint;
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

public class LastPillBroad extends BroadcastReceiver {
    public int ID;
    public String I;
    String medname,mednop,username;
    String s;
    @Override
    public void onReceive(Context context, Intent intent) {
        I=intent.getStringExtra("ID");
        ID=Integer.parseInt(I);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID);

        medname=intent.getStringExtra("medname");
        mednop=intent.getStringExtra("mednop");
        username=intent.getStringExtra("username");
        Toast.makeText(context,username,Toast.LENGTH_SHORT).show();

        new ConnectionLast().execute();

    }

    @SuppressLint("StaticFieldLeak")
    public class ConnectionLast extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL  + "fillpills.php";
            try{
                java.net.URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(medname, "UTF-8");

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
