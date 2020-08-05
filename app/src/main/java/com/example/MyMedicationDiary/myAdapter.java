package com.example.MyMedicationDiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class myAdapter extends ArrayAdapter<String> {
    Context context;
    String details[];


    myAdapter(Context c, String detailss[]) {
        super(c, R.layout.recycleview_item, R.id.textView1, detailss);
        this.context=c;
        this.details=detailss;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      /*  LayoutInflater layoutInflater=(LayoutInflater) getS(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate((R.layout.recycleview_item), parent,false);*/
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).
                inflate(R.layout.recycleview_item, parent, false);
        TextView tv;
        tv=view.findViewById(R.id.textView1);
        tv.setText(details[position]);

        return view;
    }
}