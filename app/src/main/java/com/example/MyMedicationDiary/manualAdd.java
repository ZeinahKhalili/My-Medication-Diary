package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class manualAdd extends AppCompatActivity {
        String name,type,quant,username;
        EditText n,ty,q;
        Button insert;
        String barcode;
        String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add);
        s1="عدد مرات اخذ الدواء اسبوعياً";
        Intent t=getIntent();
       barcode= t.getStringExtra("barcode");

       n=findViewById(R.id.namemedd);
        ty=findViewById(R.id.typpmed);
        q=findViewById(R.id.quanmedd);
        insert=findViewById(R.id.btninsertman);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=n.getText().toString();
                type=ty.getText().toString();
                quant=q.getText().toString();
                new Connectionmanual().execute();

            }
        });

    }


    public class Connectionmanual extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "manualAdd.php?medname=" + name+"&medtype="+type+"&mednop="+quant+"&family="+"NSAIDs"+"&barcode="+barcode;

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

            Intent intent_m=new Intent(manualAdd.this,newMed.class);

            intent_m.putExtra("nname", name);
            intent_m.putExtra("ttype", type);
            intent_m.putExtra("nnop", quant);
            intent_m.putExtra("family","NONE");
            intent_m.putExtra("username",username);

            String s1="عدد مرات اخذ الدواء اسبوعياً";

            intent_m.putExtra("s1", s1);
            startActivity(intent_m);
        }
    }



}
