package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class notfoundD extends AppCompatDialogFragment {
    int position;

    public interface notfoundDd {
        void  onInterClicked();

        void  onCancelClicked();

    }

    notfoundDd mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(notfoundDd) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("يبدو ان هذا الدواء غير موجود، هل تود اضافته بنفسك؟")
                .setPositiveButton("ادخال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onInterClicked();

                    }
                })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onCancelClicked();
                    }
                });
        return builder.create();
    }
}
