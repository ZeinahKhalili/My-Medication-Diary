package com.example.MyMedicationDiary;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter2 extends BaseAdapter
{
    Context context;

    List<Subject2> subject_list;

    public ListAdapter2(List<Subject2> listValue, Context context)
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
        ViewItem2 viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem2();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.recycleview_item2, null);

            viewItem.SubNameTextVieww = (TextView)convertView.findViewById(R.id.textViewNa);

            viewItem.SubFullFormTextVieww = (TextView)convertView.findViewById(R.id.textViewSp);
            viewItem.SubFullFormTextVieww2 = (TextView)convertView.findViewById(R.id.textViewDT);




            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem2) convertView.getTag();
        }

        viewItem.SubNameTextVieww.setText("اسم الطبيب: "+subject_list.get(position).Subject_Namee);
        viewItem.SubFullFormTextVieww.setText("تخصص الطبيب: "+subject_list.get(position).Subject_Spec);
        viewItem.SubFullFormTextVieww2.setText("موعد الزيارة: "+subject_list.get(position).Subject_DT);

        return convertView;
    }
}

class ViewItem2
{
    TextView SubNameTextVieww;
    TextView SubFullFormTextVieww;
    TextView SubFullFormTextVieww2;

}
