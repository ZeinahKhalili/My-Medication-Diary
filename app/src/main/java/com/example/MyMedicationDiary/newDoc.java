package com.example.MyMedicationDiary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;

public class newDoc extends AppCompatActivity implements SingleChoiceDialogFragment3.SingleChoiceListener3 {

    public TextView doccdatee;
    public TextView docctimee;
    public EditText docname;
    public EditText docspec;
    public Button adddoc;
    public Button gb;
    public Button extra;

    public String    s;

    public Context context;
    public AlertDialog dialog;

    public String doctorname,doctorspec,doctordate,doctortime;

    public String user;

    public  Calendar newCalender;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;
    public String m;
    public long selectedTimestamp;


        public Long t;

    public    ArrayList <Long> arrayListL;
    public    ArrayList <String> arrayListSp;
    public    ArrayList <String> arrayListN;
    public String appn;
    public String appss;
    public String appd;
    public String appm;
    public Long timee;

    public  int flag=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doc);


        doccdatee = (TextView) findViewById(R.id.docdate);
        docname=(EditText) findViewById(R.id.docname);
        docspec=(EditText) findViewById(R.id.docspec);
        adddoc=(Button) findViewById(R.id.btnadddoc);

        Intent t=getIntent();
        s=t.getStringExtra("username");
        s=SaveSharedPreference.getUserName(getApplicationContext());


        newCalender = Calendar.getInstance();
        doccdatee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(newDoc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(newDoc.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);

                                Calendar tem = Calendar.getInstance();

                                Log.w("TIME",System.currentTimeMillis()+"");

                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0) {

                                    doccdatee.setText(newDate.getTime().toString());

                                    mHour = newDate.get(Calendar.HOUR_OF_DAY);
                                    mMinute = newDate.get(Calendar.MINUTE);
                                    mYear = newDate.get(Calendar.YEAR);
                                    mMonth = newDate.get(Calendar.MONTH) + 1;
                                    mDay = newDate.get(Calendar.DATE);

                                    mDate = mDay + "/" + mMonth + "/" + mYear;
                                    mTime = mHour + ":" + mMinute;

                                    newDate.set(Calendar.MONTH, --mMonth);
                                    newDate.set(Calendar.YEAR, mYear);
                                    newDate.set(Calendar.DAY_OF_MONTH, mDay);
                                    newDate.set(Calendar.HOUR_OF_DAY, mHour);
                                    newDate.set(Calendar.MINUTE, mMinute);
                                    newDate.set(Calendar.SECOND, 0);
                                    selectedTimestamp =  newDate.getTimeInMillis();

                                    m=Long.toString(selectedTimestamp);

                                }
                                else

                                    Toast.makeText(newDoc.this,"Invalid time",Toast.LENGTH_SHORT).show();



                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });




        adddoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                goTo();
                //  new Connection().execute();
            }
        });
        docspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog= new SingleChoiceDialogFragment3();
                singleChoiceDialog.setCancelable(true);
                singleChoiceDialog.show(getSupportFragmentManager(),"Single Choice Dialog");
            }
        });



    }

    private void goTo() {
        String method="adddoc";
        doctorname=docname.getText().toString().trim();
        doctorspec=docspec.getText().toString().trim();
        doctordate=doccdatee.getText().toString().trim();
        if (doctorname.equals("")||doctorspec.equals("")||doctordate.equals("")){
            Toast.makeText(getApplicationContext(),"من فضلك ادخل كافة المعلومات اعلاه",Toast.LENGTH_SHORT).show();

        }else {

            background bg = new background(this);
            bg.execute(method, doctorname, doctorspec, doctordate, m,s);
        }

    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        docspec.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
