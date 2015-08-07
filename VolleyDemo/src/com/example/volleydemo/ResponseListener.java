package com.example.volleydemo;

import com.android.volley.Response;

/**
 * Created by gyzhong on 15/3/1.
 * 简化回调接口
 */
public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {
}