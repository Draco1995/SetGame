package com.muxinzhi.www.set;

/**
 * Created by MSI on 2018/2/22.
 */

public class DigitsPhraser {
    public static String phrase(int a){
        String ans = "";
        ans+=a%3;
        a = a/3;
        ans+=a%3;
        a = a/3;
        ans+=a%3;
        ans+=a/3;
        return ans;
    }
}
