package com.example.rex.xmcg.utils;

/**
 * Created by Rex on 2016/10/9.
 */

import static com.example.rex.xmcg.MyApplication.getInstances;

/**
 * Toast统一管理类
 */
public class TUtils {

    private TUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(int message) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, android.widget.Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, android.widget.Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, int duration) {
        if (isShow)
            android.widget.Toast.makeText(getInstances(), message, duration).show();
    }

}