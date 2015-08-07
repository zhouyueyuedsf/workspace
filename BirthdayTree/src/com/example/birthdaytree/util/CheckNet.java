package com.example.birthdaytree.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNet extends Activity {
	
	public boolean isNet(){
		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
		  NetworkInfo mWiFiNetworkInfo =con.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		if((mWiFiNetworkInfo != null&&mWiFiNetworkInfo.isAvailable())|internet){  
		    //执行相关操作  
			return true;
		}else{  
		 return false;
		}  
	}
}
