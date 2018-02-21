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
        scoreBoard.startGame();
    }

    public int[] requestRemoval(int serialNumber, int serialNumber1, int serialNumber2) {
        return server.requestRemoval(serialNumber,serialNumber1,serialNumber2);
    }
}
