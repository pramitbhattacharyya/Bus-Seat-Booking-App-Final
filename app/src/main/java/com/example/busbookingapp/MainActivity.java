package com.example.busbookingapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements Verify_dialog_box.Verify_dialog_boxListener{

    private static Button login,verify;
    private static EditText name, id,  email, pass,code;
    private static RadioGroup dept, course;
    private static RadioButton deptSlct, courseSlct;
    private static String Name, ID, Year, Email, Pass,getCode="Hi";
    private static int k=0, tries=0;
    AutoCompleteTextView year;
    MediaPlayer start,denied,accessed,newacc;
    private static String []yearcont={"2020","2021","2022","2023","2024","2025","2026","2027","2028","2029"};
//    private static TextView Code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.Login);
        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        year = (AutoCompleteTextView) findViewById(R.id.year);
        email = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        dept = (RadioGroup) findViewById(R.id.dept);
        course = (RadioGroup) findViewById(R.id.course);
        start=MediaPlayer.create(MainActivity.this,R.raw.start);
        denied=MediaPlayer.create(MainActivity.this,R.raw.denied);
        accessed=MediaPlayer.create(MainActivity.this,R.raw.accessed);
        newacc=MediaPlayer.create(MainActivity.this,R.raw.newacc);
        ArrayAdapter subadapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,yearcont);
        year.setThreshold(1);
        year.setAdapter(subadapter);
//        verify=(Button)findViewById(R.id.verify);
//        Code=(TextView)findViewById(R.id.Code);
//        code=(EditText)findViewById(R.id.code);
//        verify.setEnabled(false);
//        verify.setVisibility(View.INVISIBLE);
//        Code.setEnabled(false);
//        Code.setVisibility(View.INVISIBLE);
//        code.setEnabled(false);
//        code.setVisibility(View.INVISIBLE);
//        message = new String[1];
//        message[0] = "Hi";
        if(start.isPlaying())
            start.stop();
        start.start();
        setOnClickListener();
    }
    public void openDialog(){
        Verify_dialog_box verify_dialog_box=new Verify_dialog_box();
        verify_dialog_box.show(getSupportFragmentManager(),"Email Verification Dialog_Box");
    }
    public void applyTexts(String Name) {
        // Do whatever to do with name and Id.
        getCode=Name;
//        Intent intent;
//        try {
//            if (Name.equals(input)) {
//                intent = new Intent(MainActivity.this, BookTicket.class);
//                startActivity(intent);
//                pw.println("1");
//                pw.flush();
//            } else {
//                Toast.makeText(MainActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
//                pw.println("-1");
//                pw.flush();
//            }
//            pw.close();
//            s.close();
//        }catch(IOException e)
//        {
//            e.printStackTrace();
//        }
    }
    public void setOnClickListener() {
        try {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ///
//                    Name = name.getText().toString();
//                    ID = id.getText().toString();
//                    Intent leap = new Intent(MainActivity.this, BookTicket.class);
//                    leap.putExtra("name",Name);
//                    leap.putExtra("id",ID);
//                    startActivity(leap);
                    ///
                    int dept_id = dept.getCheckedRadioButtonId();
                    int course_id = course.getCheckedRadioButtonId();
                    if (dept_id == -1 || course_id == -1)
                        Toast.makeText(MainActivity.this, "Please Select A RadioButton! Every details are mandatory.", Toast.LENGTH_LONG).show();
                    else {
                        deptSlct = (RadioButton) findViewById(dept_id);
                        courseSlct = (RadioButton) findViewById(course_id);
                        Name = name.getText().toString();
                        ID = id.getText().toString();
                        Year = year.getText().toString();
                        Email = email.getText().toString();
                        Pass = pass.getText().toString();
//                        Toast.makeText(MainActivity.this,Pass,Toast.LENGTH_LONG).show();
                        int index = Email.indexOf('@');
//                        if(index==-1)
//
                        if (index != -1) {
                            String dom = Email.substring(index);
                            if (!dom.equals("@iitbhilai.ac.in"))
                                Toast.makeText(MainActivity.this, "Use Institute Email ID and without extra spaces.", Toast.LENGTH_LONG).show();
                            else if (Pass.length() < 8)
                                Toast.makeText(MainActivity.this, "Password should be of atleast 8 characters.", Toast.LENGTH_LONG).show();
                            else if (Name.equals("") || ID.equals("") || Year.equals("") || Email.equals("") || Pass.equals(""))
                                Toast.makeText(MainActivity.this, "Every details are mandatory.", Toast.LENGTH_LONG).show();
                            else {
                                int PassHash = Pass.hashCode();
                                // Send the given info to the server to verify email address.
                                MessageSender ms = new MessageSender();
                                ms.execute(ID + ";" + Name + ";" + deptSlct.getText().toString() + ";" + courseSlct.getText().toString() + ";" + Year + ";" + Email + ";" + String.valueOf(PassHash));
                                Toast.makeText(MainActivity.this, "Sent to server", Toast.LENGTH_SHORT).show();
//                                MessageReciever mr=new MessageReciever(message);
//                                mr.execute();
//                                message=mr.test;
//                                while(true){
//                                    if (!message[0].equals("Hi"))
//                                        break;
//                                }
//                                try {
//                                    while (true) {
//                                        input = br.readLine();
//                                        break;
//                                    }
//                                    br.close();
//                                    pw.close();
//                                    s.close();
//                                    Toast.makeText(MainActivity.this,input,Toast.LENGTH_SHORT).show();
//                                }catch (IOException e)
//                                {
//                                    Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
//                                }
                                // if the details of the user is already present then login(searched by IDNo), otherwise make new entries to
                                // the database.
                                // if already present then match the data from the database by idno. and return 1 if matched.
//                                Thread myThread =new Thread(new MyServerThread());
//                                myThread.start();
//                                int rtn = Integer.parseInt(message); // this will recieve the return value after verification.
                                // 1 if already present and matched.
                                // 0 if new signup.
                                // -1 if already present but not matched.
//                                if (message.equals("1")) {
//                                    Intent intent = new Intent(MainActivity.this, BookTicket.class);
//                                    startActivity(intent);
//                                    // go to book ticket activity.
//                                }
//                                else if (message.equals("-1")) {
//                                    Toast.makeText(MainActivity.this, "The details provided are invalid/incorrect. Please recheck are try again.", Toast.LENGTH_LONG).show();
//                                    pass.setText("");
//                                    id.setText("");
//                                    name.setText("");
//                                    year.setText("");
//                                    email.setText("");
//                                }
//                                else {
//                                    Toast.makeText(MainActivity.this,"Going to verify"+message,Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(MainActivity.this, VerifyEmail.class);
//                                    intent.putExtra("message", message);
//                                    startActivity(intent);
//                                    // go to verification activity.
//                                }
                            }
                        } else
                            Toast.makeText(MainActivity.this, "Invalid Email-Id.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error Occured.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    class MessageSender  extends AsyncTask<String, Void, String>{
        Socket s;
         //    DataOutputStream dos;
         PrintWriter pw;
         BufferedReader br;
         Handler h=new Handler();
         String input = "";
         @Override
        protected String doInBackground(String... voids) {
            String message = voids[0];
            try {
                s = new Socket("10.2.64.198", 7800);
                pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw.println(message);
                pw.flush();
                while (true) {
                    input = br.readLine();
                    break;
                }
//                br.close();
//                pw.close();
//                s.close();
                Intent intent;
                if(input.equals("1")) {
                    if(accessed.isPlaying())
                        accessed.stop();
                    accessed.start();
                    br.close();
                    pw.close();
                    s.close();
                    intent = new Intent(MainActivity.this, BookTicket.class);
                    intent.putExtra("name",Name);
                    intent.putExtra("id",ID);
                    startActivity(intent);
                }
                else if (input.equals("-1")) {
                    if(denied.isPlaying())
                        denied.stop();
                    denied.start();
//                    Handler h= new Handler();
//                    h.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this, "The details provided are invalid/incorrect. Please recheck are try again.", Toast.LENGTH_LONG).show();
//
//                        }
//                    });
                    pw.close();
                    br.close();
                    s.close();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"Incorrect Password. No. of tries left: "+(3-++tries),Toast.LENGTH_SHORT).show();
                            pass.setText("");
                            id.setText("");
                            name.setText("");
                            year.setText("");
                            email.setText("");
                            if(tries==3)
                            {
                                login.setEnabled(false);
                                login.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    Intent changePass=new Intent(MainActivity.this,ChangePassword.class);
                    changePass.putExtra("name",Name);
                    changePass.putExtra("id",ID);
                    startActivity(changePass);
                }
                else if(input.equals("5")) {
                    if(denied.isPlaying())
                        denied.stop();
                    denied.start();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"EmailId already registered with other IDNo. Please contact service provider.",Toast.LENGTH_SHORT).show();
                            pass.setText("");
                            id.setText("");
                            name.setText("");
                            year.setText("");
                            email.setText("");
                        }
                    });
                }
                else {
//                    Toast.makeText(MainActivity.this,"Going to verify"+message,Toast.LENGTH_SHORT).show();
//                    intent = new Intent(MainActivity.this, VerifyEmail.class);
//                    intent.putExtra("message", input);
//                    startActivity(intent);
                    // go to verification activity.
//                        login.setEnabled(false);
//                        login.setVisibility(View.INVISIBLE);

//                        verify.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        });
//                    if(k==0)
//                    {
//                        openDialog();
//                        k=1;
//                    }
                    if(newacc.isPlaying())
                        newacc.stop();
                    newacc.start();
                    openDialog();
                    while(getCode.equals("Hi"));
                    if (getCode.equals(input)) {
                        intent = new Intent(MainActivity.this, BookTicket.class);
                        intent.putExtra("name",Name);
                        intent.putExtra("id",ID);
                        pw.println("1");
                        pw.flush();
                        while (true) {
                            input = br.readLine();
                            break;
                        }
                        br.close();
                        pw.close();
                        s.close();
                        startActivity(intent);
                    } else {
//                        Toast.makeText(MainActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                        pw.println("-1");
                        pw.flush();
                        while (true) {
                            input = br.readLine();
                            break;
                        }
                        br.close();
                        pw.close();
                        s.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//            return input;
            return null;
        }
    }
//    class MyServerThread implements Runnable{
////        Socket s;
////        ServerSocket ss;
////        InputStreamReader isr;
////        BufferedReader br;
//        String message;
//        Handler h=new Handler();
//        @Override
//        public void run() {
//            try {
////                ss = new ServerSocket(7801);
//                while(true)
//                {
////                    s=ss.accept();
////                    isr=new InputStreamReader(s.getInputStream());
////                    br=new BufferedReader(isr);
//                    message=br.readLine();
//                    h.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
////                            sbr.append(message+"\n");
////                            listview();
////                            chat.setText(chat.getText().toString()+"\n"+message);
//                        }
//                    });
//                    break;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}


//}
