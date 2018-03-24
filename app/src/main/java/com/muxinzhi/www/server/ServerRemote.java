package com.muxinzhi.www.server;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;
import com.muxinzhi.www.set.Mediator;
import com.muxinzhi.www.tcphandler.TcpClient;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MSI on 2018/2/19.
 */

public class ServerRemote extends Server {
    private Mediator mediator;
    private Handler mainActivityHandler;
    private String ip;
    private String port;
    private TcpClient tcp;
    private static int requestNumber = 0;
    private Handler localHandler = null;

    private int getRequestNumber(){
        synchronized (new Object()){
            requestNumber++;
            return requestNumber;
        }
    }

    class LocalMessage{
        String tag;
        String msg;
        LocalMessage(String tag,String msg){
            this.tag = tag;
            this.msg = msg;
        }
    }

    private ConcurrentLinkedQueue<LocalMessage> messageQueue= new ConcurrentLinkedQueue<LocalMessage>();

    private String lookForResponse(String tag){
        while(true){
            if(messageQueue.peek()==null||!messageQueue.peek().tag.equals(tag)){
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace( );
                }
            }else{
                break;
            }
        }
        return messageQueue.remove().msg;
    }



    public ServerRemote(Mediator m, Handler handler, String ip,String port){
        mediator = m;
        this.mainActivityHandler = handler;
        this.ip = ip;
        this.port = port;
        sendMessage("Connecting to Server");
        tcp = new TcpClient(handler,ip,port, this);
        new Thread(tcp).start();
        new Thread(){
            public void run(){
                Looper.prepare( );
                localHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        // 接收到UI线程的中用户输入的数据
                        String[] message = msg.obj.toString().split(" ");
                        mediator.addScore(Integer.valueOf(message[0]));
                        mediator.setCards(message[1]);
                    }
                };
                Looper.loop();
            }
        }.start();
    }

    public void addScore(String msg){
        Message m = new Message();
        m.what = 0;
        m.obj = msg;
        localHandler.sendMessage(m);
    }

    public void addResponse(String msg){
        String[] m = msg.split(":");
        messageQueue.add(new LocalMessage(m[0],m[1]));
        Log.i("messageQueue","Message added:"+msg+"\n"+messageQueue.size());
    }

    private void sendMessage(String m){
        Message msg = new Message();
        msg.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("text",m);
        msg.setData(bundle);
        mainActivityHandler.sendMessage(msg);
    }

    @Override
    public MessageRecieve request(MessageRequest msg) {
        return null;
    }

    /**
     * 正式开始游戏时（点击Start Button后）第一个调用的函数
     * @param cardNumbers 需要多少卡牌
     * @return 一系列数字，代表了卡牌的号码
     */
    @Override
    public int[] initialGame(int cardNumbers) {
        int rn = getRequestNumber();
        String msg = ""+rn+":"+"initialGame,"+cardNumbers;
        tcp.sendMessage(msg);
        String ans = lookForResponse(""+rn);
        Log.i("lookForResponse","ans found"+"ans");
        String[] m = ans.split(",");
        int[] numbers = new int[cardNumbers];
        for(int i = 0;i<cardNumbers;i++){
            numbers[i] = Integer.valueOf(m[i]);
        }
        return numbers;
    }

    @Override
    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2,
                                int a,int b,int c,int player) {
        int rn = getRequestNumber();
        String msg = ""+rn+":"+"requestRemoval,"+serialNumber+","+serialNumber1+","+serialNumber2+
                ","+a+","+b+","+c;
        tcp.sendMessage(msg);
        String ans = lookForResponse(""+rn);
        Log.i("lookForResponse","ans found"+"ans");
        if(ans.equals("refused")){
            return null;
        }else{
            String[] m = ans.split(",");
            int[] numbers = new int[3];
            for(int i = 0;i<3;i++){
                numbers[i] = Integer.valueOf(m[i]);
            }
            mediator.addScore();
            return numbers;
        }
    }

    /**
     * 第二个调用的函数，
     * @return 第一个参数代表有多少玩家参与，第二个
     * 参数代表本玩家的序列号
     */
    @Override
    public int[] requestConnection() {
        int rn = getRequestNumber();
        String msg = ""+rn+":"+"requestConnection,null";
        tcp.sendMessage(msg);
        String ans = lookForResponse(""+rn);
        Log.i("lookForResponse","ans found"+"ans");
        String[] m = ans.split(",");
        int[] numbers = new int[2];
        numbers[0] = Integer.valueOf(m[0]);
        numbers[1] = Integer.valueOf(m[1]);
        return numbers;
    }
}
