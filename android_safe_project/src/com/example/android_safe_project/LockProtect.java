package com.example.android_safe_project;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.guoshisp.apdd.db.dao.AppLockDao;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LockProtect extends Service {

	boolean flag;
	private List<String> lockPacknames;
	private List<String>  tempStopProtectPacknames;
	private AppLockDao dao;
	private Intent okIntent;
private MyBinder binder;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		binder=new MyBinder();
		return binder;
	}
private class MyBinder extends Binder implements IService
{

	@Override
	public void callTempStopProtect(String packname) {
		// TODO Auto-generated method stub
		tempStopProtect(packname);
	}	
}
private void tempStopProtect(String packname) {
	// TODO Auto-generated method stub
	tempStopProtectPacknames.add(packname);
}
	@Override
	public void onCreate() {
		super.onCreate();
		dao = new AppLockDao(this);
		Log.v("AppLockDao", "--->");
		flag = true;
		okIntent = new Intent(this, OkActivity.class);
		okIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		tempStopProtectPacknames=new ArrayList<String>();
		new Thread() {
			public void run() {
				while (flag) {
					ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
					RunningTaskInfo taskInfo = am.getRunningTasks(1).get(0);
					lockPacknames = dao.findAll();
					String packname = taskInfo.topActivity.getPackageName();
					Log.v(packname, "--->");
					if(tempStopProtectPacknames.contains(packname))
					{
						try{
							Thread.sleep(200);
						}catch(InterruptedException e)
						{
							e.printStackTrace();
						}
						continue;
					}
					okIntent.putExtra("packname", packname);
					Log.v("putExtra", "--->");
					Boolean bool=lockPacknames.contains(packname);
					Log.v(bool.toString(), "----->");
					if (lockPacknames.contains(packname)) {
						startActivity(okIntent);
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onDestroy() {
		flag = false;
		super.onDestroy();
	}



}
