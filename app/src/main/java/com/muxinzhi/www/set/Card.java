package com.muxinzhi.www.set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.muxinzhi.www.set.enums.EColor;
import com.muxinzhi.www.set.enums.EFill;
import com.muxinzhi.www.set.enums.EShape;
import com.muxinzhi.www.setgame.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by MSI on 2018/2/19.
 */

public class Card extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    protected Lock lock = new ReentrantLock( );
    protected int number = 0;
    protected EColor color;
    protected EFill fill;
    protected EShape shape;
    protected int serialNumber;
    protected int layoutNumber;
    volatile boolean clicked = false;
    private Paint mPaint;
    private Rect mBounds;
    private Painter painter;
    private CardSet cardSet;
    protected int layout = R.drawable.bbuton_initial;

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect( );
        //layout =
        this.setClickable(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i("124",""+clicked);
        this.setBackgroundResource(layout);
        Log.i("layout",""+layout);
        //canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        if(serialNumber!=0) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(60);
            String text = DigitsPhraser.phrase(serialNumber);
            mPaint.getTextBounds(text, 0, text.length( ), mBounds);
            float textWidth = mBounds.width( );
            float textHeight = mBounds.height( );
            canvas.drawText(text, getWidth( ) / 2 - textWidth / 2, getHeight( ) / 2
                    + textHeight / 2, mPaint);
        }
    }

    @Override
    public void onClick(View v) {
        final Card c = this;
        new Thread(){
            public void run(){
                cardSet.request(c);
            }
        }.start();
    }

    public void setCardSet(CardSet cardSet) {
        this.cardSet = cardSet;
    }


    public void setSerialNumber(int serialNumber){
        this.serialNumber = serialNumber;

    }

    public void setParameters(int number, EShape shape, EColor color, EFill fill) {
        this.number = number;
        this.serialNumber = serialNumber;
        this.shape = shape;
        this.color = color;
        this.fill = fill;
        setOnClickListener(this);
        invalidate();
    }


    public void startListening() {
        setOnClickListener(this);
    }

    public void setLayout(int l){
        layout = l;
        Log.i("Setting Layout",""+this.layoutNumber+" "+l);
        invalidate();
    }
}
