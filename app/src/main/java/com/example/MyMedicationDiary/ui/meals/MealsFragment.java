package com.example.MyMedicationDiary.ui.meals;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.MyMedicationDiary.ListAdapter3;
import com.example.MyMedicationDiary.R;
import com.example.MyMedicationDiary.Subject3;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.example.MyMedicationDiary.Constants.UU.URL;

public class MealsFragment extends Fragment {

    private mealsViewModel mealsViewModel;

    public String userr;

    ListView listView;
    ArrayAdapter<String> adapter;

    public ListAdapter3 myadapt;

    public    ArrayList <Long> arrayListtt=new ArrayList<>();
    public    ArrayList <String> arrayListnm=new ArrayList<>();
    public    ArrayList <String> arrayListnn=new ArrayList<>();
    public    ArrayList <String> arrayListTR=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mealsViewModel =
                ViewModelProviders.of(this).get(mealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_meal, container, false);
        final TextView textView = root.findViewById(R.id.text_meal);
        mealsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


                userr=getActivity().getIntent().getExtras().getString("username");


                listView=(ListView) getView().findViewById(R.id.meallist);


                new ConnectionM().execute();


            }
        });
        return root;
    }


    public class ConnectionM extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            String host=URL+"showmeal.php?username="+userr;

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response=client.execute(request);
                BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer=new StringBuffer("");
                String line="";

                while ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result=stringBuffer.toString();

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
        protected void onPostExecute( String result ){
            try {


                JSONObject jsonResult = new JSONObject(result);

                List<Subject3> SubjectFullFormList;

                Subject3 subject;
                SubjectFullFormList = new ArrayList<Subject3>();


                int success=jsonResult.getInt("success");
                if(success==1){
                    JSONArray meals=jsonResult.getJSONArray("meals");
                    for (int i = 0; i < meals.length(); i++) {
                        JSONObject meal=meals.getJSONObject(i);
                        subject = new Subject3();

                        String medname;
                        String mealn;
                        String timeStamp;
                        String typeofrep;
                        String mty;
                        medname = meal.getString("medname");
                        mealn = meal.getString("mname");
                        timeStamp=meal.getString("timeStamp");
                        typeofrep=meal.getString("typeofrep");
                        mty=meal.getString("mealtimestring");


                        long timee=Long.parseLong(timeStamp);




                        arrayListtt.add(timee);
                        arrayListnm.add(medname);
                        arrayListnn.add(mealn);
                        arrayListTR.add(typeofrep);

                        subject.Subject_Nameee=medname;
                        subject.Subject_MN=mealn;
                        subject.Subject_T=mty;

                        listView.setVisibility(View.VISIBLE);

                        SubjectFullFormList.add(subject);

                        myadapt=new ListAdapter3(SubjectFullFormList,getContext());

                        listView.setAdapter(myadapt);


                    }
                    for (int j = 0; j < arrayListtt.size(); j++) {

                        if(arrayListtt.get(j)>=System.currentTimeMillis()) {
                            Intent itAlarm = new Intent("MEALALARM");


                            itAlarm.putExtra("mealname", arrayListnn.get(j));
                           itAlarm.putExtra("medname", arrayListnm.get(j));
                            itAlarm.putExtra("user", userr);

                            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);
                            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                            if(arrayListTR.get(j).equals("once")) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListtt.get(j), pendingIntent1);

                                }
                            }
                            else if(arrayListTR.get(j).equals("daily")){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtt.get(j),86400000, pendingIntent2);

                                }
                            }
                            else if(arrayListTR.get(j).equals("weeklly")){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    alarme.setRepeating(AlarmManager.RTC_WAKEUP, arrayListtt.get(j),604800000, pendingIntent2);

                                }
                            }

                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}