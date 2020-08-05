package com.example.MyMedicationDiary;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

public class background2 extends AsyncTask <String, Void,String> {


    public String URL="http://192.168.1.106/";
    AlertDialog dialog;
    Context context;


    /*
     *
     * */

    public background2(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Family Status");
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
            if(s.contains("hi")){
                Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();

            }

        }






    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String userr=voids[0];
        String famm = voids[1];

        // 192.168.1.107
        String host = URL + "check.php?username=" + userr+"&family="+famm;

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
}

