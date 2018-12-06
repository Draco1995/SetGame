package com.muxinzhi.www.tcphandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.muxinzhi.www.server.ServerRemote;
import com.muxinzhi.www.setgame.R;

/**
 * Created by MSI on 2018/3/23.
 */

public class TcpClient implements Runnable{
    private Socket s;
    Handler handler;
    Handler revHandler;
    BufferedReader br = null;
    OutputStream os = null;

    String ip;
    String port;
    ServerRemote sr;

    private void recievedMessage(String m){
        String[] res = m.split(":");
        if(res[0].equals("System")){
            Message msg = new Message();
            msg.what = 0;
            Bundle bundle = new Bundle();
            bundle.putString("text",res[1]);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }else if(res[0].equals("SCORE")){
            sr.addScore(res[1]);
        }else{
            sr.addResponse(m);
        }

    }

    public void sendMessage(String m){
        Message msg = new Message();
        msg.what = 0x345;
        msg.obj = m;
        while(revHandler == null){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace( );
            }
        }
        revHandler.sendMessage(msg);
    }

    private void sendMessageMain(String m){
        Message msg = new Message();
        msg.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("text",m);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    public TcpClient(Handler handler,String ip,String port, ServerRemote s) {
        this.handler = handler;
        this.ip = ip;
        this.port = port;
        sr = s;
    }
    @SuppressLint("HandlerLeak") @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            s = new Socket();
            Log.d("111111111111", "@@@@@@@@@@@@@@@@@@@@");
//          s = new Socket("192.168.0.78", 8888);
            s.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 5000);
            sendMessageMain("Successfully Connected");
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);

            Log.d("111111111111", "[Math Processing Error]" +
                            "[Math Processing Error][Math Processing Error][Math Processing Error]" +
                    "$$");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            new Thread() {

                @Override
                public void run() {
                    String content = null;
                    try {
                        while ((content = br.readLine()) != null) {
                            recievedMessage(content);
                            Log.d("Recieved", content);
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            }.start();
            Looper.prepare();
            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x345) {
                        try {
                            os.write((msg.obj.toString() + "\r\n")
                                    .getBytes("utf-8"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            //sr.setTcpHandler(revHandler);
            Looper.loop();

        } catch (SocketTimeoutException e) {
            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = "Timeout";
            handler.sendMessage(msg);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
