package com.example.volleydemo;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class XMLRequest extends Request<XmlPullParser> {
	
	private final Listener<XmlPullParser> mListener;
	public XMLRequest(int method, String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	@Override
	protected void deliverResponse(XmlPullParser response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
		XmlPullParser xmlPullParser = null;
        try {
        	  String xmlString = new String(response.data,  
                      HttpHeaderParser.parseCharset(response.headers));  
              XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
               xmlPullParser = factory.newPullParser();  
              xmlPullParser.setInput(new StringReader(xmlString));  
              return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			// TODO: handle exception
		}
        return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));  
	}

	
  
}	
