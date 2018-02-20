package com.muxinzhi.www.set;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.muxinzhi.www.set.enums.EColor;
import com.muxinzhi.www.set.enums.EFill;
import com.muxinzhi.www.set.enums.EShape;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by MSI on 2018/2/19.
 */

public class Card extends View implements View.OnClickListener {


    protected Lock lock = new ReentrantLock();
    private Painter painter;
    protected int number = 0;
    protected EColor color;
    protected EFill fill;
    protected EShape shape;
    volatile boolean clicked = false;
    private CardSet cardSet;
    protected int serialNumber;

    Card(Context context, AttributeSet attrs){
        super(context,attrs);

    }
    public void setCardSet(CardSet cardSet){
        this.cardSet = cardSet;
        setOnClickListener(this);
    }
    public void setParameters(int number, int serialNumber,EShape shape, EColor color, EFill fill){
        this.number = number;
        this.serialNumber = serialNumber;
        this.shape = shape;
        this.color = color;
        this.fill = fill;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        painter.draw(this,canvas);
    }

    @Override
    public void onClick(View view) {
        sendRequest();
    }

    public void sendRequest(){
        cardSet.request(this.serialNumber);
    }

}
