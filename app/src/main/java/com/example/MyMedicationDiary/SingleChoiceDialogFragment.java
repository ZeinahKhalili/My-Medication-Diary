package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SingleChoiceDialogFragment extends DialogFragment {
        int position=0; // default position
        public interface SingleChoiceListener {
            void onPositiveButtonClicked (String[] list, int position);
            void onNegativeButtonClicked();
        }

        SingleChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"SingleChoiceListener must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final String[] list=getActivity().getResources().getStringArray(R.array.mealsChoices);

        builder.setTitle("اختر وجبتك")
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
