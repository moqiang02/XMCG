package com.example.rex.xmcg;

import java.util.Random;

/**
 * Created by Rex on 2016/10/8.
 */

public class CommonUtils {
    public static String getRandomCode(){
        String yzm ="";
        for (int i =0;i<4;i++){
            int number = new Random().nextInt(10);
            yzm+=number;
        }
        return yzm;
    }
}
