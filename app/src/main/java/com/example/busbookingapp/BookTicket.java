package com.example.busbookingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class BookTicket extends AppCompatActivity {

    private static Button setDate,seats,set,history;
    private static AutoCompleteTextView dest;
    private static TextView price;
    private static RadioGroup slot;
    static final int DIALOG_ID_Date=0;
    int year_x,month_x,day_x,Slot_num;
    private static String Date,Dest,name,ID,Slot;
    MediaPlayer start,denied,accessed,newacc;
    String []Destination={"Raipur City","Raipur RailWay Station","Ambhuja Mall","Mata Ghatrani Temple","Bhilai Steel Plant",
            "IIT Bhilai New Campus","Santoshi Nagar","NIT Raipur","Ghari Chawk","Water Park"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        Intent getintent=getIntent();
        name=getintent.getStringExtra("name");
        ID=getintent.getStringExtra("id");
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
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        setDate=(Button)findViewById(R.id.setDate);
        seats=(Button)findViewById(R.id.seats);
        set=(Button)findViewById(R.id.set);
        dest=(AutoCompleteTextView)findViewById(R.id.dest);
        price=(TextView)findViewById(R.id.price);
        slot=(RadioGroup)findViewById(R.id.slot);
        history=(Button)findViewById(R.id.history);
        seats.setEnabled(false);
        seats.setVisibility(View.INVISIBLE);
        ArrayAdapter subadapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,Destination);
        dest.setThreshold(1);
        dest.setAdapter(subadapter);
        //int pr= Take reconfigurable price from server
//        price.setText(pr);
        denied=MediaPlayer.create(BookTicket.this,R.raw.denied);
        accessed=MediaPlayer.create(BookTicket.this,R.raw.accessed);
        newacc=MediaPlayer.create(BookTicket.this,R.raw.newacc);
        setOnClickListener();

    }
    public void setOnClickListener()
    {
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a_builder=new AlertDialog.Builder(BookTicket.this);
                a_builder.setMessage("Are u sure to have finalised the slot ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                try {
                                    Slot_num = slot.getCheckedRadioButtonId();
                                    if (Slot_num == -1)
                                        Toast.makeText(BookTicket.this, "Please Select A RadioButton! Every details are mandatory.", Toast.LENGTH_LONG).show();
                                    else {
                                        Slot=((RadioButton) findViewById(Slot_num)).getText().toString();
                                        Dest = dest.getText().toString();
                                        if (!Dest.equals("")) {
                                            Date=day_x+"-"+(month_x+1)+"-"+year_x;
                                            String mnth = String.valueOf(month_x + 1);
                                            if(month_x<9)
                                                mnth = "0" + mnth;
                                            String date_s = "" + year_x + "-" + mnth + "-" + day_x + " " +Slot.substring(0,Slot.indexOf("-")-1);
                                            Date date = new Date();
                                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
                                            if (!date.before(dt.parse(date_s)))
                                                Toast.makeText(BookTicket.this, "Give a later date and than present !!", Toast.LENGTH_LONG).show();
                                            else {
                                                set.setEnabled(false);
                                                set.setVisibility(View.INVISIBLE);
                                                seats.setEnabled(true);
                                                seats.setVisibility(View.VISIBLE);
                                                MessageSender ms = new MessageSender();
                                                ms.execute(Date + ";" + Slot);
                                                if(newacc.isPlaying())
                                                    newacc.stop();
                                                newacc.start();
                                            }
                                        }
                                        else
                                            Toast.makeText(BookTicket.this,"Every field is mandatory",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(BookTicket.this,"Values not set.",Toast.LENGTH_SHORT).show();
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
                alert.setTitle("Set The Timings !!!");
                alert.show();
            }
        });
        seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    if(accessed.isPlaying())
                        accessed.stop();
                    accessed.start();
                            // Check condition if the day is not a weekend.
                            Toast.makeText(BookTicket.this, Dest + " " + Date + " " + Slot, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BookTicket.this, SelectSeats.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id", ID);
                            intent.putExtra("day", day_x);
                            intent.putExtra("month", month_x+1);
                            intent.putExtra("year", year_x);
//                            intent.putExtra("dest", Dest);
                            intent.putExtra("slot", Slot);
                            intent.putExtra("price",price.getText().toString());
                            startActivity(intent);
                            set.setEnabled(true);
                            set.setVisibility(View.VISIBLE);
                            seats.setEnabled(false);
                            seats.setVisibility(View.INVISIBLE);
//                            finish();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(denied.isPlaying())
                    denied.stop();
                denied.start();
                Intent intent=new Intent(BookTicket.this,History.class);
                startActivity(intent);
            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID_Date);
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
                s = new Socket("10.2.64.198", 7801);
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                pw.println(message);
                pw.flush();
                if (!message.equals("-1")){
                    while (true) {
                        input = br.readLine();
                        break;
                    }
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        if (input!=null && !input.equals("-1"))
                            price.setText(input);
                    }
                });
            }
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
    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DIALOG_ID_Date)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return  null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            try {
                year_x = year;
                month_x = month;
                day_x = day;
                Toast.makeText(BookTicket.this, "Date set:- "+day_x + "/" + (month_x + 1) + "/" + year_x, Toast.LENGTH_SHORT).show();
//                if(flag==1)
//                {
//                    Set.setEnabled(true);
//                    Set.setVisibility(View.VISIBLE);
//                }
//                flag=2;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };
    @Override
    public void onBackPressed() {
        MessageSender phi=new MessageSender();
        phi.execute("-1");
        finish();
    }
}
