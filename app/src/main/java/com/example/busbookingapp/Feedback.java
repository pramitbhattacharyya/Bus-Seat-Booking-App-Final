package com.example.busbookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Feedback extends AppCompatActivity {

    EditText feedback;
    Button send,web;
    private static String feed="";
    private static TextView text_v;
    private static RatingBar rating_b;
    MediaPlayer hello,thanks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback=(EditText)findViewById(R.id.feed);
        send=(Button)findViewById(R.id.sendFeed);
        rating_b=(RatingBar)findViewById(R.id.ratingBar);
        text_v=(TextView)findViewById(R.id.textView);
        send.setEnabled(false);
        send.setVisibility(View.INVISIBLE);
        web=(Button)findViewById(R.id.web);
        hello=MediaPlayer.create(Feedback.this,R.raw.hello);
        thanks=MediaPlayer.create(Feedback.this,R.raw.thanks);
        Toast.makeText(Feedback.this,"Give a comment and set a rating for the app",Toast.LENGTH_SHORT).show();
        listenerForRatingBar();
        setOnClickListener();
        if(hello.isPlaying())
            hello.stop();
        hello.start();
    }
    public  void setOnClickListener()
    {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(thanks.isPlaying())
                        thanks.stop();
                    thanks.start();
                    feed=feedback.getText().toString();
                    if(!feed.equals(""))
                    {
                        feedback.setText("Feedback is being sent to the server please wait.");
                        MessageSender ms=new MessageSender();
                        ms.execute(feed+" "+text_v.getText().toString());
                    }
                    else
                        Toast.makeText(Feedback.this,"Give a comment please",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Feedback.this,DeveloperHandler.class);
                intent.putExtra("url","https://iitbhilai.ac.in");
                startActivity(intent);
            }
        });
    }
    public void listenerForRatingBar()
    {
        rating_b.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener()
                {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean b)
                    {
                        text_v.setText("Rating:"+String.valueOf(rating));
                        send.setEnabled(true);
                        send.setVisibility(View.VISIBLE);
                    }
                }
        );

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
                s = new Socket("10.2.64.198", 7803);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                pw.println(message);
                pw.flush();
                while (true) {
                    input = br.readLine();
                    break;
                }
                if(input.equals("1")) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Feedback.this,"Feedback Sent. Thank you.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
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
}
