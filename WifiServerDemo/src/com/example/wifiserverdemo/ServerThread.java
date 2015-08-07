package com.example.wifiserverdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServerThread extends Thread{
	Handler mainHandler;
	Socket client;
	public ServerThread(Handler mainHandler, Socket client) {
		// TODO Auto-generated constructor stub
		this.mainHandler = mainHandler;
		this.client = client;
	}
	@Override
	public void run() {
		
		String text;
		try {
			
			while(true){
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				text = reader.readLine();//当客户端接入时这里就不能往下了就是说没读到
				String text1 = text;
				Message message = mainHandler.obtainMessage();
				message.obj = text;
				mainHandler.sendMessage(message);
				Log.v(text, "----->");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	
		
	}
}
