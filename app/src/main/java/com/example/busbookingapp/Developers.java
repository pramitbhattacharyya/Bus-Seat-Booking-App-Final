package com.example.busbookingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Developers extends AppCompatActivity {

    ImageButton btn,feedback;
    private static ImageButton fb,insta,quora,skype,git,linked;
    private  static ProgressBar prog_bar;
    private static TextView text_view;
    MediaPlayer dev;
//    private static String flag="HI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        fb=(ImageButton)findViewById(R.id.facebook);
        insta=(ImageButton)findViewById(R.id.instagram);
        quora=(ImageButton)findViewById(R.id.qoura);
        skype=(ImageButton)findViewById(R.id.skype);
        git=(ImageButton)findViewById(R.id.github);
        linked=(ImageButton)findViewById(R.id.linked_in);
        dev=MediaPlayer.create(Developers.this,R.raw.welcome);
        if(dev.isPlaying())
            dev.stop();
        dev.start();

//        Intent getIntent=getIntent();
//        flag=getIntent.getStringExtra("flag");
//        Toast.makeText(Developers.this,flag,Toast.LENGTH_SHORT).show();

//        text_view=(TextView)findViewById((R.id.textView));
//        prog_bar=(ProgressBar) findViewById(R.id.progressBar);
//        prog_bar.setMax(100);
//        prog_bar.setScaleY(2f);
//        cprog_bar=(ProgressBar)findViewById(R.id.cprog_bar);
//        text_view.setVisibility(View.INVISIBLE);
//        prog_bar.setVisibility(View.INVISIBLE);
//        cprog_bar.setVisibility(View.INVISIBLE);
        setOnClickListener();
    }
    public void setOnClickListener()
    {
        btn=(ImageButton)findViewById(R.id.start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressAnimation();
//                prog_bar.setVisibility(View.VISIBLE);
//                text_view.setVisibility(View.VISIBLE);
//                cprog_bar.setVisibility(View.VISIBLE);
//                prog_bar.setProgress(50);
////                cprog_bar.setProgress(50);
//                text_view.setText("Loading: "+ 50+" / "+ 100);
//                set(20);
//                long time=System.currentTimeMillis();
//                while (System.currentTimeMillis()-time<1000);
//                time=System.currentTimeMillis();
//                set(40);
////                prog_bar.setProgress(100);
//////                cprog_bar.setProgress(100);
////                text_view.setText("Loading: "+ 100+" / "+ 100);
//                while (System.currentTimeMillis()-time<1000);
//                time=System.currentTimeMillis();
//                set(60);
//                while (System.currentTimeMillis()-time<1000);
//                time=System.currentTimeMillis();
//                set(80);
//                while (System.currentTimeMillis()-time<1000);
//                time=System.currentTimeMillis();
//                set(100);


//                int i=1;
//                long time=System.currentTimeMillis();
//                while(i<5)
//                {
//                    if(System.currentTimeMillis()-time>=500)
//                    {
//                        time=System.currentTimeMillis();
//                        i++;
//                        set(i);
//                    }
//                }
                Intent intent=new Intent(Developers.this,MainActivity.class);
                startActivity(intent);
//                prog_bar.setProgress(0);
//                text_view.setText("");
//                cprog_bar.setProgress(0);
            }
        });
        feedback=(ImageButton)findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,Feedback.class);
                startActivity(intent);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://www.facebook.com/people/Pramit-Bhattacharyya/100007015189401");
                startActivity(intent);
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://www.instagram.com/pramitbhattacharyya/");
                startActivity(intent);
            }
        });
        quora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://www.quora.com/profile/Pramit-Bhattacharyya-7");
                startActivity(intent);
            }
        });
        linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://www.linkedin.com/in/pramit-bhattacharyya-400776164/");
                startActivity(intent);
            }
        });
        skype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://secure.skype.com/portal/profile");
                startActivity(intent);
            }
        });
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Developers.this,DeveloperHandler.class);
                intent.putExtra("url","https://github.com/pramitbhattacharyya");
                startActivity(intent);
            }
        });
    }
//    public void set(int i)
//    {
//        prog_bar.setProgress(i*20);
////        cprog_bar.setProgress(i*20);
////        prog_bar.setVisibility(View.VISIBLE);
////        cprog_bar.setVisibility(View.VISIBLE);
////        text_view.setVisibility(View.VISIBLE);
////                        seek_bar.startAnimation(seek_bar.getAnimation());
//        text_view.setText("Loading: "+ (i)+" / "+ 100);
//    }

//    public void seekbarr()
//    {
//        seek_bar=(SeekBar)findViewById(R.id.seekBar);
//        text_view=(TextView)findViewById((R.id.textView));
//        text_view.setText("Covered: "+ seek_bar.getProgress()+" / "+ seek_bar.getMax());
//        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
//        {
//            int progress_value;
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
//            {
//                progress_value=i;
//                text_view.setText("Covered: "+ i +" / "+ seek_bar.getMax());
//                Toast.makeText(Developers.this, "SeekBar is in progress", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar)
//            {
//                Toast.makeText(Developers.this, "SeekBar in StartTracking", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar)
//            {
//                text_view.setText("Covered: "+ seek_bar.getProgress()+" / "+ seek_bar.getMax());
//                Toast.makeText(Developers.this, "SeekBar in StopTracking", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
    //        super.onBackPressed();
        AlertDialog.Builder a_builder=new AlertDialog.Builder(Developers.this);
        a_builder.setMessage("Do you want to Exit the App ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                        giveNoti(Developers.this);
                        finish();
    //                        finish();
    //                        android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
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
        alert.setTitle("Quit The App !!!");
        alert.show();
    }
    public void giveNoti(Context context)
    {
        int NOTIFICATION_ID = 143;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID="my_channel_02";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
//                        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("BUS BOOKING APP")
                .setContentText("Thank You For Using The App. Please Use Again.")
                .setLights(Color.RED, 1,1)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        ////////////////////////////////////////////////////////////////////
        //.setDefaults(NotificationCompat.DEFAULT_SOUND);
//                        Notification notif = builder.build();
//                        notif.flags |= Notification.FLAG_SHOW_LIGHTS;
        ////////////////////////////////////////////////////////////////////////
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
        ////////////////////////////////////////////////////////////////
//                        notificationManager.notify(NOTIFICATION_ID, notif);
        ////////////////////////////////////////////////////////////////
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
