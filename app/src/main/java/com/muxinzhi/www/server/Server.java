package com.muxinzhi.www.server;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;

/**
 * Created by MSI on 2018/2/19.
 */

abstract public class Server {
    abstract public MessageRecieve request(MessageRequest msg);
}
