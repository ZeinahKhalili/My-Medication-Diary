package com.example.MyMedicationDiary;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class LastPillBroadCanc extends BroadcastReceiver {
    String medname,mednop,username;
    public int ID;
    public String I;
    @Override
    public void onReceive(Context context, Intent intent) {
        I=intent.getStringExtra("ID");
        ID=Integer.parseInt(I);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID);

        medname=intent.getStringExtra("medname");
        mednop=intent.getStringExtra("mednop");
        username=intent.getStringExtra("username");

        new ConnectionEND().execute();


    }
    @SuppressLint("StaticFieldLeak")
    public class ConnectionEND extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "meddelete.php?username=" + username+"&medname="+medname;

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }
}
