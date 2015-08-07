package com.example.birthdaytree.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Person extends BmobObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;//用户主键
	private String chatId;//发起聊天的唯一标识
	private String name;
	private String date;
	private String sex;
	private String hobby;
	private Bitmap headImage;
	public Person() {
		// TODO Auto-generated constructor stub
		
	}
	public Person(int id ,String chatId, String name, String date, String sex, String hobby, Bitmap image){
		this.ID = id;
		this.chatId = chatId;
		this.name = name;
		this.date = date;
		this.sex = sex;
		this.hobby = hobby;
		this.ID = id;	
		if(image!=null){
			Log.v("image!=null", "======");
		this.headImage = image;
		}else{	
			Log.v("image==null","======");
		this.headImage = null;
		}
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Bitmap getHeadImage() {
		return headImage;
	}
	public void setHeadImage(Bitmap headImage) {
		this.headImage = headImage;
	}
	
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
}
