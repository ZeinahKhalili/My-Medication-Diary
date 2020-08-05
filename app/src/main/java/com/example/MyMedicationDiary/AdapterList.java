package com.example.MyMedicationDiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder>{

    private ListView lv;
    private TextView message,time;

    public AdapterList(ListView lv) {
        this.lv = lv;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_item,viewGroup,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterList.MyViewHolder myViewHolder, int i) {

       /* Reminders reminders = allReminders.get(i);
        if(!reminders.getMessage().equals(""))
            message.setText(reminders.getMessage());
        else
            message.setHint("No Message");
        time.setText(reminders.getRemindDate().toString());
*/

    }

    @Override
    public int getItemCount() {
        return lv.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }
    }

}
