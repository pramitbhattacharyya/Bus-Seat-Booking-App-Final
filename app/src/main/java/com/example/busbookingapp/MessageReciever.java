package com.example.busbookingapp;

//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.view.View;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//public class MessageReciever extends AsyncTask<String, Void, String> {
////        Thread rcvthread;
//
//    String test[];
//
//    MessageReciever(String []message) {
//        this.test=message;
////        while(test.equals("Hi"));
//    }
//    Socket s=null;
//    //    ServerSocket ss;
//    InputStreamReader isr;
//    BufferedReader br;
////        Handler h = new Handler();
////        String message;
//    //            ss = new ServerSocket(7801);
////    public String recieve() {
////        try{
////            s=new Socket("192.168.43.241",7800);
////            isr = new InputStreamReader(s.getInputStream());
////            br = new BufferedReader(isr);
////        while (true) {
////            message = br.readLine();
////            break;
////        }
////            br.close();
////            isr.close();
////            s.close();
////        }catch(IOException e)
////        {
////            e.printStackTrace();
////        }
////        return message;
////    }
//    @Override
//    protected String doInBackground(String... voids) {
//        String mess = "";
//        try {
//            s = new Socket("10.2.64.198", 7800);
//            isr = new InputStreamReader(s.getInputStream());
//            br = new BufferedReader(isr);
//            while (true) {
//                mess = br.readLine();
//                break;
//            }
//            br.close();
//            isr.close();
//            s.close();
////                h.post(new Runnable() {
////                    @Override
////                    public void run() {
////                        return message;
////                    }
////                });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return mess;
//    }
//
//    @Override
//    protected void onPostExecute(String mess) {
//        test[0] = mess;
//    }
//}
//
//
////    public void start(View view)
////    {
////        Socket s;
////        //    ServerSocket ss;
////        InputStreamReader isr;
////        BufferedReader br;
////            Handler h=new Handler();
//////        String message;
////        rcvthread=new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    s = new Socket("192.168.43.241", 7800);
////                    isr = new InputStreamReader(s.getInputStream());
////                    br = new BufferedReader(isr);
////                    while (true) {
////                        message = br.readLine();
////                        break;
////                    }
////                    br.close();
////                    isr.close();
////                    s.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////        }
////    }
//
//

//import android.os.AsyncTask;
//import android.widget.TextView;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class Client extends AsyncTask<Void, Void, Void> {
//
//    String dstAddress;
//    int dstPort;
//    String response = "";
//    TextView textResponse;
//
//    Client(String addr, int port, TextView textResponse) {
//        dstAddress = addr;
//        dstPort = port;
//        this.textResponse = textResponse;
//    }
//
//    @Override
//    protected Void doInBackground(Void... arg0) {
//
//        Socket socket = null;
//
//        try {
//            socket = new Socket(dstAddress, dstPort);
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
//                    1024);
//            byte[] buffer = new byte[1024];
//
//            int bytesRead;
//            InputStream inputStream = socket.getInputStream();
//
//            /*
//             * notice: inputStream.read() will block if no data return
//             */
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, bytesRead);
//                response += byteArrayOutputStream.toString("UTF-8");
//            }
//
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response = "UnknownHostException: " + e.toString();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response = "IOException: " + e.toString();
//        } finally {
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
////        return response;
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        textResponse.setText(response);
//        super.onPostExecute(result);
//    }
//
//}
