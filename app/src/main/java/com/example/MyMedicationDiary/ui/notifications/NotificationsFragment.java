package com.example.MyMedicationDiary.ui.notifications;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.MyMedicationDiary.Home;
import com.example.MyMedicationDiary.R;
import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;
import static com.example.MyMedicationDiary.Constants.UU.URL;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    CompactCalendarView calender;
    TextView te;
    Button delvac;
    String x;
    Dialog dialog;
    Button yes,no;
    String filename;
String user;
    long time1,time2,time3,time4,time5,time6,time7;
int day,month,year;
     ActionBar actionBar;
    Dialog d;
    String DescVac;
     TextView tv,vacT;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
long ltime;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
             //   user = getActivity().getIntent().getExtras().getString("username");
                user= SaveSharedPreference.getUserName(getContext());


                delvac=getActivity().findViewById(R.id.btndelvac);
                te=getActivity().findViewById(R.id.datebth);
                calender=getActivity().findViewById(R.id.calendarView);
                calender.setUseThreeLetterAbbreviation(true);
                tv=getActivity().findViewById(R.id.titmo);
                Date myDate = new Date();
                 filename = dateFormatMonth.format(myDate);
                tv.setText(filename);
                tv.setTextColor(Color.parseColor("#F3B3A5"));
                d=new Dialog(getContext());

               // calender.set

                d.setContentView(R.layout.vacd);
                vacT=d.findViewById(R.id.vacT);

                new ConnectionBTH().execute();


                delvac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog=new Dialog(getContext());
                        dialog.setContentView(R.layout.floating_popupdelvac);
                        dialog.show();
                        yes=dialog.findViewById(R.id.btnyesdel);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Connectiondelvac().execute();
                            }
                        });
                        no=dialog.findViewById(R.id.btnnodel);
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });



            }
        });

        //te.setText(x);
        return root;
    }


    public class ConnectionBTH extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            String line = null;

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {

                java.net.URL url = new URL(URL + "showbth.php?username=" + user);

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
            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        @Override
        protected void onPostExecute(String result) {
            te.append(result);

            try {
                calender.setCurrentDate(new SimpleDateFormat("yyyy/MM/dd").parse(result));


                  ltime=new SimpleDateFormat("yyyy/MM/dd").parse(result).getTime(); //timeStamp of Date of Birth.

               ltime+=25200000L;// at 7 am




            } catch (ParseException e) {
                e.printStackTrace();
            }

            time1=ltime+2592000000L; //1 month
            Event ev1 = new Event(Color.CYAN, time1, "first Vac");
            calender.addEvent(ev1);
           time3=ltime+ 5184000000L; //2 months
            Event ev3 = new Event(Color.CYAN, time3, "Third Vac");
            calender.addEvent(ev3);
            Event ev33 = new Event(Color.RED, time3, "Third Vac");
            calender.addEvent(ev33);
            Event ev333 = new Event(Color.GRAY, time3, "Third Vac");
            calender.addEvent(ev333);
            Event ev3333 = new Event(Color.MAGENTA, time3, "Third Vac");
            calender.addEvent(ev3333);
            time2=ltime+ 10368000000L; //4 months
            Event ev2 = new Event(Color.CYAN, time2, "Third Vac");
            calender.addEvent(ev2);
            Event ev22 = new Event(Color.RED, time2, "Third Vac");
            calender.addEvent(ev22);
            Event ev222 = new Event(Color.GRAY, time2, "Third Vac");
            calender.addEvent(ev222);
            Event ev2222 = new Event(Color.MAGENTA, time2, "Third Vac");
            calender.addEvent(ev2222);
            time4=ltime+15552000000L; //6 months
            Event ev4 = new Event(Color.CYAN, time4, "Forth Vac");
            calender.addEvent(ev4);
            Event ev44 = new Event(Color.CYAN, time4, "Forth Vac");
            calender.addEvent(ev44);
            Event ev444 = new Event(Color.CYAN, time4, "Forth Vac");
            calender.addEvent(ev444);
            Event ev4444 = new Event(Color.CYAN, time4, "Forth Vac");
            calender.addEvent(ev4444);
            time5=ltime+31104000000L; // 1 year
            Event ev5 = new Event(Color.CYAN, time5, "Fifth Vac");
            calender.addEvent(ev5);
            Event ev55 = new Event(Color.CYAN, time5, "Fifth Vac");
            calender.addEvent(ev55);
            Event ev555 = new Event(Color.CYAN, time5, "Fifth Vac");
            calender.addEvent(ev555);
            Event ev5555 = new Event(Color.CYAN, time5, "Fifth Vac");
            calender.addEvent(ev5555);
            time6=ltime+47335428000L; // 1.5 years
            Event ev6 = new Event(Color.CYAN, time6, "Sixth Vac");
            calender.addEvent(ev6);
            Event ev66 = new Event(Color.CYAN, time6, "Sixth Vac");
            calender.addEvent(ev66);
            time7=ltime+63113904000L; // 2 years
            Event ev7 = new Event(Color.CYAN, time7, "Seventh Vac");
            calender.addEvent(ev7);
            calender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    Context context = getContext();
                    long r=dateClicked.getTime()+25200000L;
                            if (r==time1) {
                                /* Toast.makeText(context, "First Vac", Toast.LENGTH_SHORT).show();*/

                                vacT.setText("لدى طفلك اليوم لقاح التهاب الكبد B الجرعة الاولى");
                                DescVac=vacT.getText().toString();
                                d.show();

                            }
                            else if(r==time3) {
                                vacT.setText("لدى طفلك اليوم الجرعة الاولى لل4 لقاحات المذكورة أدناه:");
                                vacT.append("\n");
                                vacT.append("لقاح الكزاز \n لقاح الانفلونزا B \n لقاح المكورات الرئوية \n لقاح الفيروسة العجلية ");
                                DescVac=vacT.getText().toString();

                                d.show();

                            }
                            else if(r==time2) {
                                vacT.setText("لدى طفلك اليوم الجرعة الثانية لل4 لقاحات المذكورة أدناه:");
                                vacT.append("\n");

                                vacT.append("لقاح الكزاز \n لقاح الانفلونزا B \n لقاح المكورات الرئوية \n لقاح الفيروسة العجلية ");
                                DescVac=vacT.getText().toString();

                                d.show();

                            }
                            else if(r==time4) {
                                vacT.setText("لدى طفلك اليوم الجرعة الثالثة لل4 لقاحات المذكورة أدناه:");
                                vacT.append("\n");

                                vacT.append("لقاح الكزاز \n لقاح الانفلونزا B \n لقاح التهاب الكبد B \n لقاح الفيروسة العجلية ");
                                DescVac=vacT.getText().toString();

                                d.show();

                            }
                            else if(r==time5) {
                                vacT.setText("لدى طفلك اليوم الجرعة الرابعة/الرابعة/الثالثة/الاولى لل4 لقاحات المذكورة أدناه بالترتيب:");
                                vacT.append("\n");
                                vacT.append("لقاح الكزاز \n لقاح الانفلونزا B \n لقاح التهاب الكبد B \n لقاح الحصبة المائية ");
                                DescVac=vacT.getText().toString();

                                d.show();
                            }
                            else if(r==time6) {
                                vacT.setText("لدى طفلك اليوم اللقاحات التالية:");
                                vacT.append("\n");

                                vacT.append(" لقاح شلل الأطفال \n التهاب الكبد A ");
                                DescVac=vacT.getText().toString();

                                d.show();

                            }
                            else if(r==time7) {
                                vacT.setText("لدى طفلك اليوم لقاح التهاب الكبد A الجرعة الثانية");
                                DescVac=vacT.getText().toString();

                                d.show();
                            }
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    tv.setText(dateFormatMonth.format(firstDayOfNewMonth));
                    if(dateFormatMonth.format(firstDayOfNewMonth).equals(filename)){
                        tv.setTextColor(Color.parseColor("#F3B3A5"));
                    }
                    else
                    tv.setTextColor(Color.parseColor("#85BEAE"));

                }
            });

            Intent itAlarm = new Intent("VAC");

            itAlarm.putExtra("DescVac","لدى طفلك اليوم لقاح التهاب الكبد B الجرعة الاولى");
            itAlarm.putExtra("username",user);

            Intent itAlarm2 = new Intent("VAC");

            itAlarm2.putExtra("DescVac","لدى طفلك اليوم موعد لاخذ لقاح الكزاز \n لقاح الانفلونزا B \n لقاح المكورات الرئوية \n لقاح الفيروسة العجلية ");
            itAlarm2.putExtra("username",user);



            Intent itAlarm3 = new Intent("VAC");

            itAlarm3.putExtra("DescVac","لدى طفلك اليوم موعد لأخذ لقاح الكزاز \n لقاح الانفلونزا B \n لقاح المكورات الرئوية \n لقاح الفيروسة العجلية ");
            itAlarm3.putExtra("username",user);

            Intent itAlarm4 = new Intent("VAC");
            itAlarm4.putExtra("username",user);

            itAlarm4.putExtra("DescVac","لدى طفلك اليوم موعد لأخذ لقاح الكزاز \n لقاح الانفلونزا B \n لقاح التهاب الكبد B \n لقاح الفيروسة العجلية ");

            Intent itAlarm5 = new Intent("VAC");

            itAlarm5.putExtra("DescVac","لدى طفلك اليوم موعد لأخذ لقاح الكزاز \n لقاح الانفلونزا B \n لقاح التهاب الكبد B \n لقاح الحصبة المائية ");
            itAlarm5.putExtra("username",user);

            Intent itAlarm6 = new Intent("VAC");

            itAlarm6.putExtra("DescVac","لدى طفلك اليوم موعد لأخذ لقاح شلل الأطفال \n التهاب الكبد A ");
            itAlarm6.putExtra("username",user);

            Intent itAlarm7 = new Intent("VAC");

            itAlarm7.putExtra("DescVac","لدى طفلك اليوم موعد لأخذ لقاح التهاب الكبد A الجرعة الثانية");
            itAlarm7.putExtra("username",user);




            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getContext(),1, itAlarm, 0);

            AlarmManager alarme1 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                    alarme1.setExact(AlarmManager.RTC_WAKEUP, time1, pendingIntent1);
                }

            PendingIntent pendingIntent2= PendingIntent.getBroadcast(getContext(),2, itAlarm2, 0);

            AlarmManager alarme2 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme2.setExact(AlarmManager.RTC_WAKEUP, time3, pendingIntent2);
            }

            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(getContext(),3, itAlarm3, 0);

            AlarmManager alarme3 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme3.setExact(AlarmManager.RTC_WAKEUP, time2, pendingIntent3);
            }

            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(getContext(),4, itAlarm4, 0);

            AlarmManager alarme4 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme4.setExact(AlarmManager.RTC_WAKEUP, time4, pendingIntent4);
            }

            PendingIntent pendingIntent5= PendingIntent.getBroadcast(getContext(),5, itAlarm5, 0);

            AlarmManager alarme5 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme5.setExact(AlarmManager.RTC_WAKEUP, time5, pendingIntent5);
            }

            PendingIntent pendingIntent6 = PendingIntent.getBroadcast(getContext(),6, itAlarm6, 0);

            AlarmManager alarme6 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme6.setExact(AlarmManager.RTC_WAKEUP, time6, pendingIntent6);
            }

            PendingIntent pendingIntent7 = PendingIntent.getBroadcast(getContext(),7, itAlarm7, 0);

            AlarmManager alarme7 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarme7.setExact(AlarmManager.RTC_WAKEUP, time7, pendingIntent7);
            }


        }

    }

    public class Connectiondelvac extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "vacdel.php?username=" + user;

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
        protected void onPostExecute(String resulta) {

            dialog.hide();
            if(resulta.equals("deleted")){
                Intent t=new Intent(getActivity(), Home.class);
                t.putExtra("username",user);
                t.putExtra("activity","vac");
                startActivity(t);
            }
            else {
                Toast.makeText(getContext(),resulta,Toast.LENGTH_LONG).show();
            }


        }

    }



}