package com.muxinzhi.www.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final protected static String EXTRA_MESSAGE = "Button_message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View button){
        // Do something in response to button
        Intent intent = new Intent(this, GameActivity.class);
        String message = ((Button)button).getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }

}

