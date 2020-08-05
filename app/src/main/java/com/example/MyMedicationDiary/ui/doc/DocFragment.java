package com.example.MyMedicationDiary.ui.doc;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.MyMedicationDiary.Constants;
import com.example.MyMedicationDiary.ListAdapter2;
import com.example.MyMedicationDiary.R;
import com.example.MyMedicationDiary.SingleChoiceDialogFragment3;
import com.example.MyMedicationDiary.Subject2;
import com.example.MyMedicationDiary.newDoc;
import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class DocFragment extends Fragment implements SingleChoiceDialogFragment3.SingleChoiceListener3 {


    String URL= Constants.UU.URL;



    private FloatingActionButton addDocs;
    public String userr;
    private DocViewModel DocViewModel;

    ListView listView;
    ArrayAdapter<String> adapter;
    public String appn;
    public String appss;
    public String appd;
    public String appm;
    public ListAdapter2 myadapt;

    public String nameD;
    public String docn,docsp,docd;
    public Dialog dialoog,dialog22,dialog3,dialogs1;
    public TextView dn,ds,dd;
    public String naame,spec,date;
    public Long timee,timee2,timee3;

    public    ArrayList <Long> arrayListL=new ArrayList<>();
    public    ArrayList <String> arrayListSp=new ArrayList<>();
    public    ArrayList <String> arrayListN=new ArrayList<>();
    public    ArrayList<PendingIntent> pi=new ArrayList<>();


    int c=0;


    public  int flag=1;
    public  int flag2=1;
    public int sizze,sizze2;

    public Button bd1,bd2,bdd1,bdd2,btnn2d,ban,ban2;
    public String timm;
    public long times;
    public TextView tr,tr2;

    public Button ex;
    public String sst,sstt;



    public  Calendar newCalender;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;
    public String m;
    public long selectedTimestamp;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DocViewModel =
                ViewModelProviders.of(this).get(DocViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doc, container, false);
        final TextView textView = root.findViewById(R.id.text_doc);
        DocViewModel.getText().observe(this, new Observer<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                addDocs=(FloatingActionButton) getView().findViewById(R.id.floatingActionButtonDOCTORS);

                userr=getActivity().getIntent().getExtras().getString("username");
                userr= SaveSharedPreference.getUserName(getContext());

                addDocs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), newDoc.class);
                        intent.putExtra("username",userr);
                        startActivity(intent);

                    }
                });

                listView=(ListView) getView().findViewById(R.id.applist);


                //  adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1);
                //   listView.setAdapter(adapter);


                dialoog = new Dialog(getContext());

                dialoog.setContentView(R.layout.floating_popupd);
                dn=dialoog.findViewById(R.id.dn);
                ds=dialoog.findViewById(R.id.ds);
                dd=dialoog.findViewById(R.id.dt);
                ex=dialoog.findViewById(R.id.btnn3D);


                btnn2d=dialoog.findViewById(R.id.btnn2D);

                dialog22=new Dialog(getContext());
                dialog22.setContentView(R.layout.floating_popup2d);
                tr=dialog22.findViewById(R.id.rrr);
                bd1=dialog22.findViewById(R.id.bsd);
                bd2=dialog22.findViewById(R.id.bnd);


                dialog3=new Dialog(getContext());
                dialog3.setContentView(R.layout.floating_popup3d);
                tr2=dialog3.findViewById(R.id.rrrd);
                bdd1=dialog3.findViewById(R.id.bsdd);
                bdd2=dialog3.findViewById(R.id.bndd);
                dn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog22.show();



                    }
                });
                ds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      /*  DialogFragment singleChoiceDialog= new SingleChoiceDialogFragment3();
                        singleChoiceDialog.setCancelable(true);
                        singleChoiceDialog.show(getActivity().getSupportFragmentManager(),"Single Choice Dialog");*/
                        dialog3.show();


                    }
                });
                bd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog22.dismiss();



                    }
                });
                bd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sst=tr.getText().toString();
                        dn.setText("اسم الطبيب: "+sst);
                        dialog22.dismiss();
                    }
                });
                bdd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();



                    }
                });
                bdd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sstt=tr2.getText().toString();
                        ds.setText("تخصص الطبيب: "+sstt);
                        dialog3.dismiss();
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position>=0) {

                            TextView textView = (TextView) view.findViewById(R.id.textViewNa);
                            String text = textView.getText().toString();
                            String t[] = text.split(":");
                            String sss=t[1].trim();
                            nameD=sss;

                            new ConnectionD2().execute();
                        }

                    }
                });
                tr2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                btnn2d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogs1=new Dialog(getContext());
                        dialogs1.setContentView(R.layout.floating_popup22d);
                        dialogs1.show();
                        ban=dialogs1.findViewById(R.id.btnnnD);
                        ban.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Connection3DD().execute();
                            }
                        });
                        ban2=dialogs1.findViewById(R.id.btnnn2D);
                        ban2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogs1.dismiss();
                            }
                        });
                    }
                });
                ex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialoog.dismiss();
                    }
                });
                dd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });




            if (flag==1) {
                new ConnectionD().execute();
                flag = 0;
            }



            }
        });
        return root;
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        ds.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }


    public class ConnectionD extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result="";
            String host=URL+"showapp.php?username="+userr;

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
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute( String result ){
            try {

                JSONObject jsonResult = new JSONObject(result);

                List<Subject2> SubjectFullFormList;

                Subject2 subject;
                SubjectFullFormList = new ArrayList<Subject2>();
                int success=jsonResult.getInt("success");
                if(success==1){
                    JSONArray apps=jsonResult.getJSONArray("apps");
                    for (int i = 0; i < apps.length(); i++) {
                        JSONObject app=apps.getJSONObject(i);
                        subject = new Subject2();



                        appn = app.getString("docname");
                        appss = app.getString("docspec");
                        appd = app.getString("docdate");

                        appm=app.getString("timeStamp");



                        timee=Long.parseLong(appm);

                        arrayListL.add(timee);
                        arrayListSp.add(appss);
                        arrayListN.add(appn);


                        subject.Subject_Namee=appn;
                        subject.Subject_Spec=appss;
                        subject.Subject_DT=appd;

                        listView.setVisibility(View.VISIBLE);

                        SubjectFullFormList.add(subject);

                        myadapt=new ListAdapter2(SubjectFullFormList,getContext());

                        listView.setAdapter(myadapt);

                    }
                    if(flag2==1){

                    for (int j = 0; j < arrayListL.size(); j++) {
                       if(arrayListL.get(j)>=System.currentTimeMillis()) {
                           Intent itAlarm = new Intent("ALARM");

                           itAlarm.putExtra("appn", arrayListN.get(j));
                           itAlarm.putExtra("appss", arrayListSp.get(j));
                           itAlarm.putExtra("user", userr);

                           PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), j, itAlarm, PendingIntent.FLAG_ONE_SHOT);

                           AlarmManager alarme = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                               assert alarme != null;
                               alarme.setExact(AlarmManager.RTC_WAKEUP, arrayListL.get(j), pendingIntent);

                           }
                           flag2=0;
                       }


                        }


                    }
                   /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(DocFragment.this).attach(DocFragment.this).commit();
*/

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ConnectionD2 extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "viewapps.php?username=" + userr+"&docname="+nameD;

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
                    JSONArray docs = jsonResult.getJSONArray("docs");
                    for (int i = 0; i < docs.length(); i++) {
                        JSONObject doc = docs.getJSONObject(i);

                        docn = doc.getString("docname");
                        docsp = doc.getString("docspec");
                        docd = doc.getString("docdate");


                        dn.setText("اسم الطبيب:"+docn);
                        ds.setText("تخصص الطبيب:"+docsp);
                        dd.setText("موعد الطبيب:"+docd);

                        dialoog.show();


/*

                        String text11 = dn.getText().toString();
                        String t1[] = text11.split(":");
                        naame=t1[1];

                        String text21 = ds.getText().toString();
                        String t2[] = text21.split(":");
                        spec=t2[1];

                        String text31 = dd.getText().toString();
                        String t3[] = text31.split(":ة");
                        date=t3[1];

*/


                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class Connection3DD extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "appdelete.php?username=" + userr+"&docname="+nameD;

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

            dialogs1.hide();
            dialoog.hide();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(DocFragment.this).attach(DocFragment.this).commit();

        }

    }

}
