package com.example.busbookingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChangeDetails extends AppCompatActivity {
    private static Button setPass;
    private static EditText pass, confpass;
    private static String Name="",Id="";
    MediaPlayer start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        setPass = (Button) findViewById(R.id.confirm_pass);
        pass = (EditText) findViewById(R.id.passw);
        confpass = (EditText) findViewById(R.id.confpassw);
        Intent get_int=getIntent();
        Name=get_int.getStringExtra("name");
        Id=get_int.getStringExtra("id");
        start=MediaPlayer.create(ChangeDetails.this,R.raw.start);
        setOnClickListener();
    }
    public void setOnClickListener()
    {
        setPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pass=pass.getText().toString();
                String Confpass=confpass.getText().toString();
                if(Pass.equals("")||Confpass.equals(""))
                    Toast.makeText(ChangeDetails.this,"Enter the password",Toast.LENGTH_SHORT).show();
                else if(Pass.equals(Confpass))
                {
                    if(Pass.length()<8)
                        Toast.makeText(ChangeDetails.this, "Password should be of atleast 8 characters.", Toast.LENGTH_LONG).show();
                    else
                    {
                        if(start.isPlaying())
                            start.stop();
                        start.start();
                        ///send to the server.
                        MessageSender ms=new MessageSender();
                        ms.execute(String.valueOf(Pass.hashCode()));
                    }
                }
                else
                    Toast.makeText(ChangeDetails.this, "Password and Confirm Password didn't match", Toast.LENGTH_LONG).show();
            }
        });
    }
    public class MessageSender extends AsyncTask<String,Void,Void> {
        Socket s;
        //    DataOutputStream dos;
        PrintWriter pw;
        BufferedReader br;
        String input="";
        Handler h=new Handler();
        @Override
        protected Void doInBackground(String... voids) {
            String message=voids[0];
            try {
                s=new Socket("10.2.64.198",7800);
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                pw.println(message);
                pw.flush();
                if(!message.equals("-1")) {
                    while(true)
                    {
                        input=br.readLine();
                        break;
                    }
                    if(input.equals("1"))
                    {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeDetails.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangeDetails.this, BookTicket.class);
                                intent.putExtra("name",Name);
                                intent.putExtra("id",Id);
                                startActivity(intent);
                            }
                        });
                    }
                    else
                    {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeDetails.this,"Unsuccessful try. Password changing failed.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                br.close();
                pw.close();
                s.close();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(ChangePassword.this,"Going back",Toast.LENGTH_SHORT).show();
//        MessageSender2 phi=new MessageSender2();
//        phi.execute("-1");
//        finish();
        showAlert();
//        super.onBackPressed();
    }
    public void showAlert()
    {
        AlertDialog.Builder a_builder=new AlertDialog.Builder(ChangeDetails.this);
        a_builder.setMessage("Do you really want to quit ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        try {
//                            if(flag==0)
//                            {
                            Toast.makeText(ChangeDetails.this,"Going back",Toast.LENGTH_SHORT).show();
                            MessageSender phi=new MessageSender();
                            phi.execute("-1");
//                            }
                            finish();
                        }catch (Exception e){
                            Toast.makeText(ChangeDetails.this,"Error.",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert=a_builder.create();
        alert.setTitle("Confirm Exit !!!");
        alert.show();
    }
}
