package com.muxinzhi.www.set;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muxinzhi.www.server.Server;
import com.muxinzhi.www.server.ServerLocal;
import com.muxinzhi.www.server.ServerRemote;
import com.muxinzhi.www.setgame.R;
import android.os.Handler;

/**
 * Created by MSI on 2018/2/19.
 */

public class SetGameLogic extends Logic {

    private Server server;
    private CardSet cardSet;
    private ScoreBoard scoreBoard;
    private Mediator mediator;
    private Handler handler;
    private int cardNumbers;
    public SetGameLogic(Activity a, Button b, int cardNumbers, Handler handler, String m, Application application){
        super(a,b);
        this.handler = handler;
        this.cardNumbers = cardNumbers;
        try {
            mediator = new Mediator();
            if(m.equals("Single Player")){
                server = new ServerLocal(mediator);
            }else{
                server = new ServerRemote(mediator,handler,application.getString(R.string.Real_Server_IP),
                        application.getString(R.string.Real_Server_Port));
            }

            cardSet = new CardSet(activity, this.cardNumbers , mediator);
            scoreBoard = new ScoreBoard(handler);
            mediator.setParameters(cardSet,scoreBoard,server);
        }catch(Exception e){
            activity.finish();
        }
    }
    @Override
    public void startGame(){
        Log.i("","Game Started!");
        startButton.setVisibility(View.INVISIBLE);
        startButton.setClickable(false);
        new Thread(){
            public void run(){
                mediator.startGame(cardNumbers);
            }
        }.start();

    }
}
