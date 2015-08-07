package com.guoshisp.apdd.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guoshisp.apdd.db.AppLockDBOpenHelper;

public class AppLockDao {
	private AppLockDBOpenHelper helper;

	public AppLockDao(Context context) {
		helper = new AppLockDBOpenHelper(context);
	}

	public boolean find(String packname) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select * from applock where packname=?",
					new String[] { packname });
			if (cursor.moveToFirst()) {
				return true;
			}
			cursor.close();
			db.close();
		}
		return result;
	}

	public boolean add(String packname) {
		if (find(packname))
			return false;

		SQLiteDatabase db = helper.getWritableDatabase();

		if (db.isOpen()) {

			db.execSQL("insert into applock (packname) values (?)",
					new Object[] { packname });

			db.close();

		}

		return find(packname);
	}

	public void delete(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from applock where packname=?",
					new Object[] { packname });
			db.close();
		}
	}

	public List<String> findAll() {
		List<String> packnames = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select packname from applock", null);
			while (cursor.moveToNext()) {
				packnames.add(cursor.getString(0));
			}
			cursor.close();
			db.close();
		}
		return packnames;
	}
}
