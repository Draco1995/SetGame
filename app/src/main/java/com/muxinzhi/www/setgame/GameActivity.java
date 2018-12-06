package com.muxinzhi.www.setgame;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muxinzhi.www.server.Server;
import com.muxinzhi.www.server.ServerLocal;
import com.muxinzhi.www.set.CardSet;
import com.muxinzhi.www.set.Logic;
import com.muxinzhi.www.set.Mediator;
import com.muxinzhi.www.set.ScoreBoard;
import com.muxinzhi.www.set.SetGameLogic;

import java.util.logging.Logger;

public class GameActivity extends AppCompatActivity {

    private Logic logic;

    private static final int SCOREBOARD = 0;
    private static final int MULTIPLAYERSTARTBUTTON = 1;
    private TextView scoreB;
    private Button startButton;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Log.i("","setting");
            if(msg.what == SCOREBOARD){

                scoreB.setText(msg.getData().getString("text"));
            }
            if(msg.what == MULTIPLAYERSTARTBUTTON){
                startButton.setClickable(true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startButton = (Button)findViewById(R.id.start_button);
        startButton.setClickable(false);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        scoreB = findViewById(R.id.scoreBoard);
        scoreB.setText("Starting Game");
        int cardNumbers = 16;
        logic = new SetGameLogic(this,startButton, cardNumbers, handler, message, getApplication());
        if(message.equals(getString(R.string.single_player)))
            startButton.setClickable(true);
    }

    public void startGame(View view){
        logic.startGame();
    }
}
