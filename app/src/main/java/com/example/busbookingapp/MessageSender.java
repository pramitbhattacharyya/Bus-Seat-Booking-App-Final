//package com.example.busbookingapp;
//
//import android.os.AsyncTask;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class MessageSender extends AsyncTask<String,Void,Void> {
//    Socket s;
////    DataOutputStream dos;
//    PrintWriter pw;
//    @Override
//    protected Void doInBackground(String... voids) {
//        String message=voids[0];
//        try {
//            s=new Socket("10.2.64.198",7800);
//            pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
//            pw.println(message);
//            pw.flush();
//            pw.close();
//            s.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//}