package com.muxinzhi.www.server;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;

/**
 * Created by MSI on 2018/2/19.
 */

public class ServerRemote extends Server {

    @Override
    public MessageRecieve request(MessageRequest msg) {
        return null;
    }

    @Override
    public int[] initialGame(int cardNumbers) {
        return null;
    }

    @Override
    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2) {
        return new int[0];
    }
}
