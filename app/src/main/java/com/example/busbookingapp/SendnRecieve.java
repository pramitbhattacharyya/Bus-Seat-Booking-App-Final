package com.example.busbookingapp;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendnRecieve extends AsyncTask<String,Void,Void> {
    Socket s;
    //    DataOutputStream dos;
    PrintWriter pw;
    BufferedReader br;
    @Override
    protected Void doInBackground(String... voids) {
        String message=voids[0];
        String input="";
        try {
            s = new Socket("10.1.7.39",7800);
            pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw.println(message);
            pw.flush();
            while(true)
            {
                input=br.readLine();
                break;
            }
//            pw.println("You sent:"+input);
//            pw.flush();
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
