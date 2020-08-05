package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class newMed extends AppCompatActivity implements dosd.dd,intervalD.intervalDd {

    public TextView mednamee;
    public TextView medtypee;
    public TextView mednopp;
    private static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public Button confirm;
    public static String name;
    public static String type;
    public static String nop;
    public String namee;
    public String typee;
    public int x=0;
    public String nopp;
    public DialogFragment inter;
    String s1;
    public  TextView weekly;
    public TextView meals;
    public TextView nopppp;
    public Button btn;
    public TextView ttt;
    public String text;
    String a;
    public String doses;
    public String thetime;
    public String wek, t;
    Intent intent;
    String day,month,year;
    String tik;
    String user;
    private int mY, mM, mH, mMin, mD;
    long selectedTimestamp;

    String doss;
    String s2;
    String dos2;
    String dos32, dos33;
    String dos42, dos43, dos44;
    public TextView endint;
    static String intervalDate;
    String interval;
    public TextView trial;
    public String family;
    String[] numOfPills = { "عدد الجرعات", "1","2","3","4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_med);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerNOP);

        mySpinner.setAdapter(new MyAdapter(newMed.this, R.layout.activity_new_med,
                numOfPills));

        weekly=(TextView) findViewById(R.id.weeklyy);
        mednamee=findViewById(R.id.medname);
        medtypee=findViewById(R.id.medtype);
        mednopp=findViewById(R.id.mednum);
        endint=findViewById(R.id.endint);

         intent=getIntent();

        namee=intent.getStringExtra("nname");
        typee=intent.getStringExtra("ttype");
        nopp=intent.getStringExtra("nnop");
        user=intent.getStringExtra("username");
        user= SaveSharedPreference.getUserName(getApplicationContext());
        family=intent.getStringExtra("family");

        name=namee;
        type=typee;
        nop=nopp;
        mednamee.setText(name);
        medtypee.setText(type);
        mednopp.setText(nop);


        tik=intent.getStringExtra("s1");

        confirm=(Button) findViewById(R.id.btnToHome);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });



        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });
        weekly.setText(tik);

        meals=(TextView) findViewById(R.id.mealss);

        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekly.getText().toString().equals("عدد مرات اخذ الدواء اسبوعياً")) {
                    Toast.makeText(getApplicationContext(),"من فضلك أدخل موعد أخذ الدواء",Toast.LENGTH_LONG).show();

                } else{
                    Intent intennt = new Intent(newMed.this, mealsActivity.class);
                intennt.putExtra("medname", name);
                intennt.putExtra("ss", s1);
                intennt.putExtra("s2", s2);
                intennt.putExtra("username", user);
                intennt.putExtra("typeofrep", t);
                startActivity(intennt);
            }
            }
        });




        s1=intent.getStringExtra("s1");
        s2=intent.getStringExtra("s2");
        t=intent.getStringExtra("type");




        weekly.setText(s1);


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                doses = parentView.getItemAtPosition(position).toString();


         }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        endint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 inter= new intervalD();
                inter.setCancelable(true);
                inter.show(getSupportFragmentManager(),"interval Choice Dialog");
            }
        });


  //  }
    }
    public void set(String s){
        mednamee.setText(s);
    }

    @Override
    public void on1Clicked() {

    }

    @Override
    public void on2Clicked() {

    }

    @Override
    public void on3Clicked() {

    }

    @Override
    public void on4Clicked() {

    }

    @Override
    public void on5Clicked() {

    }

    @Override
    public void on6Clicked() {

    }




    @Override
    public void onEnd() {
        intervalDate="onEnd";
        x=1;
        inter.dismiss();
    }

    @Override
    public void onDate() {
        x=1;
        inter.dismiss();
        Toast.makeText(getApplicationContext(),"ادخل تاريخ الانتهاء من اخذ الدواء",Toast.LENGTH_LONG).show();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    // Creating an Adapter Class
    public class MyAdapter extends ArrayAdapter {

        public MyAdapter(Context context, int textViewResourceId,
                         String[] objects) {
            super(context, textViewResourceId, objects);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

// Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.cusnop, parent, false);

// Declaring and Typecasting the textview in the inflated layout
            nopppp= (TextView) layout
                    .findViewById(R.id.nop);

// Setting the text using the array
            nopppp.setText(numOfPills[position]);

// Setting the color of the text
            nopppp.setTextColor(Color.rgb(75, 180, 225));


// Setting Special atrributes for 1st element
            if (position == 0) {

// Setting the size of the text
                nopppp.setTextSize(20f);
// Setting the text Color
                nopppp.setTextColor(Color.BLACK);


            }

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
        public String onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             text = parent.getItemAtPosition(position).toString();

                return text;
        }
    }


    private void goTo(){
        wek=weekly.getText().toString();
        if(!((doses.equals("1"))||(doses.equals("2"))||(doses.equals("3"))||(doses.equals("4")))){
            Toast.makeText(getApplicationContext(),"من فضلك أدخل عدد الجرعات",Toast.LENGTH_LONG).show();
        }
        else if (weekly.getText().toString().equals("عدد مرات اخذ الدواء اسبوعياً")) {
            Toast.makeText(getApplicationContext(),"من فضلك أدخل موعد أخذ الدواء",Toast.LENGTH_LONG).show();

        }
        else if(x==0){
            Toast.makeText(getApplicationContext(),"من فضلك أدخل موعد انتهاء أخذ الدواء",Toast.LENGTH_LONG).show();
        }
        else {
            String method = "confmed";
            background bg = new background(this);
            bg.execute(method, name, type, nop, doses, wek, t, s2,family,user,intervalDate);
        }

    }
    private void goTo2(){

        Intent intent=new Intent(newMed.this, weekk.class);

        intent.putExtra("medname",name);
        intent.putExtra("medtype",type);
        intent.putExtra("mednop",nop);
        intent.putExtra("family",family);

        intent.putExtra("meddoses",doses);


        startActivity(intent);
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
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            long datte=c.getTimeInMillis();
            return  dialog;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            intervalDate=year+"-"+month+"-"+dayOfMonth;
            Calendar cs = Calendar.getInstance();
             cs.set(Calendar.YEAR,year);
            cs.set(Calendar.MONTH,month);
             cs.set(Calendar.DAY_OF_MONTH,dayOfMonth);
             Date d=cs.getTime();
            intervalDate= dateFormatMonth.format(d);



        }
    }

}
