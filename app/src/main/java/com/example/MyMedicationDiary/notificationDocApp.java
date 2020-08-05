package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class notificationDocApp extends AppCompatActivity {

   public String doctor_name;
    public String doctor_spec;
    public String user;
    public TextView textView;
    public TextView textView2;
    public Button done;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_doc_app);

        Intent t=getIntent();

       doctor_name= t.getStringExtra("username");
       doctor_spec=t.getStringExtra("spec");
       user=t.getStringExtra("user");
       textView=findViewById(R.id.textView44);
       textView2=findViewById(R.id.textView55);
       textView.append(doctor_name);
        textView2.append(doctor_spec);
        done=findViewById(R.id.btndone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectionDone().execute();
            }
        });

    }

    public class ConnectionDone extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String URL= Constants.UU.URL;

            String result = "";
            String host = URL + "appdelete.php?username=" + user+"&docname="+doctor_name;

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

            Intent intent=new Intent(notificationDocApp.this, Home.class);
            intent.putExtra("username",user);
            startActivity(intent);
        }

    }





}
