package com.example.birthdaytree.service;

import java.util.List;

import com.example.birthdaytree.service.ServerVerifyService.WifiStateReceiver;
import com.example.birthdaytree.thread.ClientVerifyThread;
import com.example.birthdaytree.thread.ServiceThreadForClient;
import com.example.birthdaytree.util.Encrypt;
import com.example.birthdaytree.util.WifiApClientUtil;



import android.R.bool;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

public class ClientVerifyService extends Service{
	WifiManager wifiManager;
	WifiStateReceiver wifiStateReceiver;
	WifiInfo wifiInfo;
	boolean bool = false;
	Intent ClientVerifyIntent;
	List<WifiConfiguration> existingConfigs ;
	 int count = 0;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ClientVerifyIntent = intent;
		// 注册一个广播,用于监听wifi状态的变化
		registerBroadcast();
		//连接wifi耗时操作用子线程完成
		ServiceThreadForClient serviceThreadForClient = new ServiceThreadForClient(this);
		serviceThreadForClient.start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	

	

	private void registerBroadcast() {
		 	wifiStateReceiver = new WifiStateReceiver();
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
	        registerReceiver(wifiStateReceiver, intentFilter);//注册广播
	        //注册弹出对话框广播
	}
	 public class WifiStateReceiver extends BroadcastReceiver{
		    
			@Override
			public void onReceive(Context context, Intent intent) {
				 String action = intent.getAction();
				if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action))
	            {
	                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
	                NetworkInfo.State state = networkInfo.getState();
	                switch (state)
	                {
	                    case CONNECTED:
	                    {
	                    	if(count == 0){
	                    		wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);	
	                    		wifiInfo = wifiManager.getConnectionInfo();
	                    		String s = wifiInfo.getSSID();
	                    		String SSID = "birthdayTree";
	                    		if(wifiInfo.getSSID().equals(SSID)){
	                    			wifiManager.setWifiEnabled(true);
	                    			String mGatewayIP = WifiApClientUtil.getGatewayIP(wifiManager);
	                    			ClientVerifyThread clientVerifyThread = new ClientVerifyThread(ClientVerifyService.this, mGatewayIP,ClientVerifyIntent);
	                    			clientVerifyThread.start();
	                    			count++;
	                    		}
	                    	}
	                      
	                        break;
	                    }
	                    case CONNECTING:
	                    {
	                    	
	                        
	                        break;
	                    }
	                    case DISCONNECTED:
	                    {
	                       
	                        break;
	                    }
	                    case DISCONNECTING:
	                    {
	                      
	                        break;
	                    }
	                    case SUSPENDED:
	                    {
	                      
	                        break;
	                    }
	                    case UNKNOWN:
	                    {
	                       
	                        break;
	                    }
	                    default:
	                    {
	                        assert(false);
	                        break;
	                    }
	                }
	            }
			}
	    	
	    	
	    }
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(wifiStateReceiver);
	}
	
	
}
