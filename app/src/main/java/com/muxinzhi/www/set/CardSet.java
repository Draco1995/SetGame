package com.muxinzhi.www.set;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.muxinzhi.www.exceptions.NoSuchIdException;
import com.muxinzhi.www.setgame.R;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MSI on 2018/2/19.
 */

public class CardSet{
    private Mediator mediator;
    Card[] cardList;
    final int cardNumbers;
    TextView textView;
    ConcurrentLinkedQueue<Card> queue = new ConcurrentLinkedQueue<Card>();
    boolean[] hash = new boolean[85];
    public CardSet(Activity activity, int cardNumbers,Mediator mediator) throws NoSuchIdException {
        cardList = new Card[cardNumbers+1];
        textView = activity.findViewById(R.id.textView);
        this.cardNumbers = cardNumbers;
        this.mediator = mediator;
        try{
            for(int i = 1;i<=cardNumbers;i++){
                cardList[i] = (Card)activity.findViewById(Reflection.reflect("card"+(i)));
            }
        }catch (NoSuchIdException e){
            Log.e("NoSuchIdException","Id not find while creating cardList");
            throw e;
        }

        for(int i=1;i<=cardNumbers;i++) {
            cardList[i].setCardSet(this);
            cardList[i].layoutNumber = i;
        }
    }

    /**
     * 修改queue,card
     */
    synchronized void request(Card card){
        if(!queue.contains(card)&&queue.size()<=2){
            queue.add(card);
            card.clicked = true;
            card.setLayout(R.drawable.bbuton_pressed);
        }else{
            if(queue.remove(card)) {
                card.clicked = false;
                card.setLayout(R.drawable.bbuton_rounded);
            }
            else{
                Log.i("CardSet","fail to remove card:"+card.serialNumber);
            }
        }
        if(queue.size()==3){
            testValid();
        }

    }

    /**
     *
     */
    private void testValid(){
        Card a = queue.poll();
        Card b = queue.poll();
        Card c = queue.poll();
        Card[] cc = {a,b,c};
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }

        if(CardComparator.isValid(a,b,c)){
            setClickable(false);
            int[] numbers = mediator.requestRemoval(a.serialNumber,b.serialNumber,
                    c.serialNumber,a.layoutNumber,b.layoutNumber,c.layoutNumber);
            if(numbers!=null){
                showResult(a,b,c,R.drawable.bbuton_correct);
                setCards(cc,numbers);
            }

        }else{
            showResult(a,b,c,R.drawable.bbuton_wrong);
        }
    }
    /**
     * 修改card
     */
    private void showResult(Card a,Card b,Card c,int l){
        setClickable(false);
        a.setLayout(l);
        b.setLayout(l);
        c.setLayout(l);
        /*
        if(l==R.drawable.bbuton_correct)
            textView.setText("Correct!");
        else
            textView.setText("Wrong");*/
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
       // textView.setText("");
        a.setLayout(R.drawable.bbuton_rounded);
        b.setLayout(R.drawable.bbuton_rounded);
        c.setLayout(R.drawable.bbuton_rounded);
        setClickable(true);
    }

    /**
     * 修改card
     * @param flag
     */
    private void setClickable(boolean flag){
        for(int i = 1;i<=cardNumbers;i++){
            cardList[i].setClickable(flag);
        }
    }

    /**
     * 修改card,queue
     * @param cards
     * @param numbers
     */

    public void remoteSetCards(int[] cards,int[] numbers){
        Card[] c = new Card[3];
        for(int i = 0 ;i<=2;i++){
            c[i] = cardList[cards[i]];
        }
        showResult(c[0],c[1],c[2],R.drawable.bbuton_correct);
        setCards(c,numbers);
    }

    synchronized private void setCards(Card[] cards,int numbers[]) {
        for(int i = 0;i<2;i++){
            for(int j = i+1;j<=2;j++){
                if(cards[i].layoutNumber<cards[j].layoutNumber){
                    Card c = cards[i];
                    cards[i] = cards[j];
                    cards[j] = c;
                }
            }
        }

        for(int i=0;i<3;i++){
            CardPhraser.setCard(cards[i],numbers[i]);
            cards[i].setSerialNumber(numbers[i]);
            cards[i].clicked = false;
            if(queue.contains(cards[i])){
                queue.remove(cards[i]);
            }
            cards[i].invalidate();
        }
    }

    public void startGame(int[] numbers) {
        Log.i(""+numbers.length,""+ cardList.length);
        for(int i=1;i<=cardNumbers;i++){
            CardPhraser.setCard(cardList[i],numbers[i-1]);
            cardList[i].setSerialNumber(numbers[i-1]);
            cardList[i].setClickable(true);
            cardList[i].startListening();
            cardList[i].setLayout(R.drawable.bbuton_rounded);
        }
    }
}
