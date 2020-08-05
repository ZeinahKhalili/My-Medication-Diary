package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class interD extends AppCompatDialogFragment {
    int position;

    public interface interDd {
        void  onNoClicked();

    }

    interDd mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(interDd) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("تنبيه! يوجد لديك تعارض")

                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNoClicked();
                    }
                });
        return builder.create();
    }
}
