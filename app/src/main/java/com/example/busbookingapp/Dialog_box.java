package com.example.busbookingapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class Dialog_box extends AppCompatDialogFragment {
    private EditText Dia_name,Dia_ID;
    private Dialog_boxListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog,null);
        Dia_name=view.findViewById(R.id.dia_Name);
        Dia_ID=view.findViewById(R.id.dia_ID);
        builder.setView(view)
                .setTitle("Book Seat")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name=Dia_name.getText().toString();
                        String Id=Dia_ID.getText().toString();
                        listener.applyTexts(name,Id);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(Dialog_boxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement Dialog_boxListener");
        }
    }

    public interface Dialog_boxListener{
        void applyTexts(String Name, String Id);
    }
}
