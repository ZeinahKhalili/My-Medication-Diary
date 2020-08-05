package com.example.MyMedicationDiary;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter3 extends BaseAdapter
{
    Context context;

    List<Subject3> subject_list;

    public ListAdapter3(List<Subject3> listValue, Context context)
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
        ViewItem3 viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem3();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.recycleview_item3, null);

            viewItem.SubNameTextView3 = (TextView)convertView.findViewById(R.id.textViewMDN);

            viewItem.SubFullFormTextView3 = (TextView)convertView.findViewById(R.id.textViewMN);
            viewItem.SubFullFormTextView33 = (TextView)convertView.findViewById(R.id.textViewT);




            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem3) convertView.getTag();
        }

        viewItem.SubNameTextView3.setText("اسم الدواء: "+subject_list.get(position).Subject_Nameee);
        viewItem.SubFullFormTextView3.setText("اسم الوجبة: "+subject_list.get(position).Subject_MN);
        viewItem.SubFullFormTextView33.setText("وقت الوجبة: "+subject_list.get(position).Subject_T);

        return convertView;
    }
}

class ViewItem3
{
    TextView SubNameTextView3;
    TextView SubFullFormTextView3;
    TextView SubFullFormTextView33;

}
