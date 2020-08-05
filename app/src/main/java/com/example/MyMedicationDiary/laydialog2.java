package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class laydialog2 extends AppCompatDialogFragment {
    int position;

    public interface laydialogg2 {
        void on1Clicked();
        void on2Clicked();
        void on3Clicked();
        void on4Clicked();
        void on5Clicked();
        void on6Clicked();

    }

    laydialogg2 mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(laydialogg2) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg2 must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.mealstimes2);

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
                        }else if(position==6){
                            mListener.on6Clicked();
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
