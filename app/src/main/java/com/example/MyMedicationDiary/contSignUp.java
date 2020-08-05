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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class contSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public EditText agee;
        public String gend;
    public TextView trial;
    public Switch swvac;
    public static String dateOfBirth;
    long datte;
    String vac;
    // Declaring the String Array with the Text Data for the Spinners
    String[] genders = { "", "Male", "Female"};
    // Declaring the Integer Array with resourse Id's of Images for the Spinners
    Integer[] images = { 0, R.drawable.gendermale, R.drawable.genderfem};
    private TextView next;

        String name,password,email,mobile,ageee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_sign_up);
        getSupportActionBar().hide();

        swvac=findViewById(R.id.swvac);

        Intent intent=getIntent();
        name=intent.getStringExtra("username");
      /*  password=intent.getStringExtra("password");
        email=intent.getStringExtra("email");
         mobile=intent.getStringExtra("mobile");
*/
        agee=(EditText)findViewById(R.id.age);

// Declaring and typecasting a Spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerGen);

// Setting a Custom Adapter to the Spinner
        mySpinner.setAdapter(new MyAdapter(contSignUp.this, R.layout.custome,
                genders));

       //  gend = mySpinner.getSelectedItem().toString();


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                gend= parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next=(TextView)findViewById(R.id.sup4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });
        swvac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"ادخل تاريخ الولادة",Toast.LENGTH_LONG).show();
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }

            }

            });

    }
    private void goTo2(){

        ageee = agee.getText().toString();
        if(swvac.isChecked()){
            vac="yes";
            String method="signup";
            String type = "2";
            background bg = new background(this);
            String dattee=Long.toString(datte);

            bg.execute(method,type,ageee,gend,name,vac,dateOfBirth,dattee);
        }
        else{
            vac="no";
            String method="signup";
            String type = "5";
            background bg = new background(this);
            bg.execute(method,type,ageee,gend,name,vac);
        }


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            View layout = inflater.inflate(R.layout.custome, parent, false);

// Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = (TextView) layout
                    .findViewById(R.id.tvLanguage);

// Setting the text using the array
            tvLanguage.setText(genders[position]);

// Setting the color of the text
            tvLanguage.setTextColor(Color.rgb(75, 180, 225));

// Declaring and Typecasting the imageView in the inflated layout
            ImageView img = (ImageView) layout.findViewById(R.id.imgLanguage);

// Setting an image using the id's in the array
            img.setImageResource(images[position]);

// Setting Special atrributes for 1st element
            if (position == 0) {
// Removing the image view
                img.setVisibility(View.GONE);
// Setting the size of the text
                tvLanguage.setTextSize(20f);
// Setting the text Color
                tvLanguage.setTextColor(Color.BLACK);

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
            long datte=c.getTimeInMillis();
            return  dialog;
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month=month+1;
            dateOfBirth=year+"/"+month+"/"+dayOfMonth;
        }
    }









}
