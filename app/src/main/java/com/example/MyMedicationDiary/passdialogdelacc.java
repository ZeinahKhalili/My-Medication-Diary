package com.example.MyMedicationDiary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;

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

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.MyMedicationDiary.Constants.UU.URL;

public class passdialogdelacc extends DialogFragment {
    EditText pass;
    Button sure,canc;
    TextView pls;
    String passnew,phonenew;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.passdialog2,container,false);
        pass=view.findViewById(R.id.oldpassdi);
        sure=view.findViewById(R.id.sureoldpass);
        canc=view.findViewById(R.id.cancoldpass);

        pls=view.findViewById(R.id.textView9);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passw=pass.getText().toString();

                if (passw.equals(SaveSharedPreference.getPass(getContext()))){
                    // ((ProfileActivity)getActivity()).tv.setText(passw);
                  //  getDialog().dismiss();
                    new ConnectionDel().execute();

                }

                else if(pass.equals("")){
                    pls.setText("الرجاء إدخال كلمة المرور السابقة");
                    pls.setTextColor(Color.RED);
                }
                else{
                    pls.setText("كلمة المرور المدخلة غير صحيحة");

                    pls.setTextColor(Color.RED);
                }

            }
        });
        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();

            }
        });
        return view;
    }

    public class ConnectionDel extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "delAcc.php?username=" + ((ProfileActivity)getActivity()).user;

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

           if (result.equals("deleted")) {
                Toast.makeText(getContext(), "تم حذف الحساب بنجاح", Toast.LENGTH_LONG).show();

                Intent t=new Intent(getContext(),SecondActivity.class);
                startActivity(t);
            }
        }


    }

    }
