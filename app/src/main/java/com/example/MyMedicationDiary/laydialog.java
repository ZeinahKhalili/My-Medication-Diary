package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class laydialog extends AppCompatDialogFragment {
    int position;

    public interface laydialogg {
        void onafterClicked ();
        void onbefClicked();
        void ondurClicked();
    }

    laydialogg mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(laydialogg) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.mealstimes);

        builder.setTitle("اختر الموعد")
                .setSingleChoiceItems(list ,position, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        position=i;
                        if (position==0){
                            mListener.onbefClicked();
                        }
                        else if(position==1){
                            mListener.ondurClicked();

                        }
                        else if(position==2){
                            mListener.onafterClicked();
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
