package com.example.MyMedicationDiary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapterMeas extends BaseAdapter
{
    Context context;

    List<SubjectMe> subject_list;

    public ListAdapterMeas(List<SubjectMe> listValue, Context context)
    {
        this.context = context;
        this.subject_list = listValue;
    }

    @Override
    public int getCount()
    {
        return this.subject_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.subject_list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.recycleview_itemme, null);

            viewItem.SubNameTextView = (TextView)convertView.findViewById(R.id.texttypem);

            viewItem.SubFullFormTextView = (TextView)convertView.findViewById(R.id.textvalm);
            viewItem.SubFullFormTextView2 = (TextView)convertView.findViewById(R.id.textdatem);
            viewItem.SSS = (TextView)convertView.findViewById(R.id.texttimem);




            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.SubNameTextView.setText("نوع الفحص:"+subject_list.get(position).Subject_Type);
        viewItem.SubFullFormTextView.setText("نتيجة الفحص:"+subject_list.get(position).Subject_Val);
        viewItem.SubFullFormTextView2.setText("تاريخ الفحص:"+subject_list.get(position).Subject_date);
        viewItem.SSS.setText("وقت الفحص:"+subject_list.get(position).Subject_time);

        return convertView;
    }
}

class ViewItemMeas
{
    TextView SubNameTextView;
    TextView SubFullFormTextView;
    TextView SubFullFormTextView2;
    TextView SSS;
}
