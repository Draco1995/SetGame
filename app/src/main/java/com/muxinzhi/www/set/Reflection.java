package com.muxinzhi.www.set;

import com.muxinzhi.www.exceptions.NoSuchIdException;
import com.muxinzhi.www.setgame.R;

import java.lang.reflect.Field;

/**
 * Created by MSI on 2018/2/21.
 */

public class Reflection {
    public static int reflect(String type) throws NoSuchIdException {
        try{
            Field field=R.id.class.getField(type);
            int i= field.getInt(new R.id());
            return i;
        }catch(Exception e){
            throw new NoSuchIdException();
        }
    }
}
