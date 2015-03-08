package com.example.birthdaytree.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Person{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String date;
	private String sex;
	private String hobby;
	private Bitmap headImage;
	public Person() {
		// TODO Auto-generated constructor stub
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