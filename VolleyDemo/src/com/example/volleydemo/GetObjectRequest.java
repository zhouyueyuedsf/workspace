package com.example.volleydemo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class GetObjectRequest<T> extends Request<T> {

	 /**
     */
    private ResponseListener mListener ;
    
    private Gson mGson ;
    private Type mClazz ;

    public GetObjectRequest(String url,Type type, ResponseListener listener) {
        super(Method.POST, url, listener);
        this.mListener = listener ;
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() ;
        mClazz = type ;
    }
    //POST提交的时候会用到
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
	// TODO Auto-generated method stub
	return super.getParams();
    }
    /**
     *解析服务器响应的数据
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result ;
            //网络下发的Json格式转成字符串
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            result = mGson.fromJson(jsonString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * �ص���ȷ������
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
 
}
