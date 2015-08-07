package com.example.koulingclient;

public class QueryThread extends PostThread{
	String url = "http://10.0.2.2/xampp/site/javaRequst/query.php";
	PostThread postThread;
	public QueryThread(PostThread postThread) {
		// TODO Auto-generated constructor stub
		this.postThread = postThread;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.Post(url);
		synchronized (postThread) {
			postThread.notify();
		}
	}
}
