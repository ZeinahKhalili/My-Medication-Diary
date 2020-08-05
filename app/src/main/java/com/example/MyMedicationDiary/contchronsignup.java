package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class contchronsignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener,  multipleChoiceFragmentCD.onMultiChoiceListener{

    public TextView cds;
        public TextView scted;
        public String chronics;
    // Declaring the String Array with the Text Data for the Spinners
    String[] genders = { "", "A+","A-", "B+","B-", "AB+","AB-", "O+","O-"};
    // Declaring the Integer Array with resourse Id's of Images for the Spinners
    Integer[] images = { 0, R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood,};
    private TextView next,t;

    public String bt;
    public String namme,password,email,mobile,age,gend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contchronsignup);
        getSupportActionBar().hide();

// Declaring and typecasting a Spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerBT);

// Setting a Custom Adapter to the Spinner
        mySpinner.setAdapter(new MyAdapter(contchronsignup.this, R.layout.custome2,
                genders));

         //   bt=mySpinner.getSelectedItem().toString();


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                bt= parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent=getIntent();
        namme=intent.getStringExtra("username");
        password=intent.getStringExtra("password");
        email=intent.getStringExtra("email");
        mobile=intent.getStringExtra("mobile");
        age=intent.getStringExtra("age");
        gend=intent.getStringExtra("gender");

       /* t=(TextView) findViewById(R.id.tttt);
        t.setText(gend);*/

        next=(TextView)findViewById(R.id.nnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo2();
            }
        });

        cds=findViewById(R.id.cdchoose);
        cds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment multiChoiceDialogCD = new multipleChoiceFragmentCD();
                multiChoiceDialogCD.setCancelable(false);
                multiChoiceDialogCD.show(getSupportFragmentManager(), "hi");
            }
        });

        scted=findViewById(R.id.tttt2);



        chronics=scted.getText().toString();

    }
    private void goTo2(){
      //  new ConnectionS().execute();


       chronics=scted.getText().toString();
        String method="signup";
        String type = "3";
        background bg = new background(this);
        bg.execute(method,type,chronics,bt,namme);
       /* if (!(name.equals("") || password.equals("") || email.equals("") || mobile.equals(""))) {
            Intent intent3 = new Intent(contchronsignup.this, Home.class);
            startActivity(intent3);
        }

*/
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPositiveButtonClickedListener(String[] list, ArrayList<String> selectedItemList) {
        StringBuilder stringBuilder=new StringBuilder();
        for(String str:selectedItemList){
            stringBuilder.append(str+" ..");
        }
        scted.setText(stringBuilder);
    }

    @Override
    public void onNegativeButtonClickedListener() {
        scted.setText("لم يتم اختيار أي مرض مزمن");

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
            View layout = inflater.inflate(R.layout.custome2, parent, false);

// Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage2 = (TextView) layout
                    .findViewById(R.id.tvLanguage2);

// Setting the text using the array
            tvLanguage2.setText(genders[position]);

// Setting the color of the text
            tvLanguage2.setTextColor(Color.rgb(75, 180, 225));

// Declaring and Typecasting the imageView in the inflated layout
            ImageView img = (ImageView) layout.findViewById(R.id.imgLanguage2);

// Setting an image using the id's in the array
            img.setImageResource(images[position]);

// Setting Special atrributes for 1st element
            if (position == 0) {
// Removing the image view
                img.setVisibility(View.GONE);
// Setting the size of the text
                tvLanguage2.setTextSize(20f);
// Setting the text Color
                tvLanguage2.setTextColor(Color.BLACK);

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







}
