package com.example.birthdaytree.bean;

import java.io.Serializable;

public class TransportPerson implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String chatId;//发起聊天的唯一标识
	public String name;
	public String date;
	public String sex;
	public String hobby;
//	public Byte[] bytes;
	
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
	
}
