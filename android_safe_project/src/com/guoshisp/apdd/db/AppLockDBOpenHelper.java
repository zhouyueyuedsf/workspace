package com.guoshisp.apdd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AppLockDBOpenHelper extends SQLiteOpenHelper {

	public AppLockDBOpenHelper(Context context) {
		// ����һ��Ӧ�������ģ������������ݿ�����
		// ���������α깤������null��ʾʹ��ϵͳĬ�ϵ��α깤�����󣬲����ģ��汾��
		super(context, "applock.db", null, 1);
	}

	/**
	 * ���ݿ��һ�α�������ʱ��ִ�и÷��� �ڸ÷����У�һ������ָ�����ݿ�ı�ṹ
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// �����������ı� (���а���_id,���� )
		db.execSQL("create table applock (_id integer primary key autoincrement, packname varchar(20))");
	}

	/**
	 * �����ݿ�İ汾�� �������ӵ�ʱ����õķ���. һ���������������,�������ݿ�ı�ṹ.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
