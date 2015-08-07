package com.example.koulingclient;

import android.content.Context;
import android.os.Handler;

public class Person{
	private String object_id;
	private String username;
	private String password;
	 GetPersonListener getPersonListener;
	 SavePersonListener savePersonListener;
	 Handler handler = new Handler();
	Person(String object_id, String username, String password){
		this.object_id = object_id;
		this.username = username;
		this.password = password;
	}
	public Person() {
		// TODO Auto-generated constructor stub
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public boolean getPerson(Context context, String object_id, GetPersonListener getPersonListener){
		this.getPersonListener = getPersonListener;
		PostThread postThread;
		postThread = new PostThread(handler,"denglu");
		postThread.setPerson(this);
		postThread.start();
		synchronized (this) {
			try {
				this.wait();//先让该线程阻塞,对象this调用阻塞函数
				if(postThread.bool){
					getPersonListener.onSuccess();//成功
				}else{
					getPersonListener.onFailed();//失败
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	public void save(Context context, SavePersonListener savePersonListener){
		this.savePersonListener = savePersonListener;
		PostThread postThread;
		postThread = new PostThread(handler,"zhuce");
		postThread.setPerson(this);
		postThread.start();
		synchronized (this) {
			try {
				this.wait();//先让该线程阻塞,对象this调用阻塞函数
				if(postThread.bool){
					savePersonListener.onSuccess();//成功
				}else{
					savePersonListener.onFailed();//失败
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
}
