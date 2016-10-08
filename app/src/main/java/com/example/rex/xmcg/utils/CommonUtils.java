package com.example.rex.xmcg.utils;

import java.util.Random;

/**
 * Created by Rex on 2016/10/8.
 */

public class CommonUtils {

    //获取四位随机数
    public static String getRandomCode(){
        String yzm ="";
        for (int i =0;i<4;i++){
            int number = new Random().nextInt(10);
            yzm+=number;
        }
        return yzm;
    }

}
