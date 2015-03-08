package com.example.birthdaytree.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	String CREAT_TABLE_USER = "create table user (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_PARENTS = "create table parents (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_FRIENDS = "create table friends (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_CLASSMATES = "create table classmates (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_COLLEAGUE = "create table colleague (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_ELDERS = "create table elders (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	String CREAT_TABLE_LOVER = "create table lover (_id integer primary key autoincrement,name text,date char(100),sex char(2),hobby text,user_image BLOB)";
	public DbHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		
	}
   //第一次使用初始化数据库时创建
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREAT_TABLE_USER);
		db.execSQL(CREAT_TABLE_PARENTS);
		Log.v("CREAT_TABLE_FRIENDS", "----->");
		db.execSQL(CREAT_TABLE_FRIENDS);
		db.execSQL(CREAT_TABLE_CLASSMATES);
		db.execSQL(CREAT_TABLE_COLLEAGUE);
		db.execSQL(CREAT_TABLE_ELDERS);
		db.execSQL(CREAT_TABLE_LOVER);
	}
	//当版本更新后创建
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	
		db.execSQL(CREAT_TABLE_PARENTS);
		Log.v("CREAT_TABLE_FRIENDS", "----->");
		db.execSQL(CREAT_TABLE_FRIENDS);
		db.execSQL(CREAT_TABLE_CLASSMATES);
		db.execSQL(CREAT_TABLE_COLLEAGUE);
		db.execSQL(CREAT_TABLE_ELDERS);
		db.execSQL(CREAT_TABLE_LOVER);
	}

}
