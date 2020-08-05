package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Calendar;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.MyMedicationDiary.Constants.UU.URL;

public class ProfileActivity extends AppCompatActivity implements profDd.profDdd{
    static String  user;
        EditText userTV,passTV,phoneTV,chrons;
        String userS,passS,phoneS,chronsS,vaccine;
        Button edd,edd2;
        Button yes,no;
    DialogFragment del;
   public static Button addbaby;

   TextView tv;
    String phonenew,passnew;
        TextView logg;
        Dialog d;
        TextView delete;
        TextView newVac;
      public static String dateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent t=getIntent();

        user=t.getStringExtra("username");

        tv=findViewById(R.id.textViewP);
        userTV=findViewById(R.id.usern);
        passTV=findViewById(R.id.passwoord);
        phoneTV=findViewById(R.id.phonenum);
        chrons=findViewById(R.id.chrons);
        delete=findViewById(R.id.deleteAcc);
        logg=findViewById(R.id.logout);
        newVac=findViewById(R.id.vacc);
        addbaby=findViewById(R.id.vacAdd);
        addbaby.setVisibility(INVISIBLE);
        tv.setVisibility(INVISIBLE);

        userTV.setFocusable(false);
        passTV.setFocusable(false);
        passTV.setVisibility(View.INVISIBLE);

        phoneTV.setFocusable(false);
        chrons.setFocusable(false);

        new Connection().execute();
        edd=findViewById(R.id.btnsaavvee);
        edd2=findViewById(R.id.btnsaavvee2);
        edd2.setVisibility(INVISIBLE);
        // تعديل
        edd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                passTV.setFocusableInTouchMode(true);
                phoneTV.setFocusableInTouchMode(true);
                edd.setVisibility(INVISIBLE);
                passTV.setVisibility(VISIBLE);
                tv.setVisibility(VISIBLE);
                edd2.setVisibility(VISIBLE);

            }
        });
        //store
        edd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passdialogactivity passd=new passdialogactivity();
                passd.show(getSupportFragmentManager(),"passdialog");


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               del=new profDd();
                del.setCancelable(false);
                del.show(getSupportFragmentManager(),"hi");


            }
        });
        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectionOutt().execute();
            }
        });
        newVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        addbaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newVac.setText("تاريخ ميلاد طفلك هو "+dateOfBirth);
                new ConnAddVac().execute();
            }
        });

    }

    @Override
    public void onYesClicked() {
        passdialogdelacc passdi=new passdialogdelacc();
        passdi.show(getSupportFragmentManager(),"passdialog");

    }

    @Override
    public void onNoClicked() {
            del.dismiss();
    }

    public class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "showinfo.php?username=" + user;

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
                    JSONArray info = jsonResult.getJSONArray("info");
                    for (int i = 0; i < info.length(); i++) {
                        JSONObject inf = info.getJSONObject(i);

                        userS = inf.getString("username");
                        passS = inf.getString("password");
                        phoneS = inf.getString("phone");
                        chronsS=inf.getString("cds");
                        vaccine=inf.getString("vaccine");




                    }
                    userTV.setText(userS);
                    passTV.setText("");
                    phoneTV.setText(phoneS);
                    chrons.setText(chronsS);
                    if(vaccine.equals("no")){
                        newVac.setVisibility(VISIBLE);
                    }
                    else{
                        newVac.setVisibility(newVac.INVISIBLE);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public class Connection2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "edittedProf.php?username=" + user+"&password="+passnew+"&phone="+phonenew;

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

        }


    }

    public class ConnectionDel extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "delAcc.php?username=" + user;

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

         Intent t=new Intent(ProfileActivity.this,SecondActivity.class);
         startActivity(t);
        }


    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return  dialog;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month=month+1;
            dateOfBirth=year+"/"+month+"/"+dayOfMonth;
            Toast.makeText(getContext(),dateOfBirth,Toast.LENGTH_SHORT).show();
            addbaby.setVisibility(VISIBLE);
            //goTo();

        }


    }
    public  class ConnAddVac extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "addVac.php?username=" + user+"&dateOfBirth="+dateOfBirth+"&vaccine="+"yes";


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
        protected  void onPostExecute(String result) {
            addbaby.setVisibility(INVISIBLE);
            Intent t=new Intent(ProfileActivity.this,Home.class);
            t.putExtra("username",user);
            startActivity(t);
        }


    }

    public class ConnectionOutt extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            String line = null;

            HttpURLConnection urlConnection = null;
            String host = Constants.UU.URL + "getOut.php";

            InputStream is = null;
            try {

                java.net.URL url = new URL(host);
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
            Intent intent=new Intent(ProfileActivity.this,SecondActivity.class);
            SaveSharedPreference.clearUserName(getApplicationContext());
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
        }

    }

}
