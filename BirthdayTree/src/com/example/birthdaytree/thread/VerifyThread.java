package com.example.birthdaytree.thread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.bean.TransportPerson;
import com.example.birthdaytree.util.JsonUtil;

public abstract class VerifyThread extends Thread {
	DbOperation dbOperation;
	Person person, user;
	public DataInputStream inDataInputStream;
	public DataOutputStream dataOutputStream;
	Socket client,socket;
	Intent VerifyThreadIntent;
	public Person TransToPerson(TransportPerson person1) {
		Person person = new Person();
		person.setChatId(person1.chatId);
		person.setName(person1.name);
		person.setDate(person1.date);
		person.setSex(person1.sex);
		person.setHobby(person1.hobby);
		return person;
	}
	public TransportPerson personToTrans(Person person2) {
		// TODO Auto-generated method stub
		TransportPerson person = new TransportPerson();
		person.chatId = person2.getChatId();
		person.name = person2.getName();
		person.date = person2.getDate();
		person.hobby = person2.getHobby();
		person.sex = person2.getSex();
	
//		if(person2.getHeadImage() != null){
//			person.bytes = (BitmapUtil.bmpTobytes(person2.getHeadImage()));
//		}
		return person;
	}
	public boolean sendUser(){
		try {	
			user = dbOperation.queryFromID("user", 1);
			TransportPerson transportPerson = personToTrans(user);
			String jsonStr = JsonUtil.TransportPersonToJson(transportPerson);
			dataOutputStream.writeUTF(jsonStr);//传输Json字符串
			
			//传输图片
			if(user.getHeadImage()!=null){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				user.getHeadImage().compress(Bitmap.CompressFormat.JPEG, 85, baos); 
				int size = baos.size();//得到大小
				dataOutputStream.writeInt(size);
				byte[] bytes = baos.toByteArray();
				dataOutputStream.write(bytes);//开始写二进制到输出流				
			}else{
				dataOutputStream.writeInt(0);
			}
			dataOutputStream.flush();
//			dataOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean accept(){
		try {
			String jsonStr = inDataInputStream.readUTF();// 读json字符串,没有消息将会阻塞
			TransportPerson transportPerson = JsonUtil
					.JsonToTransportPerson(jsonStr);
			person = TransToPerson(transportPerson);
			int size = inDataInputStream.readInt();// 读图片大小
			if(size != 0){
				byte[] bs = new byte[size]; // 分配缓存大小为size
				int len = 0;
				while (len < size) {
					len += inDataInputStream.read(bs, len, size - len);// 读出图像
				}
				Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				if (bitmap != null) {
					person.setHeadImage(bitmap);
				}
				
			}
//			inDataInputStream.close();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
