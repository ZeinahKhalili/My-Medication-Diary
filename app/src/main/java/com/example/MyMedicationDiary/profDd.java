package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class profDd extends AppCompatDialogFragment {
    int position;

    public interface profDdd {
     void  onYesClicked();
        void  onNoClicked();

    }

    profDdd mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(profDdd) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("هل انت متأكد؟")
                .setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onYesClicked();
                    }
                })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onNoClicked();
            }
        });
        return builder.create();
    }
}
