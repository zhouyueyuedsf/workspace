package com.example.downloaddemo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceActivity.Header;

import com.example.downloaddemo.bean.ThreadInfo;
import com.example.downloaddemo.db.DBHelper;
import com.example.downloaddemo.db.ThreadDAO;

/**
 * 数据访问接口的实现
 * 
 * @author zhouyueyue
 * 
 */
public class ThreadDAOImpl implements ThreadDAO {
	private DBHelper dbHelper = null;

	public ThreadDAOImpl(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new DBHelper(context);
	}

	@Override
	public void insertThread(ThreadInfo threadInfo) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(
				"insert into thread_info (thread_id, url, start, end, finished) values (?,?,?,?,?)",
				new Object[] { threadInfo.getId(), threadInfo.getUrl(),
						threadInfo.getStart(), threadInfo.getEnd(),
						threadInfo.getFinished() });
		db.close();
	}

	@Override
	public void deleteThread(String url, int thread_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from thread_info where url = '" + url
				+ "' and thread_id = '" + thread_id + "'");
		db.close();
	}

	@Override
	public void updateThread(String url, int thread_id, int finished) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(
				"update thread_info set finished = ? where url = ? and thread_id = ?",
				new Object[] {finished, url, thread_id});
		db.close();
	}
	/*
	 * 该处用的单线程下载,此处的查询语句是为多线程准备的
	 * @see com.example.downloaddemo.db.ThreadDAO#getThreads(java.lang.String)
	 */
	@Override
	public List<ThreadInfo> getThreads(String url) {
		// TODO Auto-generated method stub
		List<ThreadInfo> threads = new ArrayList<ThreadInfo>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
		while(cursor.moveToNext()){
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
			threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
			threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
			threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
			threads.add(threadInfo);
		}
		db.close();
		return threads;
	}

	@Override
	public boolean isExists(String url, int thread_id) {
		// TODO Auto-generated method stub
		List<ThreadInfo> threads = new ArrayList<ThreadInfo>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, thread_id+""});
		boolean bool = cursor.moveToNext();
		return bool;
	}

}
