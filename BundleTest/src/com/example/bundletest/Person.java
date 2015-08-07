package com.example.bundletest;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {	
	String str;
	
    Bitmap bit;
	public Person() {
		// TODO Auto-generated constructor stub
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Bitmap getBit() {
		return bit;
	}
	public void setBit(Bitmap bit) {
		this.bit = bit;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
		
		@Override
		public Person[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Person[size];
		}
		
		@Override
		public Person createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			Person person = new Person();
			person.str = in.readString();
			person.bit = Bitmap.CREATOR.createFromParcel(in);
			return person;
		}
	};
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(str);
		bit.writeToParcel(parcel, 0);
	}
	
}
