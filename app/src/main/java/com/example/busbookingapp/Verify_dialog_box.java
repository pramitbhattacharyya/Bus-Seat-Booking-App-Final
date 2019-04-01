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

public class Verify_dialog_box extends AppCompatDialogFragment {
    private EditText code;
    private Verify_dialog_boxListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.verify_dialog,null);
        code=view.findViewById(R.id.code);
        builder.setView(view)
                .setTitle("Verify Email")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name=code.getText().toString();
                        listener.applyTexts(name);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(Verify_dialog_boxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement Verify_dialog_boxListener");
        }
    }

    public interface Verify_dialog_boxListener{
        void applyTexts(String Name);
    }
}
