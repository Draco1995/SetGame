package com.muxinzhi.www.server;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;
import com.muxinzhi.www.set.Mediator;
import com.muxinzhi.www.tcphandler.TcpClient;

/**
 * Created by MSI on 2018/2/19.
 */

public class ServerRemote extends Server {
    private Mediator mediator;
    private Handler mainActivityHandler;
    private String ip;
    private String port;
    private TcpClient tcp;
    public ServerRemote(Mediator m, Handler handler, String ip,String port){
        mediator = m;
        this.mainActivityHandler = handler;
        this.ip = ip;
        this.port = port;
        sendMessage("Connecting to Server");
        tcp = new TcpClient(handler,ip,port);
        new Thread(tcp).start();
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
        return null;
    }

    @Override
    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2, int player) {
        return new int[0];
    }

    /**
     * 第二个调用的函数，
     * @return 第一个参数代表有多少玩家参与，第二个
     * 参数代表本玩家的序列号
     */
    @Override
    public int[] requestConnection() {
        return new int[0];
    }
}
