package com.example.MyMedicationDiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class tipsD extends DialogFragment {

    Button dan;
    TextView title,contentTip,br;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tipsdialog,container,false);
        title=view.findViewById(R.id.tiptitle);
        contentTip=view.findViewById(R.id.tipcontent);
        dan=view.findViewById(R.id.okkk);
        br=view.findViewById(R.id.tipbrought);
        String tipText =  ((Home)getActivity()).tipText;
        String broughtBy = ((Home)getActivity()).broughtBy;
        contentTip.setText(tipText);
        br.append("\n");
        br.append("هذه النصيحة مقدمة إليكم من "+broughtBy);
        dan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
