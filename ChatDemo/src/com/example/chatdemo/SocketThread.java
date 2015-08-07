package com.example.chatdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Handler;
import android.util.Log;

public class SocketThread extends Thread {
	private Socket socket;
	private int port;
	private String ip;
	private Client client;
	private MessageListener mMessageListener;
	public Handler mainHandler;
	public SocketThread(String ip,int port,MessageListener mMessageListener){
		this.ip = ip;
		this.port = port;
	
		this.mMessageListener = mMessageListener;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.v("run()", "---->");	
		//建立连接
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, 8899), 3000);
			//新建一个客户端,并设置客户端的消息监听
			Log.v("connect", "---->");
			client = new Client(socket, mMessageListener);
			//启动客户端线程
			client.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
	}
	public void sendMsg(String message) {
		client.getOut().sendMsg(message);
	}
	//定义一个客户端内部类
public class Client{
		private ClientInputThread in;
	    private ClientOutputThread out;
	    
		public Client() {
			// TODO Auto-generated constructor stub
		}
		public void start() {
			// 打开输入输出线程
			in.setStart(true);
			out.setStart(true);
			in.start();
			out.start();
		}
		public Client(Socket socket,MessageListener mMessageListener){
			  //用这个监听输入流线程来接收信息
		     this.in = new ClientInputThread(socket);
		     this.in.setMessageListener(mMessageListener);
		      //以后就用这个监听输出流的线程来发送信息了
		     this.out = new ClientOutputThread(socket);
		}
		  // 得到读消息线程
	    public ClientInputThread getIn() {
	      return in;
	    }

	    // 得到写消息线程
	    public ClientOutputThread getOut() {
	      return out;
	    }
	}

	
}
