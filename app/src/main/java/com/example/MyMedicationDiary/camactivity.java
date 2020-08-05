package com.example.MyMedicationDiary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class camactivity extends AppCompatActivity  implements interD.interDd, notfoundD.notfoundDd{


    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    public String barcode;
    Button entering;
    BarcodeDetector barcodeDetector;
    public String [] meds;
    public String [] res;
    public     String nam;
    public  String typ;
    public   String nop;
    public  String fam;
    public String username;
    public  String fam2;
    public String medname;
    public String username2;
    String URL= Constants.UU.URL;
    public  ArrayList<String> arrayList;
    public  ArrayList<String> arrayList2;
    public String h;
    TextView ttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barscan);
        getSupportActionBar().hide();

        Intent t=getIntent();
        username= t.getStringExtra("username");
        surfaceView =(SurfaceView)findViewById(R.id.camerapreview);
        textView=(TextView)findViewById(R.id.textview);
        entering=(Button)findViewById(R.id.btnnext);
        entering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });
        barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(640,480).setAutoFocusEnabled(true).build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                            Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //  vibrator.vibrate(1000);
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            textView.setText(qrCodes.valueAt(0).displayValue); // here is the value of the qr code
                        }
                    });
                }
            }
        });


    }
    private void goTo(){

        barcode=textView.getText().toString();

        new Connection1().execute();
    }
    private void goTo2(){
        new Connection2().execute();

    }


    @Override
    public void onNoClicked() {

    }

    @Override
    public void onInterClicked() {
        Intent intent_m=new Intent(camactivity.this,manualAdd.class);
        barcode=textView.getText().toString();
        intent_m.putExtra("username",username);
        intent_m.putExtra("barcode",barcode);

        startActivity(intent_m);
    }

    @Override
    public void onCancelClicked() {

    }

    public class Connection1 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            String line = null;

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {

                URL url = new URL(URL + "newmed.php?barcode=" + barcode);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                is = urlConnection.getInputStream();

            } catch (Exception e) {

                Log.e("Fail 1", e.toString());

            }


            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                result = sb.toString();

            } catch (Exception e) {

                Log.e("Fail 2", e.toString());

            }


            try {
                JSONArray JA = new JSONArray(result);

                JSONObject json = null;

                meds = new String[JA.length()];

                for (int i = 0; i < JA.length(); i++) {


                    json = JA.getJSONObject(i);

                    nam = json.getString("name");
                    typ = json.getString("type");
                    nop = json.getString("numOfPills");

                    fam = json.getString("family");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            if(result.contains("not")){
                DialogFragment newFragment = new notfoundD();
                newFragment.show(getSupportFragmentManager(), "dfd");
            }
            else{
                goTo2();
            }
        }

    }
    public class Connection2 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            arrayList=new ArrayList<String>();
            arrayList2=new ArrayList<String>();
            String line = null;

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {

                URL url = new URL(URL + "check.php?username=" + username+"&family="+fam);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                is = urlConnection.getInputStream();

            } catch (Exception e) {

                Log.e("Fail 1", e.toString());

            }


            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                result = sb.toString();

            } catch (Exception e) {

                Log.e("Fail 2", e.toString());

            }
            h="hi";

            try {
                JSONArray JA = new JSONArray(result);

                JSONObject json = null;


                for (int i = 0; i < JA.length(); i++) {


                    json = JA.getJSONObject(i);


                    fam2 = json.getString("family");
                    medname=json.getString("medname");
                    arrayList.add(fam2);
                    arrayList2.add(medname);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }




        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String result) {
/*
            Dialog dd;
            dd=new Dialog(getApplicationContext());
            dd.setContentView(R.layout.ddd);
            ttt=dd.findViewById(R.id.sss);*/
            Intent intent_m=new Intent();
            boolean f=true;
            boolean d=true;
            if(fam.equals("NSAIDs")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("Quinolones")) {

                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        f = false;

                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);


                    }
                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";

                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }
            else if(fam.equals("Quinolones")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("MTX")|| arrayList.get(i).equals("NSAIDs")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;
                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                    }
                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }  else if(fam.equals("MTX")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("Quinolones")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;
                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                    }
                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }
            else if(fam.equals("Macrolide")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("Statins")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;

                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);


                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                    }

                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }
            else if(fam.equals("Statins")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("Macrolide")||arrayList.get(i).equals("warfarin")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;

                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);


                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                    }

                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }
            else if(fam.equals("Ultram")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("warfarin")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;

                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                       /* dd.setTitle("تنبيه!");
                     //   dd.setMessage("يوجد لديك تعارض  مع "+ arrayList2.get(i));
                        ttt.setText("ll");
                        dd.show();
*/
                    }
                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }
            else if(fam.equals("warfarin")){
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).equals("Ultram")||arrayList.get(i).equals("Statins")){
                        DialogFragment newFragment = new interD ();
                        newFragment.show(getSupportFragmentManager(), "ddd");

                        f = false;

                        Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_SHORT).show();
                        Intent itAlarm = new Intent("INTERACTION");
                        itAlarm.putExtra("name",arrayList2.get(i));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, itAlarm, 0);

                        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarme.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

                    }
                }
                if(f){
                    Toast.makeText(getApplicationContext(), "allowed", Toast.LENGTH_SHORT).show();
                    intent_m.setClass(getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);
                    intent_m.putExtra("username",username);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    startActivity(intent_m);
                }
            }

        }

    }







}
