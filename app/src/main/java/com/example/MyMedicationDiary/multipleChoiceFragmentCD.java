package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class multipleChoiceFragmentCD extends DialogFragment {
    public interface onMultiChoiceListener{
        void onPositiveButtonClickedListener(String[] list, ArrayList<String> selectedItemList);
        void onNegativeButtonClickedListener();
    }

    onMultiChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener= (onMultiChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+" onMultiChoiceListener must be implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ArrayList<String> selectedItemList = new ArrayList<>();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final String[] list = getActivity().getResources().getStringArray(R.array.cd);

        builder.setTitle("حدد الامراض المزمنة")
                .setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            selectedItemList.add(list[i]);
                        }else {
                            selectedItemList.remove(list[i]);
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onPositiveButtonClickedListener(list, selectedItemList);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onNegativeButtonClickedListener();
                    }
                });

        return builder.create();
    }
}
