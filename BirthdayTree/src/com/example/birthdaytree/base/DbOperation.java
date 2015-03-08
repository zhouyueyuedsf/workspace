package com.example.birthdaytree.base;

import java.util.ArrayList;

import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.util.BitmapUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DbOperation {
	DbHelper dbHelper;

	public DbOperation(Context context, String dbname) {
		dbHelper = new DbHelper(context, dbname, 4);
		Log.v("DbHelper", "----->");
	}

	public boolean insertData(Person person,String tableName) {
//		String sql = "insert into '"+tableName+"' (name,date,sex,hobby,user_image) values('"
//				+ person.getName() + "','" + person.getDate() + "','"
//				+ person.getSex() + "','" + person.getHobby() + "','"
//				+ BitmapUtil.bmpToBlob(person.getHeadImage()) + "') ";
		ContentValues cv = new ContentValues();
		cv = BitmapUtil.bmpToBlob(person.getHeadImage());
		cv.put("name", person.getName());
		cv.put("date", person.getDate());
		cv.put("sex", person.getSex());
		cv.put("hobby", person.getHobby());	
		
		dbHelper.getWritableDatabase().insert(tableName, null, cv);
		dbHelper.close();
		return true;
	}
	public ArrayList<Person> queryAllData(String tableName){
		String sql = "select * from '"+tableName+"'";
		Cursor cursor= dbHelper.getReadableDatabase().rawQuery(sql, null);	
		ArrayList<Person> persons = new ArrayList<Person>();
		while(cursor.moveToNext()){
			Person person = new Person();
			person.setName(cursor.getString(1));
			Log.v(cursor.getString(1), "+++++");
			person.setDate(cursor.getString(2));
			person.setSex(cursor.getString(3));
			person.setHobby(cursor.getString(4));	
			byte[] in = cursor.getBlob(cursor.getColumnIndex("user_image"));
			Bitmap image = BitmapFactory.decodeByteArray(in, 0, in.length);
			if(image!=null){
				Log.v("image!=null", "======");
				person.setHeadImage(image);
			}else{	
				Log.v("image==null","======");
				person.setHeadImage(null);
			}			
			
			persons.add(person);
		}
		
		return persons;
		
	}
	public boolean insertData(Object object, String column) {
		String sql;
		if (column.equals("name")) {
			String name = (String) object;
			sql = "insert into user (name) values ('" + name + "')";// 这里的是加号作为连接符
		} else if (column.equals("birthday")) {
			String birthday = (String) object;
			sql = "insert into user (date) values ('" + birthday + "')";// 这里的是加号作为连接符
		} else if (column.equals("sex")) {
			String sex = (String) object;
			sql = "insert into user (sex) values ('" + sex + "')";// 这里的是加号作为连接符
		} else if (column.equals("hobby")) {
			String hobby = (String) object;
			sql = "insert into user (hobby) values ('" + hobby + "')";// 这里的是加号作为连接符
		} else if (column.equals("image")) {
			sql = "";
		} else {
			sql = "";
		}
		dbHelper.getWritableDatabase().execSQL(sql);
		return true;
	}
}