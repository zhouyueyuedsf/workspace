package com.example.birthdaytree.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.util.BitmapUtil;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DbOperation {
	static DbHelper dbHelper;
	private static DbOperation dbOperation = null;
	
	public DbOperation(){
	}
	
	public DbOperation(Context context, String dbname) {
		dbHelper = new DbHelper(context, dbname, 4);
	}
	
	public static synchronized DbOperation getInstance(Context context, String dbname){
		if(dbHelper == null){
			dbHelper = new DbHelper(context, dbname, 4);						
		}
		if(dbOperation == null){
			dbOperation = new DbOperation();
		}
		return dbOperation;
	}
	public boolean insertData(Person person,String tableName) {
		ContentValues cv = BitmapUtil.bmpToBlob(person.getHeadImage());
		cv.put("chatId", person.getChatId());
		cv.put("name", person.getName());
		cv.put("date", person.getDate());
		cv.put("sex", person.getSex());
		cv.put("hobby", person.getHobby());	
		
		
		//插入语句
		dbHelper.getWritableDatabase().insert(tableName, null, cv);
		dbHelper.close();
		return true;
	}
	public boolean queryChatId(String chatId){
		String sql = "select chatId from (select * from parents,friends,classmates,colleague,elders,lover) where chatId = '"+chatId+"';";
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql,null);
		if(cursor.getColumnCount() > 1){
			return true;
		}else{
			return false;
		}
	}
	public Person queryFromID(String tableName,int ID){
		
		String sql = "select * from '"+tableName+"' where _id = '"+ID+"'";
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, null);
		Person person = null;
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String chatId = cursor.getString(1);
			String name = cursor.getString(2);
			String date = cursor.getString(3);
			String sex = cursor.getString(4);
			String hobby = cursor.getString(5);
			byte[] in = cursor.getBlob(cursor.getColumnIndex("user_image"));
			Bitmap image;
			if(in != null){
				 image = BitmapFactory.decodeByteArray(in, 0, in.length);
			}else{
				 image = null;
			}
			
			 person = new Person(id, chatId, name, date, sex, hobby, image);
		}
		
		return person;
		
	}
	/**
	 * 
	 * @param tableName:表名
	 * @return 返回一个Map类型,对应的ID,和Person对象
	 */
	public Map<Integer, Person> queryAllData(String tableName){
		String sql = "select * from '"+tableName+"'";
		Cursor cursor= dbHelper.getReadableDatabase().rawQuery(sql, null);	
		Map<Integer, Person> map = new HashMap<Integer, Person>();
		ArrayList<Person> persons = new ArrayList<Person>();
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String chatId = cursor.getString(1);
			String name = cursor.getString(2);
			String date = cursor.getString(3);
			String sex = cursor.getString(4);
			String hobby = cursor.getString(5);
			byte[] in = cursor.getBlob(cursor.getColumnIndex("user_image"));
			Bitmap image;
			if(in != null){
				 image = BitmapFactory.decodeByteArray(in, 0, in.length);
			}else{
				image = null;
			}
			Person person = new Person(id, chatId, name, date, sex, hobby, image);		
			
			map.put(Integer.parseInt(cursor.getString(0)), person);
			
			
		}
		cursor.close();//游标关闭会造成异常
		return map;
		
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

	public void delete(Person person,String tableName) {
		// TODO Auto-generated method stub
		int id = person.getID();
		String sql = "delete from '"+tableName+"' where _id = '"+id+"'";
		dbHelper.getWritableDatabase().execSQL(sql);
	}
	/**
	 * 通过chatId去更新
	 * @param chatId
	 * @param tableName
	 */
	public void update(String chatId, String tableName){
		
	}
	/**
	 * 同过Id去更新
	 * @param person
	 * @param tableName
	 */
	public void update(Person person, String tableName) {
		// TODO Auto-generated method stub
		int id = person.getID();
		String chatId = person.getChatId();
		String name = person.getName();
		String date = person.getDate();
		String hobby = person.getHobby();
		String sex = person.getSex();
		Bitmap headImage = person.getHeadImage();
		ContentValues cv = new ContentValues();
		cv = BitmapUtil.bmpToBlob(headImage);
		cv.put("chatId", chatId);
		cv.put("name", name);
		cv.put("date", date);
		cv.put("sex", sex);
		cv.put("hobby",hobby);	
		String[] whereArgs = {String.valueOf(id)};
		dbHelper.getWritableDatabase().update(tableName, cv, "_id=?", whereArgs);
	}
}
