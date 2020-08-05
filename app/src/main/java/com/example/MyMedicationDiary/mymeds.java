package com.example.MyMedicationDiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class mymeds extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymeds);
        String []items={"item1","item2","item3"};

      /*  Intent intent=getIntent();
        String Item=intent.getStringExtra("linn");*/
        listView=(ListView)findViewById(R.id.medLV);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        for(int i=0;i<3;i++){
            adapter.add(items[i]);
        }


    }
}
