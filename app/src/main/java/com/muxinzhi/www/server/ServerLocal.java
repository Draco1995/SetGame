package com.muxinzhi.www.server;

import android.provider.MediaStore;

import com.muxinzhi.www.message.MessageRecieve;
import com.muxinzhi.www.message.MessageRequest;
import com.muxinzhi.www.set.Mediator;

import java.util.Random;

/**
 * Created by MSI on 2018/2/19.
 */

public class ServerLocal extends Server {
    Random random = new Random(42);
    boolean[] hash = new boolean[81];
    boolean[] taken = new boolean[81];

    Mediator mediator;

    public ServerLocal(Mediator m){
        mediator = m;
    }

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
    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2, final int player) {
        if(!taken[serialNumber]&&!taken[serialNumber1]&&!taken[serialNumber2]){
            taken[serialNumber]=true;
            taken[serialNumber1]=true;
            taken[serialNumber2]=true;
            mediator.addScore(player);
            return getNewValidCards(3);
        }else{
            return null;
        }

    }

    @Override
    public int[] requestConnection() {
        int[] a = new int[2];
        a[0] = 1;
        a[1] = 1;
        return a;
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
