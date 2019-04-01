package com.example.busbookingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VerifyEmail extends AppCompatActivity {

    private static Button btn;
    private static EditText Code;
    private static String getcode;
    String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
//        Thread myThread =new Thread(new MyServerThread());
//        myThread.start();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        btn=(Button)findViewById(R.id.btn);
        Code=(EditText)findViewById(R.id.code);
        setOnClickListener();
    }
    public void setOnClickListener()
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getcode=Code.getText().toString();
                    MessageSender ms =new MessageSender();
                    // Match the code with the one sent by the server to the email.
                    if(message.equals(getcode))
                    {
                        Intent intent=new Intent(VerifyEmail.this,BookTicket.class);
                        startActivity(intent);
                        ms.execute("1");
                        Toast.makeText(VerifyEmail.this,"Sent to server",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        ms.execute("-1");// email verification failed
                        Toast.makeText(VerifyEmail.this,"Code Mismatched.",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    public class MessageSender extends AsyncTask<String,Void,Void> {
    Socket s;
//    DataOutputStream dos;
    PrintWriter pw;
    @Override
    protected Void doInBackground(String... voids) {
        String message=voids[0];
        try {
//            s=new Socket("10.2.64.198",7800);
            pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            pw.println(message);
            pw.flush();
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
//    class MyServerThread implements Runnable{
//        Socket s;
//        ServerSocket ss;
//        InputStreamReader isr;
//        BufferedReader br;
//        Handler h=new Handler();
//        @Override
//        public void run() {
//            try {
//                ss = new ServerSocket(7801);
//                while(true)
//                {
//                    s=ss.accept();
//                    isr=new InputStreamReader(s.getInputStream());
//                    br=new BufferedReader(isr);
//                    message=br.readLine();
//                    h.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(VerifyEmail.this,message,Toast.LENGTH_SHORT).show();
////                            sbr.append(message+"\n");
////                            chat.setText(chat.getText().toString()+"\n"+message);
//                        }
//                    });
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
