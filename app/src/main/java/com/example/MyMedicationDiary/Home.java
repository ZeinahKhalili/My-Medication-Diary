package com.example.MyMedicationDiary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Home extends AppCompatActivity{
        MenuItem menuItem;
    MenuItem menuItem2;

    Button btout;
        Switch sw;
        String user;
        String ss;
    String result = "";
    String resultt;

    String tipText,broughtBy;
    String count;
    int counter=0;

    String activity;
   // public ComponentName getCallingActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().hide();
        new ConnectionV().execute();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_meal,R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

         menuItem = navView.getMenu().getItem(3);
       menuItem2 = navView.getMenu().getItem(0);

       // menuItem2.setVisible(false);

        Intent t=getIntent();
            user=t.getStringExtra("username");
            activity=t.getStringExtra("activity");

            user=SaveSharedPreference.getUserName(getApplicationContext());

        btout=(Button)findViewById(R.id.btnout);
        btout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectionOut().execute();
            }
        });

        new Connectionch().execute();


        int min=0, max=11;

       counter = (int)(Math.random() * (max - min + 1) + min);
        count=Integer.toString(counter);



        if (!(sharedPref.getLastDate(getApplicationContext()).equals(new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(new Date()))) || !(sharedPref.getLastUserName(getApplicationContext()).equals(user)))
        {
            new Connectiontips().execute();
            sharedPref.setLastDate(getApplicationContext(),  new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(new Date()));
            sharedPref.setLastUserName(getApplicationContext(), user);

        }
        else
        {

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prof,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile:
                Intent T=new Intent(new Intent(this,ProfileActivity.class));
                T.putExtra("username",user);
                startActivity(T);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ConnectionV extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String line = null;

            HttpURLConnection urlConnection = null;
            String host = Constants.UU.URL + "getvac.php";

            InputStream is = null;
            try {

                URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

                while ((line = reader.readLine()) != null) {
                    result += line;
                }


                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            resultt = result;
            if (resultt.equals("yes")) {
                menuItem.setVisible(true);
            } else if (resultt.equals("no")) {
                menuItem.setVisible(false);
            }

        }

    }

    public class ConnectionOut extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String line = null;

            HttpURLConnection urlConnection = null;
            String host = Constants.UU.URL + "getOut.php";

            InputStream is = null;
            try {

                URL url = new URL(host);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));

                while ((line = reader.readLine()) != null) {
                    result += line;
                }


                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String resultd) {
            Intent intent=new Intent(Home.this,SecondActivity.class);
            SaveSharedPreference.clearUserName(getApplicationContext());
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
        }

    }


    public class Connectionch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "getch.php?username="+user;

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
        protected void onPostExecute(String resultt) {
            if(resultt.contains("لا يوجد")){
                menuItem2.setVisible(false);
            }

        }
    }




    public class Connectiontips extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "tips.php?counter=" + count;

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
            String tips[]=result.split("-");
            tipText=tips[0];
            broughtBy=tips[1];


            tipsD dialogFragment = new tipsD();
           /* Bundle bundle = new Bundle();
            bundle.putString("tipText",tipText);
            bundle.putString("broughtBy",broughtBy);

            dialogFragment.setArguments(bundle);
*/
            dialogFragment.show(getSupportFragmentManager(),"dialog");



        }
    }

}
