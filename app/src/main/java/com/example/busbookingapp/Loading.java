package com.example.busbookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loading extends AppCompatActivity {

    private  static ProgressBar prog_bar;
    private static TextView text_view;
    private static int flag=0;
    MediaPlayer load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        text_view = (TextView) findViewById((R.id.textView));
        prog_bar = (ProgressBar) findViewById(R.id.progressBar);
        prog_bar.setMax(100);
        prog_bar.setProgress(0);
        prog_bar.setScaleY(2f);
        load=MediaPlayer.create(Loading.this,R.raw.loading);
        if(load.isPlaying())
            load.stop();
        load.start();

//        if(flag==0)
//        {
            progressAnimation();
//            flag=1;
//        }
//        while(!(text_view.getText().toString().equals("100%")));
//        long time=System.currentTimeMillis();
//        while (System.currentTimeMillis()-time<1000);
//        finish();
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<100;i++)
                    {
                        prog_bar.setProgress(i);//
                        sleep(50);
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(Loading.this,Developers.class);
                    startActivity(intent);
                    load.stop();
                    load.release();
                    finish();
                }
            }
        };
        thread.start();
    }

    public void progressAnimation()
    {
        ProgressActionBar progressActionBar=new ProgressActionBar(Loading.this,prog_bar,text_view,0f,100f);
        progressActionBar.setDuration(5000);
        prog_bar.setAnimation(progressActionBar);
    }

    public class ProgressActionBar extends Animation {
        private Context context;
        private ProgressBar prog_bar;
        private TextView textView;
        private float from;
        private float to;
        public ProgressActionBar(Context context,ProgressBar prog_bar,TextView textView,float from, float to)
        {
            this.context=context;
            this.prog_bar=prog_bar;
            this.textView=textView;
            this.from=from;
            this.to=to;
        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value=from+(to-from)*interpolatedTime;
//            prog_bar.setProgress((int)value);
            String disp="Loading: "+(int)value+"%";
            text_view.setText(disp);
//            textView.setText("Loading: "+(int)value+"%");
            if(value==to)
            {
//                Intent intent=new Intent(context,Developers.class);
//                intent.putExtra("flag",1);
//                context.startActivity(intent);
//                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        System.exit(0);
    }
}






