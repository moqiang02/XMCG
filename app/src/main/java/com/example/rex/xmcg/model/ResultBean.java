package com.example.rex.xmcg.model;

import java.util.List;

/**
 * Created by Rex on 2016/9/19.
 */
public class ResultBean<T> {
    public int code;
    public String msg;
    public T data;
    public List<T> dataList;

}
