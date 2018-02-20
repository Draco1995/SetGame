package com.muxinzhi.www.set;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * Created by MSI on 2018/2/19.
 */

public class Painter {

    private Paint mPaint;

    Painter(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(60);
        mPaint.setColor(Color.BLACK);
    }

    public void draw(Card card, Canvas canvas){
        canvas.drawRect(0, 0, card.getWidth(), card.getHeight(), mPaint);
        //canvas.drawText(""+card.number,card.getLeft(),card.getTop(),mPaint);
    }

}
