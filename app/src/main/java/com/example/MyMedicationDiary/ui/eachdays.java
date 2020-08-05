package com.example.MyMedicationDiary.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.MyMedicationDiary.R;

public class eachdays extends AppCompatDialogFragment  {
    int position;

    public interface eachdayslis {
        void on1Clicked();
        void on2Clicked();
        void on3Clicked();
        void on4Clicked();
        void on5Clicked();

    }

    eachdayslis mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(eachdayslis) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg2 must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.eachdays);

        builder.setTitle("اختر الموعد")
                .setSingleChoiceItems(list ,position, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        position=i+1;
                        if (position==1){
                            mListener.on1Clicked();
                        }
                        else if(position==2){
                            mListener.on2Clicked();

                        }
                        else if(position==3){
                            mListener.on3Clicked();
                        }
                        else if(position==4){
                            mListener.on4Clicked();
                        }else if(position==5){
                            mListener.on5Clicked();
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
