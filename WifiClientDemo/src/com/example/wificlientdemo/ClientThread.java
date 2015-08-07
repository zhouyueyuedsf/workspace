package com.example.wificlientdemo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread{
	private String IP;
	public ClientThread(String IP) {
		// TODO Auto-generated constructor stub
		this.IP = IP;
	} 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		  try {
				Socket socket = new Socket(IP, 5000);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				writer.write("hello");
				writer.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
