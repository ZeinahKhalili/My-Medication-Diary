package com.example.MyMedicationDiary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private TextView signup;
    // private TextView signin;
    TextView forgetpass;
    EditText usr, pas;
    String phoneNo;
    String userss;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().hide();
      //  onBackPressed();

        forgetpass=findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userss = usr.getText().toString();

                new ConnectionForget().execute();
            }
        });
        signup=(TextView) findViewById(R.id.sup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });



        login=(TextView) findViewById(R.id.logiin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });

        usr = (EditText) findViewById( R.id.usrusr);
        pas = (EditText) findViewById(R.id.passwrd);

        // signin=(TextView) findViewById(R.id.logiin);
        /*signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });*/
    }
    private void goTo(){
        Intent intent=new Intent(SecondActivity.this, SignUp.class);
        startActivity(intent);

    }
    private void goTo2(){

        String user = usr.getText().toString();
        String pass = pas.getText().toString();
        String method="login";
        background bg = new background(this);
        bg.execute(method,user,pass);

    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



    public class ConnectionForget extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "forgetpass.php?username=" + userss;

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
            phoneNo=result;
        }


    }


}
