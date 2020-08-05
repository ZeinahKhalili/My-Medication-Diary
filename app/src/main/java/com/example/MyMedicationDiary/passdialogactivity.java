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

public class passdialogactivity extends DialogFragment {
    EditText pass;
    Button sure,canc;
    TextView pls;
    String passnew,phonenew;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.passdialog,container,false);
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
                    getDialog().dismiss();
                    if (( ((ProfileActivity) getActivity()).passTV.getText().toString().equals(""))){
                        passnew=SaveSharedPreference.getPass(getContext());

                        phonenew=((ProfileActivity)getActivity()).phoneTV.getText().toString();
                        ((ProfileActivity)getActivity()). passTV.setFocusable(false);
                        ((ProfileActivity)getActivity()). phoneTV.setFocusable(false);
                        ((ProfileActivity)getActivity()). passTV.setVisibility(View.INVISIBLE);
                        ((ProfileActivity)getActivity()). tv.setVisibility(INVISIBLE);
                        ((ProfileActivity)getActivity()). edd2.setVisibility(INVISIBLE);
                        ((ProfileActivity)getActivity()). edd.setVisibility(VISIBLE);
                        new Connection22222().execute();
                    }
                    else {
                        passnew = ((ProfileActivity) getActivity()).passTV.getText().toString();

                        phonenew = ((ProfileActivity) getActivity()).phoneTV.getText().toString();
                        ((ProfileActivity) getActivity()).passTV.setFocusable(false);
                        ((ProfileActivity) getActivity()).phoneTV.setFocusable(false);
                        ((ProfileActivity) getActivity()).passTV.setVisibility(View.INVISIBLE);
                        ((ProfileActivity) getActivity()).tv.setVisibility(INVISIBLE);
                        ((ProfileActivity) getActivity()).edd2.setVisibility(INVISIBLE);
                        ((ProfileActivity) getActivity()).edd.setVisibility(VISIBLE);
                        SaveSharedPreference.setPass(getContext(), passnew);
                        new Connection22222().execute();
                    }

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
                ((ProfileActivity)getActivity()). passTV.setFocusable(false);
                ((ProfileActivity)getActivity()). phoneTV.setFocusable(false);
                ((ProfileActivity)getActivity()). passTV.setVisibility(View.INVISIBLE);
                ((ProfileActivity)getActivity()). tv.setVisibility(INVISIBLE);
                ((ProfileActivity)getActivity()). edd2.setVisibility(INVISIBLE);
                ((ProfileActivity)getActivity()). edd.setVisibility(VISIBLE);

            }
        });
        return view;
    }



    public class Connection22222 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            String host = URL + "edittedProf.php?username=" + SaveSharedPreference.getUserName(getContext()) + "&password=" + passnew + "&phone=" + phonenew;

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
            Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
/*
            Intent t=new Intent(getContext(),SecondActivity.class);
            startActivity(t);*/
        }
    }
}
