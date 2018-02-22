package com.muxinzhi.www.setgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    final static String EXTRA_MESSAGE = "Botton_Message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessgae(View view){
        Intent intent = new Intent(this, GameActivity.class);
        String message = ((Button)view).getText().toString();
        //String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }

    public void startSetting(View view){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
