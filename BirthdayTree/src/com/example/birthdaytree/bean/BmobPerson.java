package com.example.birthdaytree.bean;

import android.graphics.Bitmap;
import cn.bmob.v3.BmobObject;

public class BmobPerson extends BmobObject {
	private String chatId;//发起聊天的唯一标识
	private String name;
	private String date;
	private String sex;
	private String hobby;
	private Bitmap headImage;
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
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
	
}
