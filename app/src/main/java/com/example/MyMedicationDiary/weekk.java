package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.MyMedicationDiary.ui.SaveSharedPreference;
import com.example.MyMedicationDiary.ui.eachdays;

import java.util.Calendar;

public class weekk extends AppCompatActivity  implements   eachdays.eachdayslis {
    //, SingleChoiceDialogFragment.SingleChoiceListener
    public TextView once1;
    public TextView daily1;
    public TextView every1;
    public TextView weeklyyy1;
    public TextView specific1;
    DialogFragment singleChoiceDialog;
    public TextView each;
    public String s1;
    public String name;
    public String ddday;
    public String type;
    public TextView dayyy;
    String period;
    public TextView sth;
    public String doses;
    public String nop;
    DatePickerDialog.OnDateSetListener setListener;
    Calendar newCalender;
    private int mY, mM, mH, mMin, mD;
    long selectedTimestamp;
    String s, finall;
    String family;
    String user;
    String typee;

    public Button confirmm;
    String[] days = {"اسبوعيا", "السبت", "الاحد", "الاثنين", "الثلاثاء", "الاربعاء", "الخميس", "الجمعة"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekk);
        getSupportActionBar().hide();
        newCalender = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        once1 = (TextView) findViewById(R.id.once);
        daily1 = (TextView) findViewById(R.id.daily);
        weeklyyy1 = (TextView) findViewById(R.id.weeklyyy);
        confirmm = (Button) findViewById(R.id.confirm);
        user= SaveSharedPreference.getUserName(getApplicationContext());

        each = findViewById(R.id.each);
        final Intent intent = getIntent();
        name = intent.getStringExtra("medname");
        type = intent.getStringExtra("medtype");
        nop = intent.getStringExtra("mednop");
        doses = intent.getStringExtra("meddoses");
        family=intent.getStringExtra("family");

        s1 = daily1.getText().toString();
        daily1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weekk.this, "اختر تاريخ البداية", Toast.LENGTH_SHORT).show();

                DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                                Calendar tem = Calendar.getInstance();

                                Log.w("TIME", System.currentTimeMillis() + "");


                                daily1.setText(newDate.getTime().toString());

                                mH = newDate.get(Calendar.HOUR_OF_DAY);
                                mMin = newDate.get(Calendar.MINUTE);
                                mY = newDate.get(Calendar.YEAR);
                                mM = newDate.get(Calendar.MONTH) + 1;
                                mD = newDate.get(Calendar.DATE);

                                newDate.set(Calendar.MONTH, --mM);
                                newDate.set(Calendar.YEAR, mY);
                                newDate.set(Calendar.DAY_OF_MONTH, mD);
                                newDate.set(Calendar.HOUR_OF_DAY, mH);
                                newDate.set(Calendar.MINUTE, mMin);
                                newDate.set(Calendar.SECOND, 0);
                                selectedTimestamp = newDate.getTimeInMillis();

                                s = Long.toString(selectedTimestamp);
                                finall = newDate.getTime().toString();
                                typee = "daily";


                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();

                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                String ax = "ختر تاريخ البداية";
                dialog.setTitle(ax);
                dialog.show();

            }

            String date = mD + "/" + mM + "/" + mY;
        });


        once1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                                Calendar tem = Calendar.getInstance();

                                Log.w("TIME", System.currentTimeMillis() + "");


                                once1.setText(newDate.getTime().toString());

                                mH = newDate.get(Calendar.HOUR_OF_DAY);
                                mMin = newDate.get(Calendar.MINUTE);
                                mY = newDate.get(Calendar.YEAR);
                                mM = newDate.get(Calendar.MONTH) + 1;
                                mD = newDate.get(Calendar.DATE);

                                newDate.set(Calendar.MONTH, --mM);
                                newDate.set(Calendar.YEAR, mY);
                                newDate.set(Calendar.DAY_OF_MONTH, mD);
                                newDate.set(Calendar.HOUR_OF_DAY, mH);
                                newDate.set(Calendar.MINUTE, mMin);
                                newDate.set(Calendar.SECOND, 0);
                                selectedTimestamp = newDate.getTimeInMillis();

                                s = Long.toString(selectedTimestamp);
                                finall = newDate.getTime().toString();
                                typee = "once";


                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();

                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }

            String date = mD + "/" + mM + "/" + mY;

        });


        weeklyyy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weekk.this, "اختر تاريخ البداية", Toast.LENGTH_SHORT).show();

                DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                                Calendar tem = Calendar.getInstance();

                                Log.w("TIME", System.currentTimeMillis() + "");


                                weeklyyy1.setText(newDate.getTime().toString());

                                mH = newDate.get(Calendar.HOUR_OF_DAY);
                                mMin = newDate.get(Calendar.MINUTE);
                                mY = newDate.get(Calendar.YEAR);
                                mM = newDate.get(Calendar.MONTH) + 1;
                                mD = newDate.get(Calendar.DATE);

                                newDate.set(Calendar.MONTH, --mM);
                                newDate.set(Calendar.YEAR, mY);
                                newDate.set(Calendar.DAY_OF_MONTH, mD);
                                newDate.set(Calendar.HOUR_OF_DAY, mH);
                                newDate.set(Calendar.MINUTE, mMin);
                                newDate.set(Calendar.SECOND, 0);
                                selectedTimestamp = newDate.getTimeInMillis();

                                s = Long.toString(selectedTimestamp);
                                finall = newDate.getTime().toString();
                                typee = "weeklly";


                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();

                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.setTitle("اختر تاريخ البداية");

                dialog.show();


            }

            String date = mD + "/" + mM + "/" + mY;

        });
        each.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 singleChoiceDialog = new eachdays();
                singleChoiceDialog.setCancelable(true);
                singleChoiceDialog.show(getSupportFragmentManager(), "each days");
            }
        });
/*
        each.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);

                                Calendar tem = Calendar.getInstance();

                                Log.w("TIME",System.currentTimeMillis()+"");


                                each.setText(newDate.getTime().toString());

                                mH = newDate.get(Calendar.HOUR_OF_DAY);
                                mMin = newDate.get(Calendar.MINUTE);
                                mY = newDate.get(Calendar.YEAR);
                                mM = newDate.get(Calendar.MONTH) + 1;
                                mD = newDate.get(Calendar.DATE);

                                newDate.set(Calendar.MONTH, --mM);
                                newDate.set(Calendar.YEAR, mY);
                                newDate.set(Calendar.DAY_OF_MONTH, mD);
                                newDate.set(Calendar.HOUR_OF_DAY, mH);
                                newDate.set(Calendar.MINUTE, mMin);
                                newDate.set(Calendar.SECOND, 0);
                                selectedTimestamp =  newDate.getTimeInMillis();

                                s=Long.toString(selectedTimestamp);
                                finall=newDate.getTime().toString();
                                typee="each x ";



                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
            String date = mD+"/"+mM+"/"+mY;

        });*/


        confirmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w=weeklyyy1.getText().toString();
                String ddd=daily1.getText().toString();
                String o=once1.getText().toString();
                String e=each.getText().toString();
                if(w.equals("أسبوعياً")&&ddd.equals("يومياً")&&o.equals("لمرة واحدة فقط")&&e.equals("كل س أيام")){
                    Toast.makeText(getApplicationContext(),"من فضلك حدد ايام أخذ الدواء",Toast.LENGTH_SHORT).show();

                }else {
                    Intent intent = new Intent(weekk.this, newMed.class);
                    intent.putExtra("family",family);
                    intent.putExtra("nname", name);
                    intent.putExtra("ttype", type);
                    intent.putExtra("nnop", nop);
                    intent.putExtra("meddoses", doses);
                    intent.putExtra("s1", finall);
                    intent.putExtra("s2", s);
                    intent.putExtra("type", typee);
                    intent.putExtra("username", user);

                    //intent.putExtra("period",period);

                    startActivity(intent);
                }
            }

        });


    }


    private void goTo() {
        Intent intent = new Intent(weekk.this, newMed.class);

        intent.putExtra("nname", name);
        intent.putExtra("s1", s1);
        intent.putExtra("ttype", type);
        intent.putExtra("nnop", nop);

        startActivity(intent);
    }


    @Override
    public void on1Clicked() {
        period = "2";
        each.setText("كل "+period+"ايام ");

        singleChoiceDialog.dismiss();
        Toast.makeText(getApplicationContext(), "اختر تاريخ البداية", Toast.LENGTH_LONG).show();
        DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                        Calendar tem = Calendar.getInstance();

                        Log.w("TIME", System.currentTimeMillis() + "");


                        each.append(newDate.getTime().toString());

                        mH = newDate.get(Calendar.HOUR_OF_DAY);
                        mMin = newDate.get(Calendar.MINUTE);
                        mY = newDate.get(Calendar.YEAR);
                        mM = newDate.get(Calendar.MONTH) + 1;
                        mD = newDate.get(Calendar.DATE);

                        newDate.set(Calendar.MONTH, --mM);
                        newDate.set(Calendar.YEAR, mY);
                        newDate.set(Calendar.DAY_OF_MONTH, mD);
                        newDate.set(Calendar.HOUR_OF_DAY, mH);
                        newDate.set(Calendar.MINUTE, mMin);
                        newDate.set(Calendar.SECOND, 0);
                        selectedTimestamp = newDate.getTimeInMillis();

                        s = Long.toString(selectedTimestamp);
                        finall = newDate.getTime().toString();
                        typee = "each2";


                    }
                }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                time.show();

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();

    }



    @Override
    public void on2Clicked() {
        period="3";
        each.setText("كل "+period+"ايام ");

        singleChoiceDialog.dismiss();

        Toast.makeText(getApplicationContext(),"اختر تاريخ البداية",Toast.LENGTH_LONG).show();
        DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                        Calendar tem = Calendar.getInstance();

                        Log.w("TIME", System.currentTimeMillis() + "");


                        each.append(newDate.getTime().toString());

                        mH = newDate.get(Calendar.HOUR_OF_DAY);
                        mMin = newDate.get(Calendar.MINUTE);
                        mY = newDate.get(Calendar.YEAR);
                        mM = newDate.get(Calendar.MONTH) + 1;
                        mD = newDate.get(Calendar.DATE);

                        newDate.set(Calendar.MONTH, --mM);
                        newDate.set(Calendar.YEAR, mY);
                        newDate.set(Calendar.DAY_OF_MONTH, mD);
                        newDate.set(Calendar.HOUR_OF_DAY, mH);
                        newDate.set(Calendar.MINUTE, mMin);
                        newDate.set(Calendar.SECOND, 0);
                        selectedTimestamp = newDate.getTimeInMillis();

                        s = Long.toString(selectedTimestamp);
                        finall = newDate.getTime().toString();
                        typee = "each3";


                    }
                }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                time.show();

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();

    }

    @Override
    public void on3Clicked() {
        period="4";
        singleChoiceDialog.dismiss();
        each.setText("كل "+period+"ايام ");

        Toast.makeText(getApplicationContext(),"اختر تاريخ البداية",Toast.LENGTH_LONG).show();
        DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                        Calendar tem = Calendar.getInstance();

                        Log.w("TIME", System.currentTimeMillis() + "");


                        each.append(newDate.getTime().toString());

                        mH = newDate.get(Calendar.HOUR_OF_DAY);
                        mMin = newDate.get(Calendar.MINUTE);
                        mY = newDate.get(Calendar.YEAR);
                        mM = newDate.get(Calendar.MONTH) + 1;
                        mD = newDate.get(Calendar.DATE);

                        newDate.set(Calendar.MONTH, --mM);
                        newDate.set(Calendar.YEAR, mY);
                        newDate.set(Calendar.DAY_OF_MONTH, mD);
                        newDate.set(Calendar.HOUR_OF_DAY, mH);
                        newDate.set(Calendar.MINUTE, mMin);
                        newDate.set(Calendar.SECOND, 0);
                        selectedTimestamp = newDate.getTimeInMillis();

                        s = Long.toString(selectedTimestamp);
                        finall = newDate.getTime().toString();
                        typee = "each4";


                    }
                }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                time.show();

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();

    }

    @Override
    public void on4Clicked() {
        period="5";
        each.setText("كل "+period+"ايام ");

        singleChoiceDialog.dismiss();

        Toast.makeText(getApplicationContext(),"اختر تاريخ البداية",Toast.LENGTH_LONG).show();
        DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                        Calendar tem = Calendar.getInstance();

                        Log.w("TIME", System.currentTimeMillis() + "");


                        each.append(newDate.getTime().toString());

                        mH = newDate.get(Calendar.HOUR_OF_DAY);
                        mMin = newDate.get(Calendar.MINUTE);
                        mY = newDate.get(Calendar.YEAR);
                        mM = newDate.get(Calendar.MONTH) + 1;
                        mD = newDate.get(Calendar.DATE);

                        newDate.set(Calendar.MONTH, --mM);
                        newDate.set(Calendar.YEAR, mY);
                        newDate.set(Calendar.DAY_OF_MONTH, mD);
                        newDate.set(Calendar.HOUR_OF_DAY, mH);
                        newDate.set(Calendar.MINUTE, mMin);
                        newDate.set(Calendar.SECOND, 0);
                        selectedTimestamp = newDate.getTimeInMillis();

                        s = Long.toString(selectedTimestamp);
                        finall = newDate.getTime().toString();
                        typee = "each5";


                    }
                }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                time.show();

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();

    }

    @Override
    public void on5Clicked() {
        period="6";
        each.setText("كل "+period+"ايام ");

        singleChoiceDialog.dismiss();

        Toast.makeText(getApplicationContext(),"اختر تاريخ البداية",Toast.LENGTH_LONG).show();
        DatePickerDialog dialog = new DatePickerDialog(weekk.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final Calendar newDate = Calendar.getInstance();
                Calendar newTime = Calendar.getInstance();
                TimePickerDialog time = new TimePickerDialog(weekk.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                        Calendar tem = Calendar.getInstance();

                        Log.w("TIME", System.currentTimeMillis() + "");


                        each.append(newDate.getTime().toString());

                        mH = newDate.get(Calendar.HOUR_OF_DAY);
                        mMin = newDate.get(Calendar.MINUTE);
                        mY = newDate.get(Calendar.YEAR);
                        mM = newDate.get(Calendar.MONTH) + 1;
                        mD = newDate.get(Calendar.DATE);

                        newDate.set(Calendar.MONTH, --mM);
                        newDate.set(Calendar.YEAR, mY);
                        newDate.set(Calendar.DAY_OF_MONTH, mD);
                        newDate.set(Calendar.HOUR_OF_DAY, mH);
                        newDate.set(Calendar.MINUTE, mMin);
                        newDate.set(Calendar.SECOND, 0);
                        selectedTimestamp = newDate.getTimeInMillis();

                        s = Long.toString(selectedTimestamp);
                        finall = newDate.getTime().toString();
                        typee = "each6";


                    }
                }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                time.show();

            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();

    }
}
