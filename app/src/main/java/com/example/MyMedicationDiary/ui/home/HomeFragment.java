package com.example.MyMedicationDiary.ui.home;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.MyMedicationDiary.Constants;
import com.example.MyMedicationDiary.ListAdapter;
import com.example.MyMedicationDiary.R;
import com.example.MyMedicationDiary.Subject;
import com.example.MyMedicationDiary.camactivity;
import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class HomeFragment extends Fragment {
    ListView listView;

    public ArrayList<String> lines = new ArrayList<>();


    ArrayAdapter<String> adapter;
    private HomeViewModel homeViewModel;
    private FloatingActionButton scanbutton;
    public ArrayList<String> ar;

    public String user;
    public String fulld;
    public String medn;
    public String medt;
    public String medp;

    public String intervalDate;

    public String medd;
    public String medda;
    public String medti;


    public ListAdapter myadapt;

    public Dialog dialog;
    public Dialog dialog1;
    String medDose;
    int medDoses;

    public EditText mn;

    public EditText mt;
    public EditText mnop;
    public EditText mdoses;
    public EditText mtime;
    public EditText mnum;


    String Y,D,M;
    String Y1,D1,M1;

    public String ttt;

    public Button btn2;
    public Button btn3;
    public Button btnout;
    public Button bn,bn2;

    public String naame,daay,tyype,noop,tiime,doose,medtypeday,timeStamp;
    public String naamee,daayy,tyypee,noopp,tiimee,doosee;

    public String sometext1,sometext2,sometext3,sometext4,sometext5,sometext6;
    public int dosesCounter=0;

    String URL= Constants.UU.URL;

        long timeStampp;
    public    ArrayList <String> arrayListmedname=new ArrayList<>();
    public    ArrayList <String> arrayListmedtype=new ArrayList<>();
    public    ArrayList <String> arrayListmednop=new ArrayList<>();
    public    ArrayList <String> arrayListtypeday=new ArrayList<>();
    public    ArrayList <Long> arrayListtimeStamp=new ArrayList<>();
    public    ArrayList <Integer> arrayListDoses=new ArrayList<>();
    public    ArrayList <String> arrayListInterval=new ArrayList<>();


    String fullld1[];
    String fullld2[];




    int flag2=1;
    String dateINT;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {


            // code here
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


                Calendar calender = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());
                View view = getView();
                assert view != null;
                TextView textViewDate = view.findViewById(R.id.date);
                textViewDate.setText(currentDate);

                Date myDate = new Date();
                myDate=Calendar.getInstance().getTime();
                dateINT = dateFormatMonth.format(myDate);

                scanbutton = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);

                scanbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), camactivity.class);
                        intent.putExtra("username",user);
                        startActivity(intent);

                    }
                });


                user= SaveSharedPreference.getUserName(getContext());


                listView = (ListView) getView().findViewById(R.id.medlist);


                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);



                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.floating_popup);
                btnout=dialog.findViewById(R.id.btnn3);

                mn = dialog.findViewById(R.id.mn);
                mn.setFocusable(false);






                mt = dialog.findViewById(R.id.mt);
                mt.setFocusable(false);

                mnop = dialog.findViewById(R.id.mnop);
                mnop.setFocusable(false);




                mdoses = dialog.findViewById(R.id.mdoses);
                mdoses.setFocusable(false);


                mnum = dialog.findViewById(R.id.mnumtime);
                mnum.setFocusable(false);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position>=0) {
                            TextView textView = (TextView) view.findViewById(R.id.textView1);
                            String text = textView.getText().toString();
                            String t[] = text.split(":");
                            ttt=t[1];

                            new Connection2().execute();

                        }

                    }
                });

                new Connection().execute();

                btn2=dialog.findViewById(R.id.btnn2);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                btn3=dialog.findViewById(R.id.btnn2);
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1=new Dialog(getContext());
                        dialog1.setContentView(R.layout.floating_popup2);
                        dialog1.show();
                        bn=dialog1.findViewById(R.id.btnnn);
                        bn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Connection3().execute();
                            }
                        });
                        bn2=dialog1.findViewById(R.id.btnnn2);
                        bn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                    }
                });


                btnout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.hide();
                            }
                });



            }
        });



        return root;
    } // end of onCreate method


    public void Reload(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
    }


    public class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "showmeds.php?username=" + user;

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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            try {
                List<Subject> SubjectFullFormList;
                JSONObject jsonResult = new JSONObject(result);
                Subject subject;
                SubjectFullFormList = new ArrayList<Subject>();

                int success = jsonResult.getInt("success");
                if (success == 1) {
                    JSONArray meds = jsonResult.getJSONArray("meds");
                    for (int i = 0; i < meds.length(); i++) {
                        JSONObject med = meds.getJSONObject(i);
                        subject = new Subject();

                        medn = med.getString("medname");
                        medt = med.getString("medtype");
                        medp = med.getString("mednop");
                        medDose=med.getString("meddoses");


                        medtypeday=med.getString("typeday");
                        timeStamp=med.getString("timeStamp");

                        intervalDate=med.getString("intervalDate");

                        int num= Integer.parseInt(medp);
                        if(num<=5){
                            subject.Subject_nn = "أوشك  دواؤك على الانتهاء، من فضلك سارع في تجديد العلبة";

                        }

                        else {
                            subject.Subject_nn ="";
                        }


                        timeStampp=Long.parseLong(timeStamp);
                        medDoses=Integer.parseInt(medDose);

                        arrayListmedname.add(medn);
                        arrayListmedtype.add(medt);
                        arrayListmednop.add(medp);
                        arrayListtypeday.add(medtypeday);
                        arrayListtimeStamp.add(timeStampp);
                        arrayListDoses.add(medDoses);

                        arrayListInterval.add(intervalDate);


                        subject.Subject_Name = medn;
                        subject.Subject_Type = medt;
                        subject.Subject_NOP = medp;

                        listView.setVisibility(View.VISIBLE);

                        SubjectFullFormList.add(subject);

                        myadapt = new ListAdapter(SubjectFullFormList, getContext());

                        listView.setAdapter(myadapt);


                    }


                    for (int o=0;o<arrayListInterval.size();o++){
                        if (!(arrayListInterval.get(o).equals("onEnd"))) {
                            fullld1 = arrayListInterval.get(o).split("-");
                            Y = fullld1[0];
                            M = fullld1[1];
                            D = fullld1[2];
                            fullld2 = dateINT.split("-");
                            Y1 = fullld2[0];
                            M1 = fullld2[1];
                            D1 = fullld2[2];

                            if (Y.equals(Y1) && M.equals(M1) && D.equals(D1)) {
                                Toast.makeText(getContext(), "اليوم انتهي اخذك لدواء " + arrayListmedname.get(o), Toast.LENGTH_LONG).show();
                                  Intent itaAlarm = new Intent("ENDINTER");
                                itaAlarm.putExtra("username",user);
                                itaAlarm.putExtra("medname",arrayListmedname.get(o));
                                PendingIntent pendingIntentsss = PendingIntent.getBroadcast(getContext(), 5, itaAlarm,  PendingIntent.FLAG_ONE_SHOT);
                                AlarmManager alarmess = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                alarmess.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntentsss);


                            }

                        }
                    }

                    if(flag2==1){
                    for (int j = 0; j < arrayListtimeStamp.size(); j++) {
                        if(arrayListtimeStamp.get(j)>=System.currentTimeMillis()) {
                            Intent itAlarm = new Intent("MEDSALARM");

                            itAlarm.putExtra("user", user);
                            itAlarm.putExtra("medname", arrayListmedname.get(j));
                            itAlarm.putExtra("medtype", arrayListmedtype.get(j));
                            itAlarm.putExtra("mednop", arrayListmednop.get(j));

                            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getContext(), j, itAlarm, 0);
                            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getContext(), j, itAlarm, 0);
                            PendingIntent pendingIntent3=PendingIntent.getBroadcast(getContext() ,j,itAlarm,0);
                            PendingIntent pendingIntent4=PendingIntent.getBroadcast(getContext() ,j,itAlarm,0);



                            PendingIntent pendingIntent311 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);
                            PendingIntent pendingIntent31 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);
                            PendingIntent pendingIntent32 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);
                            PendingIntent pendingIntent33 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);



                            AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            AlarmManager alarme2 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            AlarmManager alarme3 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            AlarmManager alarme4 = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (arrayListtypeday.get(j).equals("daily")) {
                                    // 1 day = 86400000 milliseconds
                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),86400000, pendingIntent1);// everyday

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),43200000, pendingIntent1);//every 12 hours

                                    }
                                    else if(arrayListDoses.get(j)==3){

                                            alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),28800000, pendingIntent1);// every 8 hours


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),21600000, pendingIntent1);//every 6 hours

                                    }

                                }
                                else if(arrayListtypeday.get(j).equals("weeklly")){
                                    // 1 week = 604800000 millisecond
                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),604800000, pendingIntent2);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){


                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j), 604800000, pendingIntent2);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;

                                       alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,604800000, pendingIntent3);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),604800000, pendingIntent1);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,604800000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,604800000, pendingIntent3);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),604800000, pendingIntent1);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,604800000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,604800000, pendingIntent3);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,604800000, pendingIntent4);//4th

                                    }


                                }
                                else if (arrayListtypeday.get(j).equals("once")) {
                                    assert alarme != null;

                                    if(arrayListDoses.get(j)==1){
                                        alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j), pendingIntent311);

                                    }
                                    else  if(arrayListDoses.get(j)==2){
                                        alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j), pendingIntent3);//1st
                                        long l=arrayListtimeStamp.get(j)+43200000L;
                                       alarme2.setExact(AlarmManager.RTC_WAKEUP, l, pendingIntent31);

                                    }
                                    else  if(arrayListDoses.get(j)==3){
                                        alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j), pendingIntent31);
                                        long l=arrayListtimeStamp.get(j)+28800000L;
                                        long l2=l+28800000L;
                                        alarme2.setExact(AlarmManager.RTC_WAKEUP, l, pendingIntent32);
                                        alarme3.setExact(AlarmManager.RTC_WAKEUP, l2, pendingIntent33);
                                    }
                                    else  if(arrayListDoses.get(j)==4){
                                        alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j), pendingIntent311);
                                        long l=arrayListtimeStamp.get(j)+21600000L;
                                        long l2=l+21600000L;
                                        long l3=l2+21600000L;
                                        alarme2.setExact(AlarmManager.RTC_WAKEUP, l, pendingIntent31);
                                        alarme3.setExact(AlarmManager.RTC_WAKEUP, l2, pendingIntent32);
                                        alarme4.setExact(AlarmManager.RTC_WAKEUP, l3, pendingIntent33);
                                    }

                                }
                                else if(arrayListtypeday.get(j).equals("each2")){
                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),172800000, pendingIntent4);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),172800000, pendingIntent4);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,172800000, pendingIntent1);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),172800000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,172800000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,172800000, pendingIntent3);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),172800000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,172800000, pendingIntent3);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,172800000, pendingIntent2);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,172800000, pendingIntent1);//4th

                                    }
                                }
                                else if(arrayListtypeday.get(j).equals("each3")){
                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),259200000, pendingIntent4);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),259200000, pendingIntent4);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,259200000, pendingIntent2);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),259200000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,259200000, pendingIntent3);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,259200000, pendingIntent2);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),259200000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,259200000, pendingIntent3);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,259200000, pendingIntent2);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,259200000, pendingIntent1);//4th

                                    }

                                }
                                else if(arrayListtypeday.get(j).equals("each4")){

                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),345600000, pendingIntent4);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),345600000, pendingIntent4);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,345600000, pendingIntent2);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),345600000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,345600000, pendingIntent3);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,345600000, pendingIntent2);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),345600000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,345600000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,345600000, pendingIntent3);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,345600000, pendingIntent1);//4th

                                    }
                                }
                                else if(arrayListtypeday.get(j).equals("each5")){


                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),432000000, pendingIntent4);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),432000000, pendingIntent4);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,432000000, pendingIntent1);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),432000000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,432000000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,432000000, pendingIntent3);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),432000000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,432000000, pendingIntent1);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,432000000, pendingIntent2);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,432000000, pendingIntent3);//4th

                                    }

                                }
                                else if(arrayListtypeday.get(j).equals("each6")){


                                    if(arrayListDoses.get(j)==1){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),518400000, pendingIntent4);// every week

                                    }
                                    else if(arrayListDoses.get(j)==2){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),518400000, pendingIntent4);//1st
                                        long l= arrayListtimeStamp.get(j)+43200000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP,l,518400000, pendingIntent2);//2nd

                                    }
                                    else if(arrayListDoses.get(j)==3){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),518400000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+28800000;
                                        long l2=l+28800000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,518400000, pendingIntent1);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,518400000, pendingIntent2);//3rd


                                    }
                                    else if(arrayListDoses.get(j)==4){
                                        alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtimeStamp.get(j),518400000, pendingIntent4);//1st
                                        long l=arrayListtimeStamp.get(j)+21600000;
                                        long l2=l+21600000;
                                        long l3=l2+21600000;
                                        alarme2.setRepeating(AlarmManager.RTC_WAKEUP, l,518400000, pendingIntent2);//2nd
                                        alarme3.setRepeating(AlarmManager.RTC_WAKEUP, l2,518400000, pendingIntent1);//3rd
                                        alarme4.setRepeating(AlarmManager.RTC_WAKEUP, l3,518400000, pendingIntent3);//4th

                                    }

                                }

                            }
                            flag2=0;
                        }


                    }


                }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    private void del(String mednameaa) {
        ttt=mednameaa;
        new Connection3().execute();
    }


    public class Connection2 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "viewmeds.php?username=" + user+"&medname="+ttt;

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

            try {
                JSONObject jsonResult = new JSONObject(result);

                int success = jsonResult.getInt("success");
                if (success == 1) {
                    JSONArray meds = jsonResult.getJSONArray("meds");
                    for (int i = 0; i < meds.length(); i++) {
                        JSONObject med = meds.getJSONObject(i);

                        medn = med.getString("medname");
                        medt = med.getString("medtype");
                        medp = med.getString("mednop");
                        medd=med.getString("meddoses");
                        medda=med.getString("meddays");

                        mn.setText("اسم الدواء:"+medn);
                        mt.setText("نوع الدواء:"+medt);
                        mnop.setText("كمية الدواء:"+medp);
                        mdoses.setText("جرعة الدواء:"+medd);
                        mnum.setText("موعد أخذ الدواء:"+medda);


                        dialog.show();
                        String text11 = mn.getText().toString();
                        String t1[] = text11.split(":");
                        naame=t1[1];

                        String text21 = mt.getText().toString();
                        String t2[] = text21.split(":");
                        tyype=t2[1];

                        String text31 = mnop.getText().toString();
                        String t3[] = text31.split(":");
                        noop=t3[1];


                        String text51 = mdoses.getText().toString();
                        String t5[] = text51.split(":");
                        doose=t5[1];
                        String text61 = mnum.getText().toString();
                        String t6[] = text61.split(":");
                        daay=t6[1];



                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    public class Connection3 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "meddelete.php?username=" + user+"&medname="+ttt;

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

            dialog1.hide();
            dialog.hide();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

        }

    }



    public class Connection4 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "mededit.php";

            try {
                java.net.URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));

                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(ttt, "UTF-8")
                        + "&&" + URLEncoder.encode("medname2", "UTF-8") + "=" + URLEncoder.encode(naamee, "UTF-8")
                        + "&&" + URLEncoder.encode("medtype", "UTF-8") + "=" + URLEncoder.encode(tyypee, "UTF-8")
                        + "&&" + URLEncoder.encode("mednop", "UTF-8") + "=" + URLEncoder.encode(noopp, "UTF-8")
                        + "&&" + URLEncoder.encode("meddoses", "UTF-8") + "=" + URLEncoder.encode(doosee, "UTF-8")
                        + "&&" + URLEncoder.encode("meddays", "UTF-8") + "=" + URLEncoder.encode(daayy, "UTF-8");


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



            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;

        }


            @Override
        protected void onPostExecute(String result) {
                dialog.hide();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

        }

    }
}


















































