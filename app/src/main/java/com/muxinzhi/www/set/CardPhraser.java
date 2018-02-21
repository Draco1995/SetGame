package com.muxinzhi.www.set;

import com.muxinzhi.www.set.enums.EColor;
import com.muxinzhi.www.set.enums.EFill;
import com.muxinzhi.www.set.enums.EShape;

/**
 * Created by MSI on 2018/2/21.
 */

public class CardPhraser {
    static void setCard(Card card, int number){
        int num;
        num = number%3;
        number = number/3;
        switch (num){
            case 0:
                card.color = EColor.BLUE;
                break;
            case 1:
                card.color = EColor.GREEN;
                break;
            case 2:
                card.color = EColor.RED;
        }
        num = number%3;
        number = number/3;
        switch (num){
            case 0:
                card.fill = EFill.HATCH;
                break;
            case 1:
                card.fill = EFill.PLEIN;
                break;
            case 2:
                card.fill = EFill.VIDE;
        }
        num = number%3;
        card.number = number/3;
        switch (num){
            case 0:
                card.shape = EShape.CIRCLE;
                break;
            case 1:
                card.shape = EShape.RECTANGLE;
                break;
            case 2:
                card.shape = EShape.RHOMBUS;
        }



    }
}
