package com.guoshisp.apdd.engine;

import com.guoshisp.apdd.db.dao.AppLockDao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * �������ṩ���У�����ֻ��Ҫ���Ķ����ݿ������ɾ������
 * @author Administrator
 *
 */
public class AppLockDBProvider extends ContentProvider {
	private static final int ADD = 1;
	// content://com.guoshisp.applock/ADD
	// content://com.guoshisp.applock/DELETE
	//��ȡ�������ݿ�Ķ���
	private AppLockDao dao;
	//����ƥ����
	private static final int DELETE = 2;
	public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	//����ƥ��·��
	static {
		//ƥ��Uri������һ������������������ָ�����ݿ��еı�������һЩҵ���߼���add��delete�ȣ�
		//��������ƥ���롣Ҳ��ִ��ƥ��ʱ�ж�ƥ���Ƿ���ȷ��matcher.match(uri)
		matcher.addURI("com.guoshisp.applock", "ADD", ADD);
		matcher.addURI("com.guoshisp.applock", "DELETE", DELETE);
	}
	@Override
	public boolean onCreate() {
		dao = new AppLockDao(getContext());
		return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//ƥ��URI
		int result = matcher.match(uri);
		//�ж��Ƿ�����ӵ�ƥ�����
		if (result == ADD) {
			//��ȡ����ӵİ�����ContentValues����Item���������ӵģ�
			String packname = values.getAsString("packname");
			//��ӵ����ݿ���
			dao.add(packname);
			//�������ݵı仯֪ͨ
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = matcher.match(uri);
		if (result == DELETE) {
			dao.delete(selectionArgs[0]);
			//�������ݵı仯֪ͨ
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
}
