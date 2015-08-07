package com.example.birthdaytree.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class PostThread extends Thread{
	private String url;
	private String chatId;
	public static boolean bool = false;
	private Handler mHandler;
	public PostThread(String url,String chatId, Handler mHandler) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.chatId = chatId;
		this.mHandler = mHandler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("chatId", chatId));  
    	Message msg = Message.obtain();

        try {   
        	//下面几句话解释了安卓连服务器的过程,1.首先通过url生成请求httpRequest,HttpPost httpRequest = new HttpPost(url)
        	//2.设置参数HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
        	//3.让请求携带参数httpRequest.setEntity(httpEntity);
        	//4.生成一个发送请求的Http客户端
        	//5.客户端执行请求,返回一个应答
        	//想到发送请求必须要有客户端就行了
        
        	HttpPost httpRequest = new HttpPost(url);
			HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
			httpRequest.setEntity(httpEntity);
			HttpClient httpClient = new DefaultHttpClient();
			//设置超时时间
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);//连接超时
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), 8000);//请求超时
	        HttpResponse httpResponse = httpClient.execute(httpRequest);
	      
	        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){   	
                String result = EntityUtils.toString(httpResponse.getEntity());  
                if(result.equals("success")){           	  
                	msg.arg1 = 1;
                }else{
                	msg.arg1 = 0;
                }
             
            }else{  
            	msg.arg1 = 0;
            }  
	      mHandler.sendMessage(msg);
		} catch (UnsupportedEncodingException e) {
			msg.arg1 = 0;
			mHandler.sendMessage(msg);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			msg.arg1 = 0;
			mHandler.sendMessage(msg);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.arg1 = 0;
			mHandler.sendMessage(msg);
		} 
	}
}
