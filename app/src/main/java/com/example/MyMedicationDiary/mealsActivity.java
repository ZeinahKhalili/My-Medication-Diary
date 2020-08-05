package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class mealsActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener, laydialog.laydialogg,laydialog2.laydialogg2{
    public TextView mealltime;
    public TextView mealtype;
    public Button bselect;
    public Button bsure;
    public String medname;
    public String mname;
    String username;

        public String timetype;
        public String timeStampdone;
    String timeStamp;
    String typeofrep;
    Long timeStampT;
    String dates;
        Dialog d1;
    DialogFragment dialogFragment=new laydialog();
    DialogFragment dialogFragment2=new laydialog2();

        Button bf,ba,bd;
    public Long s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);


        mealltime = (TextView) findViewById(R.id.mealtime);
        mealtype = (TextView) findViewById(R.id.textmealname);
        bselect = (Button) findViewById(R.id.btnsm);
        bsure = (Button) findViewById(R.id.btnsure);

        mealltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.setCancelable(false);
                dialogFragment.show(getSupportFragmentManager(),"hi");


            }
        });


        Intent t=getIntent();
        medname=t.getStringExtra("medname");
        timeStamp=t.getStringExtra("s2");
        dates=t.getStringExtra("ss");
        typeofrep=t.getStringExtra("typeofrep");
        username=t.getStringExtra("username");
        timeStampT=Long.parseLong(timeStamp);

        bselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog= new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(true);
                singleChoiceDialog.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });



       /* bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        bsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });


    }

    private void goTo() {
        mname=mealtype.getText().toString();
        String mty=mealltime.getText().toString();
        if(mname.equals("")){
            Toast.makeText(getApplicationContext(),"من فضلك اختر اسم الوجبة",Toast.LENGTH_SHORT).show();

        }
        else if(mty.equals("وقت الوجبة")){
            Toast.makeText(getApplicationContext(),"من فضلك ادخل وقت الوجبة",Toast.LENGTH_SHORT).show();

        }
        else {
            String method = "addmeal";
            background bg = new background(this);
            bg.execute(method, medname, mname, timetype, timeStampdone, dates, timeStamp, typeofrep, mty,username);
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        mealtype.setText(list[position]);
       }

    @Override
    public void onNegativeButtonClicked() {
        mealtype.setText("لم يتم اختيار الوجبة");
    }


    @Override
    public void onafterClicked() {
        Toast.makeText(getApplicationContext(),"بعد",Toast.LENGTH_SHORT).show();
        mealltime.setText("");
        mealltime.append(" بعد الدواء ");
        timetype="after";
        dialogFragment.dismiss();
        dialogFragment2.setCancelable(false);
        dialogFragment2.show(getSupportFragmentManager(),"hii");
    }

    @Override
    public void onbefClicked() {
        Toast.makeText(getApplicationContext(),"قبل",Toast.LENGTH_SHORT).show();
        mealltime.setText("");

        mealltime.append(" قبل الدواء ");

        timetype="before";
        dialogFragment.dismiss();
        dialogFragment2.setCancelable(false);
        dialogFragment2.show(getSupportFragmentManager(),"hii");
    }

    @Override
    public void ondurClicked() {
        Toast.makeText(getApplicationContext(),"اثناء",Toast.LENGTH_SHORT).show();
        mealltime.setText("");

        mealltime.append(" مع الدواء ");

        timetype="during";
        dialogFragment.dismiss();
        timeStampdone=timeStamp;
    }

    @Override
    public void on1Clicked() {
        Toast.makeText(getApplicationContext(),"ساعه",Toast.LENGTH_SHORT).show();
        mealltime.append("بساعة ");

        if(timetype.equals("after")) {
            timeStampT += 180000;
        }
        else if(timetype.equals("before")){
            timeStampT -= 3600000;
        }
        timeStampdone=Long.toString(timeStampT);

        dialogFragment2.dismiss();

    }

    @Override
    public void on2Clicked() {
        Toast.makeText(getApplicationContext(),"ساعتان",Toast.LENGTH_SHORT).show();
        mealltime.append("بساعتين ");

        if(timetype.equals("after")) {
            timeStampT+=7200000;
        }
        else if(timetype.equals("before")){
            timeStampT-=7200000;

        }
        timeStampdone=Long.toString(timeStampT);
        dialogFragment2.dismiss();

    }

    @Override
    public void on3Clicked() {
        Toast.makeText(getApplicationContext(),"ثلاثة ساعات",Toast.LENGTH_SHORT).show();
        mealltime.append("بثلاث ساعات ");

        if(timetype.equals("after")) {
            timeStampT+=10800000;
        }
        else if(timetype.equals("before")){
            timeStampT-=10800000;

        }
        timeStampdone=Long.toString(timeStampT);
        dialogFragment2.dismiss();

    }

    @Override
    public void on4Clicked() {
        Toast.makeText(getApplicationContext(),"أربعة ساعات",Toast.LENGTH_SHORT).show();
        mealltime.append("بأربع ساعات ");

        if(timetype.equals("after")) {
            timeStampT+=14400000;
        }
        else if(timetype.equals("before")){
            timeStampT-=14400000;

        }
        timeStampdone=Long.toString(timeStampT);
        dialogFragment2.dismiss();

    }

    @Override
    public void on5Clicked() {
        Toast.makeText(getApplicationContext(),"خمس ساعات",Toast.LENGTH_SHORT).show();
        mealltime.append("بخمس ساعات ");

        if(timetype.equals("after")) {
            timeStampT+=18000000;
        }
        else if(timetype.equals("before")){
            timeStampT-=18000000;

        }
        timeStampdone=Long.toString(timeStampT);
        dialogFragment2.dismiss();

    }

    @Override
    public void on6Clicked() {
        Toast.makeText(getApplicationContext(),"ست ساعات",Toast.LENGTH_SHORT).show();
        mealltime.append("بستة ساعات ");

        if(timetype.equals("after")) {
            timeStampT+=21600000;
        }
        else if(timetype.equals("before")){
            timeStampT-=21600000;

        }
        timeStampdone=Long.toString(timeStampT);
        dialogFragment2.dismiss();

    }
}
