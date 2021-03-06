package com.muxinzhi.www.server;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;

/**
 * Created by MSI on 2018/2/19.
 */

abstract public class Server {
    abstract public MessageRecieve request(MessageRequest msg);
    abstract public int[]  initialGame(int cardNumbers);

    public abstract int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2,
                                         int a,int b,int c, int player);

    public abstract int[] requestConnection();
}
