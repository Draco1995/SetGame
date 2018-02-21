package com.muxinzhi.www.setgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class GameActivity extends AppCompatActivity {

    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button startButton = (Button)findViewById(R.id.start_button);
        startButton.setClickable(false);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        int cardNumbers = 16;
        logic = new SetGameLogic(this,startButton, cardNumbers);

        startButton.setClickable(true);
    }

    public void startGame(View view){
        logic.startGame();
    }
}
