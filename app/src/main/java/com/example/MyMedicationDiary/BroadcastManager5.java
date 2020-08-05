package com.example.MyMedicationDiary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.MyMedicationDiary.ui.home.HomeFragment;

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

public class BroadcastManager5 extends BroadcastReceiver {
    String dataa,data2,user;
    String trial;
    public int ID;
    String IDD;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        trial=intent.getStringExtra("trial");
        dataa=intent.getStringExtra("medname");
        data2=intent.getStringExtra("mednop");
        user=intent.getStringExtra("username");

        new ConnectionssS(context).execute();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context,String text,String text2,String text3){

        int min=0, max=1000000;

        ID = (int)(Math.random() * (max - min + 1) + min);
        IDD=Integer.toString(ID);

        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent notificationIntent = new Intent(context, HomeFragment.class);


        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(notificationDocApp.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent yesReceive = new Intent("RECMED");
        yesReceive.putExtra("username",user);
        yesReceive.putExtra("mednop",data2);
        yesReceive.putExtra("medname",dataa);
        yesReceive.putExtra("ID",IDD);

        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, 1, yesReceive, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent yesReceive2 = new Intent("SNOOZE");
        yesReceive2.putExtra("username",user);
        yesReceive2.putExtra("mednop",data2);
        yesReceive2.putExtra("medname",dataa);
        yesReceive2.putExtra("ID",IDD);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(context, 1, yesReceive2, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Notification notification = builder.setContentTitle("My Medication Diary")
                .setContentText("مرحبا "+text3 +"، هذا التذكير اللاحق لأخذ دوائك "+text)
                .setSound(alarmsound).setSmallIcon(R.drawable.logo2)
                .setContentIntent(intent2)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#5CA490"))
                .addAction(R.drawable.logo2, "تمّ أخذ الدواء", pendingIntentYes)
                .addAction(R.drawable.logo2, "ذكرني لاحقا", pendingIntentYes2)

                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(ID, notification);

    }
    public class ConnectionssS extends AsyncTask<String, String, String> {
        Context cx;

        public ConnectionssS(Context context) {
            cx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String line = null;

            HttpURLConnection urlConnection = null;
            String host = Constants.UU.URL + "checkStatus.php";

            InputStream is = null;
            try {

                URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

                while ((line = reader.readLine()) != null) {
                    result += line;
                }


                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String resultd) {
            if (resultd.equals("online")) {
                new Connectionaval(cx).execute();


            }


        }
    }

    public class Connectionaval extends AsyncTask<String, String, String> {
        Context cx;

        public Connectionaval(Context context) {
            cx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String line = null;

            HttpURLConnection urlConnection = null;
            String host = Constants.UU.URL + "checkava.php";

            InputStream is = null;
            try {

                URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(dataa, "UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

                while ((line = reader.readLine()) != null) {
                    result += line;
                }


                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String resulta) {
            if (resulta.equals("available")) {
                createNotification(cx,dataa,data2,user);


            }


        }
    }

}


