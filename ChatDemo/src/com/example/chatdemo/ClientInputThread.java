package com.example.chatdemo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.os.Handler;
import android.util.Log;

public class ClientInputThread extends Thread {
	  private Socket socket;
	  private String msg;
	  private boolean isStart = true;
	  private InputStream ois;
	  private DataInputStream dis;
	  private MessageListener mMessageListener;// 消息监听接口对象
	  public Handler mainHandler;
	public ClientInputThread(Socket socket) {
		// TODO Auto-generated constructor stub
		 try {
		      ois = socket.getInputStream();//获得socket的输入流
		      dis = new DataInputStream(new BufferedInputStream(ois));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}

	public void setMessageListener(MessageListener mMessageListener) {
		// TODO Auto-generated method stub
		this.mMessageListener = mMessageListener;
	}

	public void setStart(boolean b) {
		// TODO Auto-generated method stub
		this.isStart = true;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		  try {
		      while (isStart) {
		        //读取信息,如果没信息将会阻塞线程 
		        msg =  dis.readUTF();
		        // 每收到一条消息，就调用接口的方法Message(String msg)
		        Log.v("收到消息", msg);
		        mMessageListener.Message(msg);
		      }
		      ois.close();
		      if (socket != null)
		        socket.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
}
