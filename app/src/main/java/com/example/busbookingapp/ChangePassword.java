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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChangePassword extends AppCompatActivity {
    private static TextView wrong,otpView;
    private static EditText otp;
    private static Button changePass,send;
    private static String Name="",Id="";
    private static String OTP="";
    MediaPlayer denied,accessed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        wrong=(TextView)findViewById(R.id.wrong);
        changePass=(Button)findViewById(R.id.change_pass);
        otp=(EditText) findViewById(R.id.otp);
        otpView=(TextView)findViewById(R.id.OtpView);
        send=(Button)findViewById(R.id.otpSend);
        otp.setVisibility(View.INVISIBLE);
        otpView.setVisibility(View.INVISIBLE);
        send.setEnabled(false);
        send.setVisibility(View.INVISIBLE);
        Intent get_int=getIntent();
        Name=get_int.getStringExtra("name");
        Id=get_int.getStringExtra("id");
        denied= MediaPlayer.create(ChangePassword.this,R.raw.denied);
        accessed=MediaPlayer.create(ChangePassword.this,R.raw.accessed);
        setOnClickListener();
    }
    public void setOnClickListener()
    {
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrong.setText("An opt is being sent to the already registered email. Please Wait...");
                changePass.setEnabled(false);
                changePass.setVisibility(View.INVISIBLE);
                MessageSender ms=new MessageSender();
                ms.execute("#send");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                String match="Hi";
                match=otp.getText().toString();
                Toast.makeText(ChangePassword.this,match+" "+OTP,Toast.LENGTH_SHORT).show();
                if(OTP.equals(match))
                {
                    if(accessed.isPlaying())
                        accessed.stop();
                    accessed.start();
                    intent = new Intent(ChangePassword.this, ChangeDetails.class);
                    intent.putExtra("name",Name);
                    intent.putExtra("id",Id);
                    startActivity(intent);
                    finish();
                }
                else {
                    if(denied.isPlaying())
                        denied.stop();
                    denied.start();
                    Toast.makeText(ChangePassword.this, "OTP Mismatched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class MessageSender extends AsyncTask<String,Void,Void> {
        Socket s;
        //    DataOutputStream dos;
        PrintWriter pw;
        BufferedReader br;
        String input = "";
        Handler h=new Handler();

        @Override
        protected Void doInBackground(String... voids) {
            String message = voids[0];
            try {
                s = new Socket("10.2.64.198", 7800);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                pw.println(message);
                pw.flush();
                while (true) {
                    input = br.readLine();
                    if(!input.equals(""))
                        break;
                }
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        OTP=input;
                        wrong.setText("An Email Has been sent to the mail address");
                        otp.setVisibility(View.VISIBLE);
                        otpView.setVisibility(View.VISIBLE);
                        send.setEnabled(true);
                        send.setVisibility(View.VISIBLE);
//                        changePass.setEnabled(false);
//                        changePass.setVisibility(View.INVISIBLE);
                    }
                });
//                pw.println("U sent "+input);
//                pw.flush();
//                while(true)
//                {
//                    input = br.readLine();
//                    break;
//                }
                pw.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
    public class MessageSender2 extends AsyncTask<String,Void,Void> {
        Socket s;
        //    DataOutputStream dos;
        PrintWriter pw;
        BufferedReader br;
        String input="";
        Handler h;
        @Override
        protected Void doInBackground(String... voids) {
            String message=voids[0];
            try {
                s=new Socket("10.2.64.198",7800);
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                pw.println(message);
                pw.flush();
                if(!message.equals("-1"))
                    while(true)
                    {
                        input=br.readLine();
                        break;
                    }
                br.close();
                pw.close();
                s.close();
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
        AlertDialog.Builder a_builder=new AlertDialog.Builder(ChangePassword.this);
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
                            Toast.makeText(ChangePassword.this,"Going back",Toast.LENGTH_SHORT).show();
                            MessageSender2 phi=new MessageSender2();
                            phi.execute("-1");
//                            }
                            finish();
                        }catch (Exception e){
                            Toast.makeText(ChangePassword.this,"Error.",Toast.LENGTH_SHORT).show();
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
