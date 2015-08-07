//子线程
package com.example.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MyThread extends Thread {
	public Handler handler;
	public Handler mainHandler;
	public MyThread() {
		// TODO Auto-generated constructor stub		
	}
	public MyThread(Handler mainHandler){
		this.mainHandler = mainHandler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare();//加上loop()函数实现了消息队列的循环
		handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					Message message = Message.obtain();
					message.obj = msg.obj;
					mainHandler.sendMessage(message);//mainHandler为与主线程相关的Handler,此句的作
					break;                            //用便是把子线程的消息发到主线程的队列
				case 1:
					Message message2 = Message.obtain();
					message2.obj = msg.obj;
					mainHandler.sendMessage(message2);
					break;
				}
			};
		};
		Looper.loop();
	}
}
