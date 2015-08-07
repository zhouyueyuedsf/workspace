package com.example.birthdaytree.thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.birthdaytree.ShowPersonBirthdayDetail;
import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.bean.TransportPerson;
import com.example.birthdaytree.util.JsonUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServerVerifyThread extends VerifyThread {
	Handler mainHandler;
	Context context;
//	private ObjectInputStream inObjectInputStream;
//	private ObjectOutputStream objectOutputStream;
	String flag;
	int id;
	public ServerVerifyThread(Handler mainHandler) {
		// TODO Auto-generated constructor stub
		this.mainHandler = mainHandler;
	}

	public ServerVerifyThread(Context context, String flag, int id,Intent verifyThreadIntent) {
		this.context = context;
		this.VerifyThreadIntent = verifyThreadIntent;
		dbOperation = new DbOperation(context, "birthdayTree.db");
		this.flag = flag;
		this.id = id;
		person = new Person();
//		user = dbOperation.queryFromID("user", 1);
	}

	@Override
	public void run() {
		
		ServerSocket serverSocket;
		// Person person;
		try {
			while (true) {
				serverSocket = new ServerSocket(5000);
				client = serverSocket.accept();
				inDataInputStream = new DataInputStream(client.getInputStream());
				dataOutputStream = new DataOutputStream(client.getOutputStream());
				if(accept()){
					dbOperation.insertData(person, flag);
					/*服务器向客户端发送自己的名片 start*/
					try {
						sleep(2000);
						if(sendUser()){
							serverSocket.close();
							client.close();
							inDataInputStream.close();
							dataOutputStream.close();
							context.stopService(VerifyThreadIntent);
						}
							
							
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					/*end*/					
				};
				
//				String jsonStr = inDataInputStream.readUTF();
				
//				
//				sendUser(client);
//			dataOutputStream.writeUTF("hello");
//				person.setID(id);
//				dbOperation.update(person, flag);
//				serverSocket.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
