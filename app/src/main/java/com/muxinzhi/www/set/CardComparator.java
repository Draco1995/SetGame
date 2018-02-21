package com.muxinzhi.www.set;

/**
 * Created by MSI on 2018/2/21.
 */

public class CardComparator {
    static boolean isValid(Card a,Card b, Card c){
        if(PisValid(a.number,b.number,c.number)&&PisValid(a.color,b.color,c.color)
                &&PisValid(a.fill,b.fill,c.fill)&&PisValid(a.shape,b.shape,c.shape))
            return true;
        return false;
    }
    private static boolean PisValid(Object a,Object b, Object c){
        if(a == b){
            if(a!=c){
                return false;
            }
        }else{
            if(a==c||b==c){
                return false;
            }
        }
        return true;
    }
}
