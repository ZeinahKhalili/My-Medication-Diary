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
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class BroadcastManager2 extends BroadcastReceiver {
    String dd;
    public int ID;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        dd=intent.getStringExtra("name");
        createNotification(context,dd);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context,String t){

            Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            Intent notificationIntent = new Intent(context, camactivity.class);


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

        Notification notification = builder.setContentTitle("My Medication Diary")
                    .setContentText("يوجد لديك تعارض مع "+t)
                    .setSound(alarmsound).setSmallIcon(R.drawable.logo2)
                    .setContentIntent(intent2)
                    .setAutoCancel(true)
                .setColor(Color.parseColor("#5CA490"))

                    .setChannelId("my_channel_01")
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel);
            }
        int min=0, max=1000000;

        ID = (int)(Math.random() * (max - min + 1) + min);

        notificationManager.notify(ID, notification);

    }
}


