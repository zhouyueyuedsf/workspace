package com.example.internetdemo1;

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
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class MyThread extends Thread {
	private String url;
	private EditText edtName;
	private Handler mainHandler;
	public MyThread(String url,EditText edtName,Handler mainHandler) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.edtName = edtName;
		this.mainHandler = mainHandler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		 Log.v(url,"------>"); 
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("address", "hubuxiang"));  
        params.add(new BasicNameValuePair("longitude", "100.252255"));  
        params.add(new BasicNameValuePair("latitude", "-15.415121"));

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
	        HttpResponse httpResponse = httpClient.execute(httpRequest); 
	        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
                String result = EntityUtils.toString(httpResponse.getEntity());  
                FileWriter fw=new FileWriter("test.xml");
                fw.write(result);
                fw.flush();
                fw.close();
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("test.xml");
                List<Person> persons = PullPersonService.getPersons(inputStream);
                Message msg = Message.obtain();
                msg.obj = result;
                mainHandler.sendMessage(msg);
//                Log.i(TAG,"result = "+result);  
            }else{  
            	 Message msg = Message.obtain();
                 msg.obj = "error"; 
                 mainHandler.sendMessage(msg); 
            }  
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
