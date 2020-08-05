package com.example.MyMedicationDiary;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;

public class notmed2 extends BroadcastReceiver {
    public long timee;
    public int ID;
    public String I;
    public String user, medname, mednum;
    @Override
    public void onReceive(final Context context, Intent intent) {
                I=intent.getStringExtra("ID");
                ID=Integer.parseInt(I);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID);

        user=intent.getStringExtra("username");

        medname=intent.getStringExtra("medname");
        mednum=intent.getStringExtra("mednop");

        snooze(context,user,medname,mednum);

    }

    public void snooze(Context context,String s1,String s2,String s3){
        Intent itAlarm = new Intent("SNOOZEALARM");

        timee= System.currentTimeMillis()+ 300000;
        itAlarm.putExtra("username",s1);
        itAlarm.putExtra("mednop",s3);
        itAlarm.putExtra("medname",s2);
        itAlarm.putExtra("trial","hi");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,11 , itAlarm, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarme = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Toast.makeText(context, s1, Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assert alarme != null;
            alarme.setExact(AlarmManager.RTC_WAKEUP, timee , pendingIntent);

        }

    }
}
