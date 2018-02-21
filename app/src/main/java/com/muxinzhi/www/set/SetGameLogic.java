package com.muxinzhi.www.set;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.muxinzhi.www.server.Server;
import com.muxinzhi.www.server.ServerLocal;

/**
 * Created by MSI on 2018/2/19.
 */

public class SetGameLogic extends Logic {

    private Server server;
    private CardSet cardSet;
    private ScoreBoard scoreBoard;
    private Mediator mediator;
    private int cardNumbers;
    public SetGameLogic(Activity a, Button b, int cardNumbers){
        super(a,b);
        this.cardNumbers = cardNumbers;
        try {
            mediator = new Mediator();
            server = new ServerLocal();
            cardSet = new CardSet(activity, this.cardNumbers , mediator);
            scoreBoard = new ScoreBoard();
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
        mediator.startGame(cardNumbers);
    }
}
