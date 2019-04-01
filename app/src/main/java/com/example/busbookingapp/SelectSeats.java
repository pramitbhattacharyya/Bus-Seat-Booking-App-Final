package com.example.busbookingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SelectSeats extends AppCompatActivity implements Dialog_box.Dialog_boxListener {
    private String Dia_name="",Dia_ID="",slot="",dest="",name="",date="",ID="";
    private String Seat_Id[]=new String[48];
    private int flag=0,num=0;
    private String Seat_name[]=new String[48];
    private String []results;
    private int count[]=new int [48];
    private StringBuffer input;
    private  String values[], each[];
    private Button seats[]=new Button[48];
    private String filename="History2.txt";
    MediaPlayer newacc;
    private Button pay,confirm;
    public int year_x,month_x,day_x,hour_x,min_x,sec_x,day,mon,year;
    String checkSumHash = "";
    String mobile_no="7777777777",email="pramitb@iitbhilai.ac.in";
    //getting the tax amount first.
    String txnAmount ;
    Paytm paytm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seats);
        final Calendar cal=Calendar.getInstance();
        // Current date & time of booking
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        hour_x=cal.get(Calendar.HOUR_OF_DAY);
        min_x=cal.get(Calendar.MINUTE);
        sec_x=cal.get(Calendar.SECOND);
        // Values of booking schedule
        Intent getintent = getIntent();
//        dest = getintent.getStringExtra("dest");
        day= getintent.getIntExtra("day",day_x);
        mon=getintent.getIntExtra("month",month_x);
        year=getintent.getIntExtra("year",year_x);
        slot=getintent.getStringExtra("slot");
        name=getintent.getStringExtra("name");
        ID=getintent.getStringExtra("id");
        txnAmount=getintent.getStringExtra("price");
        newacc=MediaPlayer.create(SelectSeats.this,R.raw.newacc);
        Toast.makeText(SelectSeats.this,"name= "+name+" ID= "+ID+" "+num,Toast.LENGTH_SHORT).show();
        seats[0]=(Button)findViewById(R.id.btn1);
        seats[1]=(Button)findViewById(R.id.btn2);
        seats[2]=(Button)findViewById(R.id.btn3);
        seats[3]=(Button)findViewById(R.id.btn4);
        seats[4]=(Button)findViewById(R.id.btn5);
        seats[5]=(Button)findViewById(R.id.btn6);
        seats[6]=(Button)findViewById(R.id.btn7);
        seats[7]=(Button)findViewById(R.id.btn8);
        seats[8]=(Button)findViewById(R.id.btn9);
        seats[9]=(Button)findViewById(R.id.btn10);
        seats[10]=(Button)findViewById(R.id.btn11);
        seats[11]=(Button)findViewById(R.id.btn12);
        seats[12]=(Button)findViewById(R.id.btn13);
        seats[13]=(Button)findViewById(R.id.btn14);
        seats[14]=(Button)findViewById(R.id.btn15);
        seats[15]=(Button)findViewById(R.id.btn16);
        seats[16]=(Button)findViewById(R.id.btn17);
        seats[17]=(Button)findViewById(R.id.btn18);
        seats[18]=(Button)findViewById(R.id.btn19);
        seats[19]=(Button)findViewById(R.id.btn20);
        seats[20]=(Button)findViewById(R.id.btn21);
        seats[21]=(Button)findViewById(R.id.btn22);
        seats[22]=(Button)findViewById(R.id.btn23);
        seats[23]=(Button)findViewById(R.id.btn24);
        seats[24]=(Button)findViewById(R.id.btn25);
        seats[25]=(Button)findViewById(R.id.btn26);
        seats[26]=(Button)findViewById(R.id.btn27);
        seats[27]=(Button)findViewById(R.id.btn28);
        seats[28]=(Button)findViewById(R.id.btn29);
        seats[29]=(Button)findViewById(R.id.btn30);
        seats[30]=(Button)findViewById(R.id.btn31);
        seats[31]=(Button)findViewById(R.id.btn32);
        seats[32]=(Button)findViewById(R.id.btn33);
        seats[33]=(Button)findViewById(R.id.btn34);
        seats[34]=(Button)findViewById(R.id.btn35);
        seats[35]=(Button)findViewById(R.id.btn36);
        seats[36]=(Button)findViewById(R.id.btn37);
        seats[37]=(Button)findViewById(R.id.btn38);
        seats[38]=(Button)findViewById(R.id.btn39);
        seats[39]=(Button)findViewById(R.id.btn40);
        seats[40]=(Button)findViewById(R.id.btn41);
        seats[41]=(Button)findViewById(R.id.btn42);
        seats[42]=(Button)findViewById(R.id.btn43);
        seats[43]=(Button)findViewById(R.id.btn44);
        seats[44]=(Button)findViewById(R.id.btn45);
        seats[45]=(Button)findViewById(R.id.btn46);
        seats[46]=(Button)findViewById(R.id.btn47);
        seats[47]=(Button)findViewById(R.id.btn48);
        date=day+"-"+mon+"-"+year;
        pay=(Button)findViewById(R.id.pay);
        confirm=(Button)findViewById(R.id.confirm);
        confirm.setEnabled(false);
        confirm.setVisibility(View.INVISIBLE);
        flag=1;
        for(int i=0;i<48;i++)
        {
            Seat_Id[i]="";
            seats[i].setBackgroundColor(Color.parseColor("#00FF00"));
            Seat_name[i]="";
        }
        // Fill Entry of Seats from the database.
        MessageSender ms=new MessageSender();
        ms.execute(date+";"+slot);
//        read();
//        if(!(input.toString().equals(""))) {
//            int k;
//            values = input.toString().split("\n",0);
//            for(String str : values) {
//                if(str.charAt(0)=='#')
//                    continue;
//                each = str.split(" ", 0);
//                k=Integer.parseInt(each[each.length-1]);
//                k--;
//                Seat_Id[k] = each[0];
//                Seat_name[k]=str.substring(str.indexOf(" ")+1,str.lastIndexOf(" "));
//                seats[k].setBackgroundColor(Color.parseColor("#FF0000"));
//            }
//        }
        setOnClickListener();
    }
    public void setOnClickListener()
    {
        seats[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               testandset(0);
            }
        });
        seats[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(1);
            }
        });
        seats[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(2);
            }
        });
        seats[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(3);
            }
        });
        seats[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(4);
            }
        });
        seats[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              testandset(5);
            }
        });
        seats[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(6);
            }
        });
        seats[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(7);
            }
        });
        seats[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(8);
            }
        });
        seats[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(9);
            }
        });
        seats[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(10);
            }
        });
        seats[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(11);
            }
        });
        seats[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(12);
            }
        });
        seats[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(13);
            }
        });
        seats[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(14);
            }
        });
        seats[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(15);
            }
        });
        seats[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(16);
            }
        });
        seats[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(17);
            }
        });
        seats[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(18);
            }
        });
        seats[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(19);
            }
        });
        seats[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(20);
            }
        });
        seats[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(21);
            }
        });
        seats[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(22);
            }
        });
        seats[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(23);
            }
        });
        seats[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(24);
            }
        });
        seats[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(25);
            }
        });
        seats[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(26);
            }
        });
        seats[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(27);
            }
        });
        seats[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(28);
            }
        });
        seats[29].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(29);
            }
        });
        seats[30].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(30);
            }
        });
        seats[31].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(31);
            }
        });
        seats[32].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(32);
            }
        });
        seats[33].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(33);
            }
        });
        seats[34].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(34);
            }
        });
        seats[35].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(35);
            }
        });
        seats[36].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(36);
            }
        });
        seats[37].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(37);
            }
        });
        seats[38].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(38);
            }
        });
        seats[39].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(39);
            }
        });
        seats[40].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(40);
            }
        });
        seats[41].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(41);
            }
        });
        seats[42].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(42);
            }
        });
        seats[43].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(43);
            }
        });
        seats[44].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(44);
            }
        });
        seats[45].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(45);
            }
        });
        seats[46].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(46);
            }
        });
        seats[47].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testandset(47);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Attach online payment systems.
                if(num>0)
                generateCheckSum();
                else
                    Toast.makeText(SelectSeats.this,"No seat is selected.",Toast.LENGTH_SHORT).show();
                //After payment is successful
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectSeats.this,History.class);
                startActivity(intent);
            }
        });
    }
    public void openDialog(){
        Dialog_box dialog_box=new Dialog_box();
        dialog_box.show(getSupportFragmentManager(),"Dialog_Box");
    }
    @Override
    public void applyTexts(String Name, String Id) {
        // Do whatever to do with name and Id.
        Dia_name=Name;
        Dia_ID=Id;
//        for(int i=0;i<48;i++)
//        {
//            if(!Seat_Id[i].equals("")) // check if idno is valid
//                seats[i].setBackgroundColor(Color.parseColor("#FF0000"));
//        }
    }
    public void testandset(int i)
    {
        if(!Dia_ID.equals("")&&Dia_ID.length()==8&&Seat_Id[i].equals("")) // check if idno is valid
        {
            Seat_Id[i] = Dia_ID;
            Seat_name[i]=Dia_name;
            count[i]=1;
            seats[i].setBackgroundColor(Color.parseColor("#FF0000"));
            num++;
            Dia_ID="";
            Dia_name="";
            if(newacc.isPlaying())
                newacc.stop();
            newacc.start();
        }
        if(Seat_Id[i].equals(""))
            openDialog();
        if(Seat_Id[i].length()==8)
            Toast.makeText(SelectSeats.this, Seat_Id[i],Toast.LENGTH_SHORT).show();
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
//            input=sbr.toString().split(";",-2);
//            sbr.delete(0,sbr.length());
        }
        catch (FileNotFoundException e)
        {
            input=new StringBuffer("") ;
            Toast.makeText(SelectSeats.this,"No such class is found !",Toast.LENGTH_SHORT).show();
//            System.exit(0);
//            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void write()// ";" to be changed to " "
    {
        StringBuffer sb=new StringBuffer("#"+day_x+"-"+(month_x+1)+"-"+year_x+" "+hour_x+":"+min_x+":"+sec_x+"\n");
        for(int j=0;j<48;j++)
            if(count[j]==1)
                sb.append(date+";"+slot+";"+Seat_Id[j]+";"+Seat_name[j]+";"+(j+1)+"\n");
            sb.append(input.toString());
        try {
            FileOutputStream fileOutputStream= openFileOutput(filename,MODE_PRIVATE);//openFileOutput(filename,MODE_PRIVATE);
            fileOutputStream.write(sb.toString().getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(SelectSeats.this,"No such class is created !",Toast.LENGTH_SHORT).show();
//            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally {
            buildAlarm(sb);
        }

    }

    public void buildAlarm(StringBuffer sb)// to be commented out
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
                                + " " + each[1].substring(0, each[1].indexOf("-") - 1);
                    else
                        alarmdate = (each[0].substring(0, each[0].indexOf("-") + 1)
                                + each[0].substring(each[0].indexOf("-") + 1))
                                + " " + each[1].substring(0, each[1].indexOf("-") - 1);
                    tryDate = format.parse(alarmdate);
//                        tryDate = (new SimpleDateFormat("ss")).parse(
//                                String.valueOf((long)((tryDate.getTime()-(30*60*1000))/1000))
//                        );
                    tryDate=new Date(tryDate.getTime()-30*60*1000);
                    Toast.makeText(SelectSeats.this,"tryDate= "+tryDate,Toast.LENGTH_SHORT).show();
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
                    setAlarm(setDate, "You have a bus ride within 30 minutes.");
                    Toast.makeText(SelectSeats.this,"Alarm is set",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (ParseException p)
        {
            Toast.makeText(SelectSeats.this, "Parse Exception occured "+p, Toast.LENGTH_SHORT).show();
            p.printStackTrace();
        }
        catch(Exception e)
        {
            Toast.makeText(SelectSeats.this, "Other than parse exception "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setAlarm(Date date, String sub)// to be commented out
    {
        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //  int time = Integer.parseInt(edTime.getText().toString());
//            String date1 = edTimes.getText().toString();

            Date date2 = new Date();

//            Date date = format.parse(date1);

            long time = date.getTime() - date2.getTime();
//            Toast.makeText(Set_HomeWork.this, "Alarm Set" +(time/1000)+ " seconds later", Toast.LENGTH_LONG).show();
            Intent ip = new Intent(SelectSeats.this, TTReciever.class);
            PendingIntent pii = PendingIntent.getBroadcast(getApplicationContext(), 0, ip, 0);
            AlarmManager ams = (AlarmManager) getSystemService(ALARM_SERVICE);
            ams.set(AlarmManager.RTC_WAKEUP,date.getTime(),pii);
            Toast.makeText(SelectSeats.this,"date= "+date.toString(),Toast.LENGTH_SHORT).show();
//            ams.set();
//                    am.setExact(AlarmManager.RTC_WAKEUP,time,"Good",);
        } catch (Exception e)
        {
            Toast.makeText(SelectSeats.this, "Alarm Not Set "+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        read();
//        write();
        showAlert();
//        super.onBackPressed();
    }
    public void showAlert()
    {
        AlertDialog.Builder a_builder=new AlertDialog.Builder(SelectSeats.this);
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
                                Toast.makeText(SelectSeats.this,"Going back",Toast.LENGTH_SHORT).show();
                                MessageSender2 phi=new MessageSender2();
                                phi.execute("-1");
//                            }
                            finish();
                        }catch (Exception e){
                            Toast.makeText(SelectSeats.this,"Error.",Toast.LENGTH_SHORT).show();
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
    public void postSuccess()
    {
        pay.setEnabled(false);
        pay.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.VISIBLE);
        confirm.setEnabled(true);
        read();
        write();
        flag=1;
        MessageSender2 ms2=new MessageSender2();
        StringBuffer sbv=new StringBuffer("");
        for(int i=0;i<48;i++) {
            if (count[i] == 1)
                sbv.append(date + "," + slot + "," + (i+1) + "," + Seat_Id[i] + "," + Seat_name[i] + "," + name + "," + ID + ";");
        }
        if(sbv.toString().equals(""))
            ms2.execute("-1");
        else
            ms2.execute(sbv.toString());
        Intent intent=new Intent(SelectSeats.this,History.class);
        startActivity(intent);
        finish();
    }
    private void generateCheckSum() {

//        String mobile_no="8777266581",email="pramitb@iitbhilai.ac.in";
        //getting the tax amount first.
//        txnAmount = textViewPrice.getText().toString().trim();

        txnAmount=String.valueOf(Integer.parseInt(txnAmount)*num);
        //creating paytm object
        //containing all the values required
        paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );
        MessageSender3 ms3=new MessageSender3();
        String send=Constants.M_ID+";"+paytm.getOrderId()+";"+paytm.getChannelId()+";"+paytm.getCustId()+";"+mobile_no+";"
                +email+";"+paytm.getTxnAmount()+";"+paytm.getWebsite()+";"+paytm.getIndustryTypeId();
        ms3.execute(send);
        //***************************
        // Code to send values of Paytm along with txnAmount to server.
        //Get CheckSum as string.
        //******************************************************

    }
    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());

        Toast.makeText(SelectSeats.this,Constants.M_ID+"\n"+paytm.getOrderId()+"\n"
                +paytm.getCustId()+"\n"+paytm.getChannelId()+"\n"+paytm.getTxnAmount()+"\n"+paytm.getWebsite()+"\n"+
                paytm.getCallBackUrl()+"\n"+checksumHash+"\n"+paytm.getIndustryTypeId(),Toast.LENGTH_LONG).show();
        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Toast.makeText(SelectSeats.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onTransactionResponse(Bundle inResponse) {
                String Status="",Respmsg="";
                try {
                    JSONObject jObj = new JSONObject(inResponse.toString());
                    Status=jObj.has("STATUS")?jObj.getString("STATUS"):"HI";
                    Respmsg=jObj.has("RESPMSG")?jObj.getString("RESPMSG"):"BYE";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(MainActivity.this, "HELLO : "+inResponse.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this,Status+" "+Respmsg,Toast.LENGTH_LONG).show();
                Status=inResponse.toString();
                Toast.makeText(SelectSeats.this,Status.substring(Status.indexOf("=")+1,Status.indexOf(",")),Toast.LENGTH_LONG).show();
                Toast.makeText(SelectSeats.this,Status.substring(Status.lastIndexOf("=")+1,Status.lastIndexOf("}")),Toast.LENGTH_LONG).show();
                if(Status.substring(Status.indexOf("=")+1,Status.indexOf(",")).equals("TXN_SUCCESS")
                        &&Status.substring(Status.lastIndexOf("=")+1,Status.lastIndexOf("}")).equals("Txn Success"))
                    postSuccess();
                finish();
            }
            public void networkNotAvailable() {
                Toast.makeText(SelectSeats.this, "Network error", Toast.LENGTH_LONG).show();
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                Toast.makeText(SelectSeats.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Toast.makeText(SelectSeats.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onBackPressedCancelTransaction() {
                Toast.makeText(SelectSeats.this, "Back Pressed", Toast.LENGTH_LONG).show();
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Toast.makeText(SelectSeats.this, inErrorMessage + inResponse.toString(), Toast.LENGTH_LONG).show();
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
                while (true) {
                    input = br.readLine();
                    break;
                }
                if(!input.equals("-1")) {
                    results = input.split(";", 0);
                    input = "";
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < results.length; i += 2) {
                                input += results[i] + " ";
                                Seat_Id[Integer.parseInt(results[i]) - 1] = results[i + 1];
                                seats[Integer.parseInt(results[i]) - 1].setBackgroundColor(Color.parseColor("#FF0000"));
                            }
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
                s=new Socket("10.2.64.198",7801);
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
//                h=new Handler();
//                h.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(input.equals("#EOF"))
//                            finish();
//                    }
//                });
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
    public class MessageSender3 extends AsyncTask<String,Void,String> {
        Socket s;
        PrintWriter pw;
        BufferedReader br;
        @Override
        protected String doInBackground(String... voids) {
            String message=voids[0];
            String input="";
            try {
                s = new Socket("10.2.64.198",7802);
                pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw.println(message);
                pw.flush();
                while(true)
                {
                    input=br.readLine();
                    if(!input.equals(""))
                        break;
                }
                checkSumHash=input.trim();
                br.close();
                pw.close();
                s.close();
                return  input;
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            initializePaytmPayment(checkSumHash, paytm);
        }
    }
}
