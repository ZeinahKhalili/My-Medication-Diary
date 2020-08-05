package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class meddialnop extends AppCompatDialogFragment {
    int position;

    public interface meddialnopL {
        void onPositiveClicked();

        void onNegativeClicked();
    }

    meddialnopL mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(meddialnopL) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"laydialogg must implemented");

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("لقد تبقى في علبة دوائك 5 حبوب")
               .setPositiveButton("اعادة التعبئة", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       Toast.makeText(getContext(),"refill",Toast.LENGTH_SHORT).show();
                        mListener.onPositiveClicked();
                   }
               })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"cancel",Toast.LENGTH_SHORT).show();
                mListener.onNegativeClicked();

            }
        });
        return builder.create();
    }
}
