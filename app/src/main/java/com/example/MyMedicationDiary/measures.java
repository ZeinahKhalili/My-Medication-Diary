package com.example.MyMedicationDiary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;

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

public class measures extends AppCompatActivity {
String user;
ListView listView;
String mval,mtype,date,time;
    public ListAdapterMeas myadapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measures);
        user= SaveSharedPreference.getUserName(getApplicationContext());

        listView = (ListView)findViewById(R.id.measuresList);
        new ConnectionMeasures().execute();



    }



    public class ConnectionMeasures extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "showmeasures.php?username=" + user;

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
                List<SubjectMe> SubjectFullFormList;
                JSONObject jsonResult = new JSONObject(result);
                SubjectMe subject;
                SubjectFullFormList = new ArrayList<SubjectMe>();

                int success = jsonResult.getInt("success");
                if (success == 1) {
                    JSONArray measures = jsonResult.getJSONArray("meds");
                    for (int i = 0; i < measures.length(); i++) {
                        JSONObject med = measures.getJSONObject(i);
                        subject = new SubjectMe();

                        mval = med.getString("measurevalue");
                        mtype = med.getString("measuretype");
                        date = med.getString("date");
                        time = med.getString("time");

                        subject.Subject_Val = mval;
                        subject.Subject_Type = mtype;
                        subject.Subject_date = date;
                        subject.Subject_time = time;

                        listView.setVisibility(View.VISIBLE);

                        SubjectFullFormList.add(subject);

                        myadapt = new ListAdapterMeas(SubjectFullFormList, getApplicationContext());

                        listView.setAdapter(myadapt);


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

}
