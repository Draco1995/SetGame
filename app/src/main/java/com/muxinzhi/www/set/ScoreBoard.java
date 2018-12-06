package com.muxinzhi.www.set;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import android.os.Handler;

/**
 * Created by MSI on 2018/2/21.
 */

public class ScoreBoard {
    final private Handler handler;
    private String[] name;
    private int[] score;
    private int playerNumbers;
    public int myself;
    public ScoreBoard(Handler handler) {
        this.handler = handler;
    }

    public void startGame(int[] playerInf) {
        playerNumbers = playerInf[0];
        name = new String[playerNumbers];
        score = new int[playerNumbers];
        myself = playerInf[1];
        String s = "";
        for(int i = 0;i<name.length;i++){
            name[i] = "Player"+(i+1);
            s += name[i]+":"+score[i]+" ";
        }
        setText(s);
    }

    private void setText(String s){
        Message m = new Message();
        m.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("text",s);
        m.setData(bundle);
        handler.sendMessage(m);
    }

    private void createText(){
        String s = "";
        for(int i = 0;i<name.length;i++){
            s += name[i]+":"+score[i]+" ";
        }
        setText(s);
    }

    synchronized public void addScore(){
        score[myself]++;
        createText();
    }

    synchronized public void addScore(int i){
        score[i]++;
        createText();
    }
}
