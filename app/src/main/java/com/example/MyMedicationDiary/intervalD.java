package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.MyMedicationDiary.R;

public class intervalD extends AppCompatDialogFragment {
    int position;

    public interface intervalDd {
        void onEnd();
        void onDate();
    }

    intervalDd mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(intervalDd) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"intervalDd must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.intervalD);

        builder.setTitle("اختر طريقة انتهاء اخذ الدواء")
                .setSingleChoiceItems(list ,position, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        position=i;
                        if (position==0){
                            mListener.onEnd();
                        }
                        else if(position==1){
                            mListener.onDate();

                        }

                    }
                }) .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder.create();
    }
}
