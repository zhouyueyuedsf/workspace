package com.dispatch.tab04;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
/**
 * 应用打开监听器服务
 * @author zhouyueyue
 *
 */
public class OpenAppListener extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread() {
			public void run() {
				//看门狗
				while (true) {
					ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
					RunningTaskInfo taskInfo = am.getRunningTasks(1).get(0);
					String packageName = taskInfo.topActivity.getPackageName();
					if (packageName.contains("browser")) {
						String name = "browser"; 
						assignIOWay(name);
					} else if (packageName.contains("music")) {						
						String name = "music"; 
						assignIOWay(name);
					}else if(packageName.contains("weixin")){
						String name = "weixin"; 
						assignIOWay(name);
					}else if(packageName.contains("taobao")){
						String name = "taobao"; 
						assignIOWay(name);
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			private void assignIOWay(String name) {
				String IOway = SharedPreferencesUtils
						.getString(OpenAppListener.this, name);
						LinuxSystemTool.setIOWay(name);
						SharedPreferencesUtils.putString(OpenAppListener.this,"IOway", IOway);
			};
		};
		return super.onStartCommand(intent, flags, startId);
	}
}
