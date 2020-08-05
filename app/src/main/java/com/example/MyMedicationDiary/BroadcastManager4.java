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


public class BroadcastManager4 extends BroadcastReceiver {
    String medname,medtype,mednop,username;
    public  Notification notification;
    public   NotificationManager notificationManager;
    public int ID;
    String IDD;
    public static final String YES_ACTION = "YES_ACTION";
    int mn;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        medname=intent.getStringExtra("medname");
        medtype=intent.getStringExtra("medtype");
        mednop=intent.getStringExtra("mednop");
        mn=Integer.parseInt(mednop);

        username=intent.getStringExtra("user");
        new ConnectionS(context).execute();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context,String t,String t2,String t3,String t4){

       int min=0, max=1000000;

        ID = (int)(Math.random() * (max - min + 1) + min);
        IDD=Integer.toString(ID);

        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent notificationIntent = new Intent(context, Home.class);



        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(notificationDocApp.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Intent yesReceive = new Intent("RECMED");
        yesReceive.putExtra("username",username);
        yesReceive.putExtra("mednop",mednop);
        yesReceive.putExtra("medname",medname);
        yesReceive.putExtra("ID",IDD);

        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, 1, yesReceive, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent yesReceive2 = new Intent("SNOOZE");
        yesReceive2.putExtra("username",username);
        yesReceive2.putExtra("mednop",mednop);
        yesReceive2.putExtra("medname",medname);
        yesReceive2.putExtra("ID",IDD);

        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(context, 1, yesReceive2, PendingIntent.FLAG_CANCEL_CURRENT);



        Notification notification = builder.setContentTitle("My Medication Diary")
                .setContentText("الان موعد دوائك "+t)
                .setSound(alarmsound).setSmallIcon(R.drawable.logo2)
                .setContentIntent(intent2)
                .setAutoCancel(true)
                .setChannelId("my_channel_01")
                .setColor(Color.parseColor("#5CA490"))
                .setColorized(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .addAction(R.drawable.logo2, "تمّ أخذ الدواء", pendingIntentYes)
                .addAction(R.drawable.logo2, "ذكرني لاحقا", pendingIntentYes2)

                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(ID, notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void createNotification2(Context context,String t,String t2,String t3,String t4){

        int min=0, max=1000000;

        ID = (int)(Math.random() * (max - min + 1) + min);
        IDD=Integer.toString(ID);

        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent notificationIntent = new Intent(context, Home.class);


        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(notificationDocApp.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
        }
        Intent intentLast = new Intent("LAST");
        intentLast.putExtra("username",username);
        intentLast.putExtra("mednop",mednop);
        intentLast.putExtra("medname",medname);
        intentLast.putExtra("ID",IDD);

        PendingIntent pendingintentlast = PendingIntent.getBroadcast(context, 1, intentLast, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intentLast2 = new Intent("CANCELLAST");
        intentLast2.putExtra("username",username);
        intentLast2.putExtra("mednop",mednop);
        intentLast2.putExtra("medname",medname);
        intentLast2.putExtra("ID",IDD);

        PendingIntent pendingintentlast2 = PendingIntent.getBroadcast(context, 1, intentLast2, PendingIntent.FLAG_CANCEL_CURRENT);


        notification = builder.setContentTitle("My Medication Diary")
                .setContentText("بعد اخذك لهذا الدواء الان، ستكون العلبة فارغة، هل تريد اعادة التعبئة؟")
                .setSound(alarmsound).setSmallIcon(R.drawable.logo2)
                .setContentIntent(intent2)
                .setAutoCancel(true)
                .setChannelId("my_channel_01")
                .setColor(Color.parseColor("#5CA490"))
                .setColorized(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .addAction(R.drawable.logo2,"أعد التعبئة",pendingintentlast)
                .addAction(R.drawable.logo2,"انهاء",pendingintentlast2)

                .build();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }



        notificationManager.notify(ID, notification);

    }

    public class ConnectionS extends AsyncTask<String, String, String> {
        Context cx;
        public ConnectionS(Context context) {
            cx=context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result="";
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
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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
            if(resultd.equals("online")){
                new Connectiona(cx).execute();

            }


        }

    }

    public class Connectiona extends AsyncTask<String, String, String> {
        Context cx;
        public Connectiona(Context context) {
            cx=context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result="";
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
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(medname, "UTF-8");

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
            if(resulta.equals("available")) {
                if (mn == 1) {

                    createNotification2(cx, medname, medtype, mednop, username);

                } else {
                    createNotification(cx, medname, medtype, mednop, username);

                }
            }


        }

    }
}


