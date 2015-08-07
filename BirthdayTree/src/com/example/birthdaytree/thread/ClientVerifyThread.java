package com.example.birthdaytree.thread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.birthdaytree.MainActivity;
import com.example.birthdaytree.MainActivity.dialogReceiver;
import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.bean.TransportPerson;
import com.example.birthdaytree.config.BirthdaytreeConstant;
import com.example.birthdaytree.dialog.CommonAlertDialogs;
import com.example.birthdaytree.util.BitmapUtil;
import com.example.birthdaytree.util.JsonUtil;
import com.example.birthdaytree.util.SharedPreferencesUtils;

public class ClientVerifyThread extends VerifyThread {
	String IP;
	Context context;
	static Person globalP = new Person();
	public ClientVerifyThread(Context context, String IP,Intent VerifyThreadIntent ) {
		// TODO Auto-generated constructor stub
		this.VerifyThreadIntent = VerifyThreadIntent;
		this.context = context;
		this.IP =IP;
		dbOperation = new DbOperation(context,
				"birthdayTree.db");
		user = dbOperation.queryFromID("user", 1);
		person = new Person();
		// 注册接受消息的广播
		Receiver dReceiver = new Receiver();
		IntentFilter filter = new IntentFilter(BirthdaytreeConstant.GET_CLASS);
		context.registerReceiver(dReceiver, filter);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			socket = new Socket(IP, 5000);
			inDataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			if(sendUser()){
				if(accept()){
					globalP = person;
					socket.close();
					inDataInputStream.close();
					dataOutputStream.close();	
					/*客户端接受服务器端发送的名片,并弹出对话框让客户端分类start*/
					Intent intent = new Intent();
					String id = person.getChatId();
					
					if(!dbOperation.queryChatId(id)){
						intent.setAction(BirthdaytreeConstant.GET_CARD);
						context.sendBroadcast(intent);//用广播实现service和activity的通信,这个是通知主界面弹出对话框
					};
				
		           /*end*/
				};	// 读json字符串,没有消息将会阻塞//收到了person,然后让弹出对话框让用户分类,这里没有while循环是因为,只用接受一次数据
			};
		

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String tableName = intent.getStringExtra("tableName");
			
			dbOperation.insertData(globalP, tableName);
			context.stopService(VerifyThreadIntent);
		}

	}

	Byte[] bytesToBytes(byte[] bytes){
		Byte[] Bytes = new Byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			Bytes[i] = new Byte(bytes[i]);
		}
		return null;
		
	}
}
