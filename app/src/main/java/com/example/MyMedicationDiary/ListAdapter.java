package com.example.MyMedicationDiary;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter
{
    Context context;

    List<Subject> subject_list;

    public ListAdapter(List<Subject> listValue, Context context)
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

            convertView = layoutInfiater.inflate(R.layout.recycleview_item, null);

            viewItem.SubNameTextView = (TextView)convertView.findViewById(R.id.textView1);

            viewItem.SubFullFormTextView = (TextView)convertView.findViewById(R.id.textView2);
            viewItem.SubFullFormTextView2 = (TextView)convertView.findViewById(R.id.textView3);
            viewItem.SSS = (TextView)convertView.findViewById(R.id.sometext);




            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.SubNameTextView.setText("اسم الدواء:"+subject_list.get(position).Subject_Name);
        viewItem.SubFullFormTextView.setText("نوع الدواء:"+subject_list.get(position).Subject_Type);
        viewItem.SubFullFormTextView2.setText("كمية الدواء:"+subject_list.get(position).Subject_NOP);
        viewItem.SSS.setText(subject_list.get(position).Subject_nn);
        viewItem.SSS.setTextColor(Color.RED);
        return convertView;
    }
}

class ViewItem
{
    TextView SubNameTextView;
    TextView SubFullFormTextView;
    TextView SubFullFormTextView2;
    TextView SSS;
}
