package com.muxinzhi.www.set;

import android.app.Activity;
import android.util.Log;

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
    ConcurrentLinkedQueue<Card> queue = new ConcurrentLinkedQueue<Card>();
    boolean[] hash = new boolean[85];
    public CardSet(Activity activity, int cardNumbers,Mediator mediator) throws NoSuchIdException {
        cardList = new Card[cardNumbers+1];
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

    void request(Card card){
        synchronized(queue){
            if(!queue.contains(card)&&queue.size()<=2){
                queue.add(card);
                card.clicked = true;
            }else{
                if(queue.remove(card))
                    card.clicked = false;
                else{
                    Log.i("CardSet","fail to remove card:"+card.serialNumber);
                }
            }
            if(queue.size()==3){
                Card a = queue.remove();
                Card b = queue.remove();
                Card c = queue.remove();
                Card[] cc = {a,b,c};
                if(CardComparator.isValid(a,b,c)){
                    int[] numbers = mediator.requestRemoval(a.serialNumber,b.serialNumber,
                            c.serialNumber);
                    setClickable(false);
                    if(numbers!=null){
                        a.setBackgroundResource(R.drawable.bbuton_correct);
                        setCards(cc,numbers); //一个问题为如果
                    }

                }else{
                    setClickable(false);
                    a.clicked = false;
                    b.clicked = false;
                    c.clicked = false;
                    a.setBackgroundResource(R.drawable.bbuton_wrong);
                    a.invalidate();
                    b.invalidate();
                    c.invalidate();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace( );
                    }
                    a.setBackgroundResource(R.drawable.bbuton_rounded);
                    setClickable(true);
                }
            }
        }

    }

    private void setClickable(boolean flag){
        for(int i = 1;i<=cardNumbers;i++){
            cardList[i].setClickable(flag);
        }
    }

    private void setCards(Card[] cards,int numbers[]) {
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
            cardList[i].invalidate();
        }
    }

    public void startGame(int[] numbers) {
        Log.i(""+numbers.length,""+ cardList.length);
        for(int i=1;i<=cardNumbers;i++){
            CardPhraser.setCard(cardList[i],numbers[i-1]);
            cardList[i].setSerialNumber(numbers[i-1]);
            cardList[i].setClickable(true);
            cardList[i].startListening();
            cardList[i].invalidate();
        }
    }
}
