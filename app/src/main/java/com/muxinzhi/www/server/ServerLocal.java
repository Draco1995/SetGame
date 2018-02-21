package com.muxinzhi.www.server;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;

import java.util.Random;

/**
 * Created by MSI on 2018/2/19.
 */

public class ServerLocal extends Server {
    Random random = new Random(42);
    boolean[] hash = new boolean[81];
    boolean[] taken = new boolean[81];
    @Override
    public MessageRecieve request(MessageRequest msg) {
        return null;
    }

    @Override
    public int[] initialGame(int cardNumbers) {
        int[] numbers = getNewValidCards(cardNumbers);
        return numbers;
    }

    @Override
    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2) {
        if(!taken[serialNumber]&&!taken[serialNumber1]&&!taken[serialNumber2]){
            taken[serialNumber]=true;
            taken[serialNumber1]=true;
            taken[serialNumber2]=true;
            return getNewValidCards(3);
        }else{
            return null;
        }

    }

    private int[] getNewValidCards(int cn) {
        int[] numbers = new int[cn];
        int count = 0;
        while(count<cn){
            int ran = random.nextInt(81);
            while(hash[ran%81]==true){
                ran++;
            }
            hash[ran%81] = true;
            numbers[count] = ran;
            count++;
        }
        return numbers;
    }
}
