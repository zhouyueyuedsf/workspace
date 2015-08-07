package com.example.volleydemo;

import java.io.UnsupportedEncodingException;

import com.android.volley.Request;

import com.google.gson.reflect.TypeToken;

public class ObjectRequest {
	 /**
     * 封装了返回Object类型的返回值
     * @param value 
     * @param listener
     */
    public static void getObjectRequst(String url,ResponseListener listener) throws UnsupportedEncodingException{
        Request request = new GetObjectRequest(url,new TypeToken<TestBean>(){}.getType(),listener) ;
        VolleyUtil.getRequestQueue().add(request) ;
    }

}
