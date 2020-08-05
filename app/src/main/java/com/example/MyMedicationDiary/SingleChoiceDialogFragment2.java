package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SingleChoiceDialogFragment2 extends DialogFragment {
    int position=0; // default position
    public interface SingleChoiceListener2 {
        void onPositiveButtonClicked (String[] list, int position);
        void onNegativeButtonClicked();

    }

    SingleChoiceListener2 mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(SingleChoiceListener2) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"SingleChoiceListener must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.singlechoice);

        builder.setTitle("اختر اليوم")
                .setSingleChoiceItems(list ,position, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        position=i;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onPositiveButtonClicked(list,position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }
}
