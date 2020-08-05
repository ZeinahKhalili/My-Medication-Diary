package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class medshow extends AppCompatActivity {
        public TextView medname;
    public TextView medtype;
    public TextView mednop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medshow);

        Intent intent= getIntent();
        String medn=intent.getStringExtra("medn");
        String medt=intent.getStringExtra("medt");
        String medp=intent.getStringExtra("medp");

        medname=findViewById(R.id.medicineName);
        medtype=findViewById(R.id.medicineType);
        mednop=findViewById(R.id.medicineNOP);

        medname.setText(medn);
        medtype.setText(medt);
        mednop.setText(medp);


    }
}
