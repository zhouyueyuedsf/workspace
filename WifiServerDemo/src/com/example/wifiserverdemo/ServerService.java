package com.example.wifiserverdemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class ServerService extends Service {
	Handler handler;
	public ServerService(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public ServerService() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		// TODO Auto-generated method stub
		ServerSocket serverSocket;  
    	while(true){
			try {
				serverSocket = new ServerSocket(5000);
				Socket client = serverSocket.accept();
				ServerThread serverThread = new ServerThread(handler,client);
		    	serverThread.start();//开启socket
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
