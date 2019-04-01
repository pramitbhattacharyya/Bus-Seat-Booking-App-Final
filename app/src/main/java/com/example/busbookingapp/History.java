package com.example.busbookingapp;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {
    private static ListView list_view;
    private  String history[];
    private  StringBuffer input;
    private  static int historydate=5;
    private String filename="History2.txt";
    MediaPlayer start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list_view=(ListView)findViewById(R.id.listview);
        setListViewHeightBasedOnChildren(list_view);
        start= MediaPlayer.create(History.this,R.raw.start);
        if(start.isPlaying())
            start.stop();
        start.start();
        read();
//        write(new StringBuffer(""));
        clearData();
        listView();
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public void listView()
    {
        list_view=(ListView)findViewById(R.id.listview);
        try {
            final List<String> task_list = new ArrayList<String>(Arrays.asList(history));
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, R.layout.name_list, task_list);
            list_view.setAdapter(adapter);
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String value=String.valueOf(list_view.getItemAtPosition(position));
                    Toast.makeText(History.this, ""+value,Toast.LENGTH_SHORT).show();
                }
            });
//            list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//                @Override
//                public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, long id)
//                {
////                Toast.makeText(HomeWorkList .this, "LongClicked", Toast.LENGTH_LONG).show();
//                    String value=String.valueOf(list_view.getItemAtPosition(pos));
//                    AlertDialog.Builder a_builder=new AlertDialog.Builder(History.this);
//                    a_builder.setMessage("Do you want to delete this task :\n"+value)
//                            .setCancelable(true)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                            {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i)
//                                {
//                                    dialogInterface.cancel();
//                                    task_list.remove(pos);
//                                    adapter.notifyDataSetChanged();
//                                    check[pos]=1;
//                                    deleted++;
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener()
//                            {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i)
//                                {
//                                    dialogInterface.cancel();
//                                }
//                            });
//                    AlertDialog alert=a_builder.create();
//                    alert.setTitle("DELETE TASK !!!");
//                    alert.show();
//                    return false;
//                }
//            });
        }
        catch (Exception e)
        {
//            Toast.makeText(HomeWorkList.this,"The Task List is Empty !!",Toast.LENGTH_LONG).show();
//            Intent intent=new Intent(HomeWorkList.this,MainActivity.class);
//            startActivity(intent);
//            finish();
            e.printStackTrace();
        }
    }
    public void read()
    {
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
//            int i=0;
            input=new StringBuffer("") ;
            String lines;
            while((lines=bufferedReader.readLine())!=null)
                input.append(lines+"\n");
            history=input.toString().split("\n",-2);
//            sbr.delete(0,sbr.length());
        }
        catch (FileNotFoundException e)
        {
            input=new StringBuffer("") ;
            Toast.makeText(History.this,"No such class is found !",Toast.LENGTH_SHORT).show();
            finish();
//            System.exit(0);
//            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void clearData()// comment this
    {
        try {
            if(history!=null && history.length>0) {
                String alarmdate = "";
                StringBuffer sb=new StringBuffer("");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date presentdate = new Date();
                Date tryDate;
                String each[];
                for (int i = 0; i < history.length; i++) {
                    if (history[i].length()>1 && history[i].charAt(0) == '#')
                    {
                        each=history[i].split(" ",0);
                        each[0]=each[0].substring(1);
                        int day = Integer.parseInt(each[0].substring(0,each[0].indexOf("-")));
                        each[0]=(day<=9?"0":"")+each[0];
                        int mon = Integer.parseInt(each[0].substring(
                                each[0].indexOf("-") + 1, each[0].lastIndexOf("-")));
                        if (mon <= 9)
                            alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                    + "0" + each[0].substring(each[0].indexOf("-") + 1))
                                    + " " + each[1].substring(0, each[1].lastIndexOf(":"));
                        else
                            alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                    + each[0].substring(each[0].indexOf("-") + 1))
                                    + " " + each[1].substring(0, each[1].lastIndexOf(":"));

                    }
                    else if(history[i].length()>1){
                        each = history[i].split(";", 0);
                        int day = Integer.parseInt(each[0].substring(0,each[0].indexOf("-")));
                        each[0]=(day<=9?"0":"")+each[0];
                        int mon = Integer.parseInt(each[0].substring(
                                each[0].indexOf("-") + 1, each[0].lastIndexOf("-")));
                        if (mon <= 9)
                            alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                    + "0" + each[0].substring(each[0].indexOf("-") + 1))
                                    + " " + each[1].substring(0, each[1].indexOf("-") - 1);
                        else
                            alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                    + each[0].substring(each[0].indexOf("-") + 1))
                                    + " " + each[1].substring(0, each[1].indexOf("-") - 1);
                        }
                    tryDate = format.parse(alarmdate);
                    tryDate =(new SimpleDateFormat("ss")).parse(
                            String.valueOf((tryDate.getTime()+historydate*24*3600*1000)/1000));
                    if (tryDate.after(presentdate)) {
                        sb.append(history[i]+"\n");
                    }
                }
                write(sb);
                Toast.makeText(History.this,"Writing file is complete",Toast.LENGTH_SHORT).show();
            }
        }catch (ParseException p)
        {
            Toast.makeText(History.this, "Parse Exception occured "+p, Toast.LENGTH_SHORT).show();
            p.printStackTrace();
        }
        catch(Exception e)
        {
            Toast.makeText(History.this, "Other than parse exception "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void write(StringBuffer sb)// delete this
    {
        try {
            FileOutputStream fileOutputStream= openFileOutput(filename,MODE_PRIVATE);//openFileOutput(filename,MODE_PRIVATE);
            fileOutputStream.write((sb.toString()).getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(History.this,"No such file is created !",Toast.LENGTH_SHORT).show();
//            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
