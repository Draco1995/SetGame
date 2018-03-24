package com.muxinzhi.www.set;

import android.media.browse.MediaBrowser;

import com.muxinzhi.www.server.Server;

/**
 * Created by MSI on 2018/2/19.
 */

public class Mediator {
    ScoreBoard scoreBoard;
    CardSet cardSet;
    Server server;
    public Mediator(){}
    public void setParameters(CardSet c, ScoreBoard s, Server server){
        scoreBoard = s;
        cardSet = c;
        this.server = server;

    }

    public void serverInitialRequest() {

    }

    public void startGame(int cardNumbers) {
        int[] numbers = server.initialGame(cardNumbers);
        cardSet.startGame(numbers);
        int[] playerInf = server.requestConnection();
        scoreBoard.startGame(playerInf);
    }

    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2,int a,int b,
                                int c) {
        return server.requestRemoval(serialNumber,serialNumber1,serialNumber2,a,b,c,scoreBoard.myself);
    }

    synchronized public void addScore(int player){
        scoreBoard.addScore(player);
    }

    synchronized public void addScore(){
        scoreBoard.addScore();
    }

    public void setCards(String m){
        String[] msg = m.split(",");
        final int[] cards = new int[3];
        final int[] numbers = new int[3];
        for(int i = 0;i<=2;i++){
            numbers[i] = Integer.valueOf(msg[i]);
            cards[i] = Integer.valueOf(msg[i+3]);
        }
        new Thread(){
            @Override
            public void run() {
                cardSet.remoteSetCards(cards,numbers);
            }
        }.start();

    }
}
