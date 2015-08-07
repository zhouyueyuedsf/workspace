package com.example.chatdemo;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.util.Log;

public class ClientOutputThread extends Thread {
	private Socket socket;
	  private DataOutputStream dos;
	  private boolean isStart = true;
	  private String message;
	public ClientOutputThread(Socket socket) {
		// TODO Auto-generated constructor stub
		  this.socket = socket;
		    try {
		      this.dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}

	public void setStart(boolean b) {
		// TODO Auto-generated method stub
		this.isStart = b;
	}

	public void sendMsg(String message) {
		// TODO Auto-generated method stub
		this.message = message;
		synchronized (this) {
		    notifyAll();//有消息发送的时候线程打开,无消息等待节约资源
		}
	}
	@Override
	  public void run() {
	    try {
	      while (isStart) {
	        if (message != null) {
	          dos.writeUTF(message);
	          dos.flush();
	          message=null;
	          synchronized (this) {
	            wait();// 发送完消息后，线程进入等待状态
	          }
	        }
	      }
	      dos.close();// 循环结束后，关闭输出流和socket
	      if (socket != null)
	        socket.close();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

}
