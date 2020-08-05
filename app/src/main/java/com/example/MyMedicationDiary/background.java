package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.example.MyMedicationDiary.ui.home.HomeFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


import org.json.JSONObject;

public class background extends AsyncTask <String, Void,String> {
    JSONObject jsonObject = null;
    String StringHolder = "";
    public String nn = "";
    public String tt = "";
    public  String pp = "";
    public String linn;
    String family;
    public String pass;
    public ArrayList<String> liness=new ArrayList<>();

    String URL= Constants.UU.URL;

    public static String username="";

    public Intent intent_nammme = new Intent();
    public Intent intent_n = new Intent();
    public Intent intent_m = new Intent();
    public Intent intt=new Intent();
    public Intent intt2=new Intent();
    public Intent intt3=new Intent();

    public Intent intent_namme=new Intent();
    public String [] meds;
            public  Intent intent_nammmee = new Intent();

    newMed n = new newMed();
    AlertDialog dialog;
    Context context;
    public Boolean login = false;
    public   String doctorname;
    public  String doctorspec;
    public  String doctordate;
    public  String m;
    String fam;
    String vacav;


    /*
    *
     * */

    public background(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("My Medication Diary");
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
        if (s.contains("success")) {

            SaveSharedPreference.setUserName(context.getApplicationContext(),username);
            SaveSharedPreference.setPass(context.getApplicationContext(),pass);

           // sharedPref.setLastUserName(context.getApplicationContext(), username);
            Intent intent_names = new Intent();

            intent_names.setClass(context.getApplicationContext(), Home.class);
           intent_names.putExtra("username",username);
           intent_names.putExtra("activity","log in");
            context.startActivity(intent_names);




        } else if (s.contains("registerd1")) {

            context.startActivity(intt);
        }
        else if (s.contains("registerd2")) {

            context.startActivity(intt2);
        }
        else if (s.contains("registerd3")) {
            SaveSharedPreference.setUserName(context.getApplicationContext(),username);
            SaveSharedPreference.setPass(context.getApplicationContext(),pass);
          //  sharedPref.setLastUserName(context.getApplicationContext(), username);
            intt3.setClass(context.getApplicationContext(), Home.class);

            intt3.putExtra("username",username);
            intt3.putExtra("activity","sign up");

            context.startActivity(intt3);

        }
        else if (s.contains("MedAdded")) {
            intent_namme.setClass(context.getApplicationContext(), Home.class);

            intent_namme.putExtra("username",username);
            intent_namme.putExtra("activity","med add");

            context.startActivity(intent_namme);

        }
        else if(s.contains("Docadded")){


           intent_nammmee.setClass(context.getApplicationContext(), Home.class);
            intent_nammmee.putExtra("activity","doc add");

            intent_nammmee.putExtra("username",username);
           context.startActivity(intent_nammmee);

        }
        else if(s.contains("mealadded")){


        }

        else if (!(s.contains("NOTfound"))) {

            try {


               // context.startActivity(intent_m);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }  else if (!(s.contains("NOMEDS"))) {

            try {

                context.startActivity(intent_n);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }




    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String method = voids[0];

        // 192.168.1.107
        String loginURL = URL+"login.php";
        String SignUpURL = URL+"signup.php";
        String NewMedURL = URL+"newmed.php";
        String confmedURL = URL+"confmed.php";
        String adm = URL + "addmeal.php";
        String addd=URL+"adddoc.php";
        if (method.equals("login")) {
            try {
                username = voids[1];
                pass = voids[2];
                URL url = new URL(loginURL);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
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
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }



        } else if (method.equals("signup")) {
            String type = voids[1];
                    if(type.equals("1")) {
                        try {

                            username = voids[2];
                            String pass = voids[3];
                            String email = voids[4];
                            String phone = voids[5];


                            URL url = new URL(SignUpURL);
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            http.setRequestMethod("POST");
                            http.setDoInput(true);
                            http.setDoOutput(true);

                            OutputStream ops = http.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                                    + "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")
                                    + "&&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                                    + "&&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                                    + "&&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");


                            writer.write(data);
                            writer.flush();
                            writer.close();
                            ops.close();

                            InputStream ips = http.getInputStream();
                            /*   ips.close();*/


                            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                result += line;
                            }
                            reader.close();
                            ips.close();
                            http.disconnect();
                            intt.setClass(context.getApplicationContext(), contSignUp.class);
                                intt.putExtra("username",username);
                            return result;


                        } catch (MalformedURLException e) {
                            result = e.getMessage();
                        } catch (IOException e) {
                            result = e.getMessage();
                        }
                    }
                    else if (type.equals("2")){
                        try {

                            String age = voids[2];
                            String gend = voids[3];
                            String user=voids[4];
                            String vac=voids[5];
                            String dateOfBirth=voids[6];
                            String datL=voids[7];

                            URL url = new URL(SignUpURL);
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            http.setRequestMethod("POST");
                            http.setDoInput(true);
                            http.setDoOutput(true);

                            OutputStream ops = http.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                            String data = URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")
                                    + "&&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gend, "UTF-8")
                                    + "&&" +  URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                            + "&&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                                    + "&&" + URLEncoder.encode("vaccine", "UTF-8") + "=" + URLEncoder.encode(vac, "UTF-8")
                            + "&&" + URLEncoder.encode("dateOfBirth", "UTF-8") + "=" + URLEncoder.encode(dateOfBirth, "UTF-8")
                            + "&&" + URLEncoder.encode("dateLong", "UTF-8") + "=" + URLEncoder.encode(datL, "UTF-8");

                            writer.write(data);
                            writer.flush();
                            writer.close();
                            ops.close();

                            InputStream ips = http.getInputStream();
                            /*   ips.close();*/


                            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                result += line;
                            }
                            reader.close();
                            ips.close();
                            http.disconnect();
                            intt2.setClass(context.getApplicationContext(), contchronsignup.class);
                            intt2.putExtra("username",user);
                            return result;


                        } catch (MalformedURLException e) {
                            result = e.getMessage();
                        } catch (IOException e) {
                            result = e.getMessage();
                        }
                    }
                    else if (type.equals("3")){
                        try {

                            String cds = voids[2];
                            String bloodType = voids[3];
                            String user = voids [4];

                            URL url = new URL(SignUpURL);
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            http.setRequestMethod("POST");
                            http.setDoInput(true);
                            http.setDoOutput(true);

                            OutputStream ops = http.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                            String data = URLEncoder.encode("cds", "UTF-8") + "=" + URLEncoder.encode(cds, "UTF-8")
                                    + "&&" + URLEncoder.encode("bloodType", "UTF-8") + "=" + URLEncoder.encode(bloodType, "UTF-8")
                                    + "&&" +  URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")

                                    + "&&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");

                            writer.write(data);
                            writer.flush();
                            writer.close();
                            ops.close();

                            InputStream ips = http.getInputStream();
                            /*   ips.close();*/


                            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                            String line = "";
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
                    }else if (type.equals("5")){
                        try {

                            String age = voids[2];
                            String gend = voids[3];
                            String user=voids[4];
                            String vac=voids[5];
                            URL url = new URL(SignUpURL);
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            http.setRequestMethod("POST");
                            http.setDoInput(true);
                            http.setDoOutput(true);

                            OutputStream ops = http.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                            String data = URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")
                                    + "&&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gend, "UTF-8")
                                    + "&&" +  URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                                    + "&&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                                    + "&&" + URLEncoder.encode("vaccine", "UTF-8") + "=" + URLEncoder.encode(vac, "UTF-8");

                            writer.write(data);
                            writer.flush();
                            writer.close();
                            ops.close();

                            InputStream ips = http.getInputStream();
                            /*   ips.close();*/


                            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                result += line;
                            }
                            reader.close();
                            ips.close();
                            http.disconnect();
                            intt2.setClass(context.getApplicationContext(), contchronsignup.class);
                            intt2.putExtra("username",user);
                            return result;


                        } catch (MalformedURLException e) {
                            result = e.getMessage();
                        } catch (IOException e) {
                            result = e.getMessage();
                        }
                    }


        } else if (method.equals("newmed")) {
            String barcode=voids[1];
            String line = null;

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {

                URL url = new URL(URL+"newmed.php?barcode="+barcode);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                is = urlConnection.getInputStream();

            } catch (Exception e) {

                Log.e("Fail 1", e.toString());

            }


            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                result = sb.toString();

            } catch (Exception e) {

                Log.e("Fail 2", e.toString());

            }


            try {
                JSONArray JA = new JSONArray(result);

                JSONObject json = null;

                meds = new String[JA.length()];

                for (int i = 0; i < JA.length(); i++) {
                    String nam;
                    String typ;
                    String nop;


                    json = JA.getJSONObject(i);

                    nam = json.getString("name");
                    typ = json.getString("type");
                    nop = json.getString("numOfPills");
                    fam=json.getString("family");





                    intent_m.setClass(context.getApplicationContext(), newMed.class);

                    intent_m.putExtra("nname", nam);
                    intent_m.putExtra("ttype", typ);
                    intent_m.putExtra("nnop", nop);
                    intent_m.putExtra("family",fam);

                    String s1="عدد مرات اخذ الدواء اسبوعياً";
                    intent_m.putExtra("s1", s1);

                    context.startActivity(intent_m);



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("confmed")){
            try {
                String medname = voids[1];
                String medtype = voids[2];
                String mednop = voids[3];
                String meddoses = voids[4];
                String meddays = voids[5];
                String typeday = voids[6];
                String timeStamp=voids[7];
                 family=voids[8];
                 String us=voids[9];
                String intervalDate=voids[10];

                URL url = new URL(confmedURL);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(us, "UTF-8")
                        + "&&" + URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(medname, "UTF-8")
                        + "&&" + URLEncoder.encode("medtype", "UTF-8") + "=" + URLEncoder.encode(medtype, "UTF-8")
                        + "&&" + URLEncoder.encode("mednop", "UTF-8") + "=" + URLEncoder.encode(mednop, "UTF-8")
                        + "&&" + URLEncoder.encode("meddoses", "UTF-8") + "=" + URLEncoder.encode(meddoses, "UTF-8")
                        + "&&" + URLEncoder.encode("meddays", "UTF-8") + "=" + URLEncoder.encode(meddays, "UTF-8")
                        + "&&" + URLEncoder.encode("typeday", "UTF-8") + "=" + URLEncoder.encode(typeday, "UTF-8")
                        + "&&" + URLEncoder.encode("timeStamp", "UTF-8") + "=" + URLEncoder.encode(timeStamp, "UTF-8")
                        + "&&" + URLEncoder.encode("family", "UTF-8") + "=" + URLEncoder.encode(family, "UTF-8")
                        + "&&" + URLEncoder.encode("intervalDate", "UTF-8") + "=" + URLEncoder.encode(intervalDate, "UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();
                /*
                String linne=medname + "-" + medtype + "-" +mednop;
                intent_namme.putExtra("line",linne);
                */
                InputStream ips = http.getInputStream();
                /*   ips.close();*/


                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();


                intent_namme.setClass(context.getApplicationContext(), HomeFragment.class);
                intent_namme.putExtra("username",username);


                return result;


            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }


        }
        else if(method.equals("showmeds")){
            String line = null;

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            try {

                URL url = new URL(URL+"showmeds.php?username="+username);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                is = urlConnection.getInputStream();

            } catch (Exception e) {

                Log.e("Fail 1", e.toString());

            }


            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                result = sb.toString();

            } catch (Exception e) {

                Log.e("Fail 2", e.toString());

            }


            try {
                JSONArray JA = new JSONArray(result);

                JSONObject json = null;

                meds = new String[JA.length()];

                for (int i = 0; i < JA.length(); i++) {
                    String medn;
                    String medt;
                    String medp;

                    json = JA.getJSONObject(i);

                    medn = json.getString("medname");
                    medt = json.getString("medtype");
                    medp = json.getString("mednop");

                    intent_n.setClass(context.getApplicationContext(), mymeds.class);
                    String linn=medn + "-" + medt + "-" +medp;
                    //   liness.add(line);
                    intent_n.putExtra("linn",linn);
                    context.startActivity(intent_n);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("adddoc")){
             doctorname = voids[1];
             doctorspec = voids[2];
             doctordate = voids[3];
            m=voids[4];
            username = voids[5];

            try{
            java.net.URL url = new URL(addd);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                    + "&&" +   URLEncoder.encode("docname", "UTF-8") + "=" + URLEncoder.encode(doctorname, "UTF-8")
                    + "&&" + URLEncoder.encode("docspec", "UTF-8") + "=" + URLEncoder.encode(doctorspec, "UTF-8")
                    + "&&" + URLEncoder.encode("docdate", "UTF-8") + "=" + URLEncoder.encode(doctordate, "UTF-8")
                    + "&&" + URLEncoder.encode("m", "UTF-8") + "=" + URLEncoder.encode(m, "UTF-8");



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

            return result;



        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;

        } else if(method.equals("addmeal")){
            String medname = voids[1];
            String mname = voids[2];
            String timetype = voids[3];
            String timeStt=voids[4];
            String tik=voids[5];
            String tik1=voids[6];
            String typeofrep=voids[7];
            String mty=voids[8];
            username = voids[9];


            try{
                java.net.URL url = new URL(adm);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&&" +   URLEncoder.encode("medname", "UTF-8") + "=" + URLEncoder.encode(medname, "UTF-8")
                        + "&&" + URLEncoder.encode("mname", "UTF-8") + "=" + URLEncoder.encode(mname, "UTF-8")
                        + "&&" + URLEncoder.encode("timetype", "UTF-8") + "=" + URLEncoder.encode(timetype, "UTF-8")
                        + "&&" + URLEncoder.encode("timeStamp", "UTF-8") + "=" + URLEncoder.encode(timeStt, "UTF-8")
                        + "&&" + URLEncoder.encode("typeofrep", "UTF-8") + "=" + URLEncoder.encode(typeofrep, "UTF-8")

                        + "&&" + URLEncoder.encode("mealtimestring", "UTF-8") + "=" + URLEncoder.encode(mty, "UTF-8");



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


                return result;



            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;

        }
        else if(method.equals("adddoc")){
            doctorname = voids[1];
            doctorspec = voids[2];
            doctordate = voids[3];
            m=voids[4];
            username = voids[5];

            try{
                java.net.URL url = new URL(addd);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&&" +   URLEncoder.encode("docname", "UTF-8") + "=" + URLEncoder.encode(doctorname, "UTF-8")
                        + "&&" + URLEncoder.encode("docspec", "UTF-8") + "=" + URLEncoder.encode(doctorspec, "UTF-8")
                        + "&&" + URLEncoder.encode("docdate", "UTF-8") + "=" + URLEncoder.encode(doctordate, "UTF-8")
                        + "&&" + URLEncoder.encode("m", "UTF-8") + "=" + URLEncoder.encode(m, "UTF-8");



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

                intent_nammmee.setClass(context.getApplicationContext(), Home.class);
                intent_nammmee.putExtra("username",username);
                intent_nammmee.putExtra("activity","doc add");
                context.startActivity(intent_nammmee);

                return result;



            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
            return result;

        }
        return result;


    }
}
