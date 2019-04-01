package com.example.busbookingapp;

// comment this whole file.
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
public class TTReciever extends BroadcastReceiver {
    private StringBuffer input;
    private String filename="History2.txt";
    @Override
    public void onReceive(Context context, Intent intent) {


        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        v.vibrate(10000);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
        ringtone.play();
        read(context);
        giveNoti(context);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(ringtone.isPlaying())
            ringtone.stop();
    }
    public void giveNoti(Context context)
    {
        int NOTIFICATION_ID = 456;

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
                .setContentTitle("BUS RIDE")
                .setContentText("You have a bus ride within 30 minutes.")
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
    public void setAlarm(Context context, Date date, String sub)
    {
        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //  int time = Integer.parseInt(edTime.getText().toString());
//            String date1 = edTimes.getText().toString();

            Date date2 = new Date();

//            Date date = format.parse(date1);

            long time = date.getTime() - date2.getTime();
//            Toast.makeText(context, "Alarm Set" +(time/1000)+ " seconds later", Toast.LENGTH_LONG).show();
            Intent ip = new Intent(context, TTReciever.class);
            PendingIntent pii = PendingIntent.getBroadcast(context.getApplicationContext(), 0, ip, 0);
            AlarmManager ams = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            ams.set(AlarmManager.RTC_WAKEUP, date.getTime(), pii);
//                    am.setExact(AlarmManager.RTC_WAKEUP,time,"Good",);
            Toast.makeText(context, "Alarm is Set", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(context, "Alarm Not Set", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void buildAlarm(Context context, StringBuffer sb)// to be commented out
    {
        try {
            if(sb!=null && !sb.toString().equals("")) {
                String data[] = sb.toString().split("\n", 0);
                String alarmdate = "";
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date presentdate = new Date();
                Date setDate = null, tryDate;
                int setflag = 0;
                String each[];
                for (int i = 0; i < data.length; i++) {
                    if (data[i].length()==0 || data[i].charAt(0) == '#')
                        continue;
                    each = data[i].split(";", 0);
                    int day = Integer.parseInt(each[0].substring(0,each[0].indexOf("-")));
                    each[0]=(day<=9?"0":"")+each[0];
                    int mon = Integer.parseInt(each[0].substring(
                            each[0].indexOf("-") + 1, each[0].lastIndexOf("-")));
                    if (mon <= 9)
                        alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                + "0" + each[0].substring(each[0].indexOf("-") + 1))
                                + " " + data[1].substring(0, data[1].indexOf("-") - 1);
                    else
                        alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                + each[0].substring(each[0].indexOf("-") + 1))
                                + " " + data[1].substring(0, data[1].indexOf("-") - 1);
                    tryDate = format.parse(alarmdate);
//                    tryDate = (new SimpleDateFormat("ss")).parse(
//                            String.valueOf((tryDate.getTime()-30*60*1000)/1000)
//                    );
                    tryDate=new Date(tryDate.getTime()-30*60*1000);
                    if (setflag == 0 && tryDate.after(presentdate)) {
                        setDate = tryDate;
                        setflag = 1;
                    } else if (tryDate.after(presentdate)) {
                        if (setDate.after(tryDate))
                            setDate = tryDate;
                    }
                }
                if (setDate != null && setflag==1)
                {
                    setAlarm(context, setDate, "You have a bus ride within 30 minutes.");
                    Toast.makeText(context,"buildalarm() performed right. Date= "+setDate.toString(),Toast.LENGTH_SHORT).show();
                }
            }

        }catch (ParseException p)
        {
            Toast.makeText(context, "Parse Exception occured "+p, Toast.LENGTH_SHORT).show();
            p.printStackTrace();
        }
        catch(Exception e)
        {
            Toast.makeText(context, "Other than parse exception "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void read(Context context)
    {
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(context.openFileInput( filename)));
//            int i=0;
            input=new StringBuffer("") ;
            String lines;
            while((lines=bufferedReader.readLine())!=null)
                input.append(lines+"\n");
//            input=sbr.toString().split(";",-2);
//            sbr.delete(0,sbr.length());
        }
        catch (FileNotFoundException e)
        {
            input=new StringBuffer("") ;
            Toast.makeText(context,"No such file is present ! "+e,Toast.LENGTH_SHORT).show();
//            System.exit(0);
//            e.printStackTrace();
        }
        catch (IOException e)
        {
            Toast.makeText(context,"Error "+e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        finally {
            buildAlarm(context,input);
        }
    }
//}
}
//
