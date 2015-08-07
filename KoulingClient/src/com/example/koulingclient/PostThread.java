package com.example.koulingclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
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

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PostThread extends Thread{
	String username ;
	String password;
	Person person;

	String flag = "";
//	Handler mainHandler;
	String curDateStr;
	public static boolean  bool = false;
	List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
	public PostThread(String url , String username , String password , String flag , Handler mainHandler) {
		// TODO Auto-generated constructor stub	
		this.flag = flag;
		this.username = username;
		this.password = password;
		this.curDateStr = curDateStr;
	}
	public PostThread() {
		// TODO Auto-generated constructor stub
	}
	public PostThread(Handler handler, String string) {
		// TODO Auto-generated constructor stub
//		this.mainHandler = handler;
		this.flag = string;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//1.声明提交的数据
		String url = null;
		if(flag == "denglu"){
			query(person.getPassword(),person.getUsername());
			url = "http://10.0.2.2/xampp/site/booksManagerForSql/query.php";
		}else if(flag == "zhuce"){
			save(person);
			url = "http://10.0.2.2/xampp/site/booksManagerForSql/add.php";
		}
		Post(url);	
	}
	public void Post(String url) {
		// TODO Auto-generated method stub
		//2.声明请求
				HttpPost request = new HttpPost(url);
				//3.设置请求参数
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(valuePairs,"utf-8");
					request.setEntity(httpEntity);
					//4.建立发送客户端
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(request);
					if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						String result = EntityUtils.toString(httpResponse.getEntity());
						if(result.equals("register success") || result.equals("enter success") ){
		        			bool = true;		
//		        			mainHandler.sendEmptyMessage(0);
//		        			person.savePersonListener.onSuccess();
		        		}else{
		        			bool = false;
		        		}
						
					}else{
						bool = false;
					}
				synchronized (person) {
					person.notify();//person对象释放线程
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
				}
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public void query(String pwd,String username) {
		// TODO Auto-generated method stub
		valuePairs.add(new BasicNameValuePair("pwd" , Encrypt.MD5(pwd).toLowerCase()));
		valuePairs.add(new BasicNameValuePair("username" , username));
	}
	public boolean save(Person person) {
		// TODO Auto-generated method stub
			valuePairs.add(new BasicNameValuePair("name" , person.getUsername()));
			valuePairs.add(new BasicNameValuePair("pwd" , person.getPassword()));
			valuePairs.add(new BasicNameValuePair("object_id" , person.getObject_id()));
			return true;
	
	
	}
	private void isHave(String object_id) {
		// TODO Auto-generated method stub
		
	}
	
}
