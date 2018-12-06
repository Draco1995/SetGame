package com.muxinzhi.www.set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path;
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
        mPaint.setStrokeWidth(5);
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

            CardPhraser.setCard(this,serialNumber);
            this.draw(canvas,getWidth(),getHeight());
            /*mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(60);
            String text = DigitsPhraser.phrase(serialNumber);
            mPaint.getTextBounds(text, 0, text.length( ), mBounds);
            float textWidth = mBounds.width( );
            float textHeight = mBounds.height( );
            canvas.drawText(text, getWidth( ) / 2 - textWidth / 2, getHeight( ) / 2
                    + textHeight / 2, mPaint);*/

        }
    }

    void draw(Canvas canvas, int width, int height) {
        // set the default paint style
        setColor(mPaint);
        // computes the topmost point
        int startY = (36 - 16*number)*height/96;
        // draw as many shapes as this.number
        for (int i = 0; i <= number; i++) {
            Log.i("number",""+number);
            RectF r = new RectF(width/8, startY + i * height/3, width*7/8, startY + i * height/3 + height/4);
            drawFilledShape(canvas, mPaint, r);
        }
    }

    private void setColor(Paint mPaint){
        switch (this.color){
            case RED:
                mPaint.setColor(Color.RED);
                break;
            case BLUE:
                mPaint.setColor(Color.BLUE);
                break;
            case GREEN:
                mPaint.setColor(Color.GREEN);
                break;
            default: new Error("invalid color");
        }
    }

    private void drawShape(Canvas canvas, Paint mPaint, RectF r) {
        switch (shape) {
            case CIRCLE:
                canvas.drawOval(r, mPaint);
                break;
            case RECTANGLE:
                canvas.drawRect(r, mPaint);
                break;
            case RHOMBUS:
                canvas.drawPath(diamond(r), mPaint);
                break;
            default: new Error("invalid shape");
        }
    }

    private void drawFilledShape(Canvas canvas, Paint mPaint, RectF r) {
        switch (fill) {
            case VIDE: mPaint.setStyle(Paint.Style.STROKE); drawShape(canvas, mPaint, r); break;
            case HATCH:
                // in case of intermediate filling, we draw concentric copies of the same shape
                mPaint.setStyle(Paint.Style.STROKE);
                for (int i = 0; i < r.width()/2; i+=6) {
                    drawShape(canvas, mPaint, new RectF(r.left + i, r.top + i * r.height()/r.width(), r.right - i, r.bottom - i * r.height()/r.width()));
                }
                break;
            case PLEIN: mPaint.setStyle(Paint.Style.FILL); drawShape(canvas, mPaint, r); break;
            default: new Error("invalid filling");
        }
    }

    private Path diamond(RectF r) {
        Path p = new Path();
        p.moveTo(r.left, r.centerY());
        p.lineTo(r.centerX(), r.top);
        p.lineTo(r.right, r.centerY());
        p.lineTo(r.centerX(), r.bottom);
        p.lineTo(r.left, r.centerY());
        return p;
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
