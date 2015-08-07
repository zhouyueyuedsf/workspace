package com.dispatch.tab04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 监听应用程序安装监听器
 * @author zhouyueyue
 *
 */
public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// 接收广播：系统启动完成后运行该应用
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {  
       }  
       //接收广播：设备上新安装了一个应用程序包后自动启动新安装应用程序。  
       if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {  
    	   System.out.println("应用已安装");
    	   String packageName = intent.getDataString();
    	   if(packageName.contains("music")){
    		   SharedPreferencesUtils.putString(context, "music", "noop");    		       		   
    	   }else if(packageName.contains("browser")){       		   
    		   SharedPreferencesUtils.putString(context, "browser", "noop");    		   
    	   }else if(packageName.contains("taobao")){
    		   SharedPreferencesUtils.putString(context, "taobao", "deadline");    		   
    	   }else if(packageName.contains("weixin")){
    		   SharedPreferencesUtils.putString(context, "weixin", "deadline");    		   
    		   
    	   }
       }  
	}

}
