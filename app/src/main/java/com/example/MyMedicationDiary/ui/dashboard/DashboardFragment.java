package com.example.MyMedicationDiary.ui.dashboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.MyMedicationDiary.Constants;
import com.example.MyMedicationDiary.R;
import com.example.MyMedicationDiary.measures;
import com.example.MyMedicationDiary.ui.SaveSharedPreference;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    TextView sugar,pressure,heart;
    TextView dan;
    Button newInsert;
    EditText sugarED,pressureED,heartED;
    Button insertm,viewm;
    String pr,su,he;
    String mtype;

    String user;
    Calendar c;
        String date,time;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                user= SaveSharedPreference.getUserName(getContext());


                dan=getActivity().findViewById(R.id.dandan);
                newInsert=getActivity().findViewById(R.id.insertmeasure2);

                sugar=getActivity().findViewById(R.id.sometextsug);
                pressure=getActivity().findViewById(R.id.sometextpres);
                heart=getActivity().findViewById(R.id.sometexthea);

                sugarED=getActivity().findViewById(R.id.textsug);
                pressureED=getActivity().findViewById(R.id.textpres);
                heartED=getActivity().findViewById(R.id.texthea);

                insertm=getActivity().findViewById(R.id.insertmeasure);
                viewm=getActivity().findViewById(R.id.viewmwasures);

                sugar.setVisibility(View.INVISIBLE);
                pressure.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.INVISIBLE);
                sugarED.setVisibility(View.INVISIBLE);
                pressureED.setVisibility(View.INVISIBLE);
                heartED.setVisibility(View.INVISIBLE);
                dan.setVisibility(View.INVISIBLE);
                newInsert.setVisibility(View.INVISIBLE);

                new Connection().execute();

                insertm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        if(!(pressure.getText().toString().equals(""))){
                           pr= pressureED.getText().toString();
                           if(pr.equals("")){
                               Toast.makeText(getContext(),"الرجاء ادخل الفحص اولا",Toast.LENGTH_LONG).show();
                           }
                           else {
                               new Connectionpress().execute();
                           }
                        }
                        if(!(sugar.getText().toString().equals(""))){
                             su= sugarED.getText().toString();

                            if(su.equals("")){
                                Toast.makeText(getContext(),"الرجاء ادخل الفحص اولا",Toast.LENGTH_LONG).show();
                            }
                            else {
                                new Connectionsugar().execute();
                            }
                        }
                        if(!(heart.getText().toString().equals(""))){
                             he= heartED.getText().toString();

                            if(he.equals("")){
                                Toast.makeText(getContext(),"الرجاء ادخل الفحص اولا",Toast.LENGTH_LONG).show();
                            }
                            else {
                                new Connectionheart().execute();
                            }
                        }

                    }
                });
                newInsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newInsert.setVisibility(View.INVISIBLE);
                        dan.setVisibility(View.INVISIBLE);
                        insertm.setVisibility(View.VISIBLE);

                        if(!(pressure.getText().toString().equals(""))){
                            pressure.setVisibility(View.VISIBLE);
                            pressureED.setVisibility(View.VISIBLE);
                        }
                        if(!(sugar.getText().toString().equals(""))){
                            sugar.setVisibility(View.VISIBLE);
                            sugarED.setVisibility(View.VISIBLE);
                        }
                        if(!(heart.getText().toString().equals(""))){
                            heart.setVisibility(View.VISIBLE);
                            heartED.setVisibility(View.VISIBLE);
                        }

                    }
                });
                viewm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), measures.class);

                        startActivity(intent);
                    }
                });



            }
        });
        return root;
    }





    public class Connectionpress extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "addpressure.php?username="+user+"&pressure="+pr+"&date="+date+"&time="+time+"&measuretype="+"ضغط";;

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

            if(resultt.equals("success")){
                sugarED.setText("");
                pressureED.setText("");
                heartED.setText("");
                insertm.setVisibility(View.INVISIBLE);
                sugar.setVisibility(View.INVISIBLE);
                pressure.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.INVISIBLE);
                sugarED.setVisibility(View.INVISIBLE);
                pressureED.setVisibility(View.INVISIBLE);
                heartED.setVisibility(View.INVISIBLE);
                dan.setVisibility(View.VISIBLE);
                newInsert.setVisibility(View.VISIBLE);
                int pe=Integer.parseInt(pr);
                if(pe>180){
                    Toast.makeText(getContext(),"انتبه ضغط الدم لديك مرتفع",Toast.LENGTH_LONG).show();
                }
            }

        }
    }


    public class Connectionsugar extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "addsugar.php?username="+user+"&sugar="+su+"&date="+date+"&time="+time+"&measuretype="+"السكري";;

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
            insertm.setVisibility(View.INVISIBLE);
            sugarED.setText("");
            pressureED.setText("");
            heartED.setText("");
            sugar.setVisibility(View.INVISIBLE);
            pressure.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.INVISIBLE);
            sugarED.setVisibility(View.INVISIBLE);
            pressureED.setVisibility(View.INVISIBLE);
            heartED.setVisibility(View.INVISIBLE);
            dan.setVisibility(View.VISIBLE);
            newInsert.setVisibility(View.VISIBLE);
            int so=Integer.parseInt(su);
            if(so>200){
                Toast.makeText(getContext(),"انتبه نسبة السكر لديك مرتفعة",Toast.LENGTH_LONG).show();
            }
        }
    }

    public class Connectionheart extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = Constants.UU.URL + "addheart.php?username="+user+"&heart="+he+"&date="+date+"&time="+time+"&measuretype="+"القلب";;

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
            insertm.setVisibility(View.INVISIBLE);
            sugarED.setText("");
            pressureED.setText("");
            heartED.setText("");
            sugar.setVisibility(View.INVISIBLE);
            pressure.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.INVISIBLE);
            sugarED.setVisibility(View.INVISIBLE);
            pressureED.setVisibility(View.INVISIBLE);
            heartED.setVisibility(View.INVISIBLE);
            dan.setVisibility(View.VISIBLE);
            newInsert.setVisibility(View.VISIBLE);
            int h=Integer.parseInt(he);
            if(h>110){
                Toast.makeText(getContext(),"انتبه نبضات القلب لديك مرتفعة",Toast.LENGTH_LONG).show();
            }
        }
    }
    public class Connection extends AsyncTask<String, String, String> {

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
            if(resultt.contains("ارتفاع ضغط الدم")){
                pressure.setText("ارتفاع ضغط الدم");
                pressure.setVisibility(View.VISIBLE);
                pressureED.setVisibility(View.VISIBLE);
            }
            if(resultt.contains("السكري")){
                sugar.setText("السكري");
                sugar.setVisibility(View.VISIBLE);
                sugarED.setVisibility(View.VISIBLE);
            }
            if(resultt.contains("امراض القلب")){
                heart.setText("امراض القلب");
                heart.setVisibility(View.VISIBLE);
                heartED.setVisibility(View.VISIBLE);
            }

        }
    }

}