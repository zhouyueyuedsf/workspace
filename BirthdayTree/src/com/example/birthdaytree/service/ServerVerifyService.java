package com.example.birthdaytree.service;


import com.example.birthdaytree.thread.ServerVerifyThread;
import com.example.birthdaytree.thread.ServiceThreadForServer;
import com.example.birthdaytree.thread.WifiApServerManager;
	
import com.example.birthdaytree.util.Encrypt;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class ServerVerifyService extends Service{
	private WifiApServerManager apServerManager;
	private String mMac;
	private WifiManager wifiManager;
	private WifiStateReceiver wifiStateReceiver;
	private WifiApStateReceiver wifiApStateReceiver;
	Intent ServerIntent;
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
		this.ServerIntent = intent;
		wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		apServerManager = WifiApServerManager.getInstance(wifiManager);
		   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	        if (wifiInfo == null || wifiInfo.getMacAddress() == null)
	        {
	            // 打开Wifi开关以便Wifi上报MAC
	            wifiManager.setWifiEnabled(true);
	        }
	        else
	        {
	            mMac = wifiInfo.getMacAddress();
	        
	        }
	        wifiStateReceiver = new WifiStateReceiver();
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
	        registerReceiver(wifiStateReceiver, intentFilter);
	        
	        wifiApStateReceiver = new WifiApStateReceiver();
	        IntentFilter intentFilter2 = new IntentFilter();
	        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
	        registerReceiver(wifiApStateReceiver, intentFilter2);
	  	  	
		ServiceThreadForServer serviceThreadForServer = new ServiceThreadForServer(apServerManager, mMac);
		serviceThreadForServer.start();
		String flag = intent.getStringExtra("flag");
		int id = intent.getIntExtra("id", 0);
			ServerVerifyThread serverThread = new ServerVerifyThread(this, flag, id,ServerIntent);
        	serverThread.start();//开启socket
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
	
		super.onDestroy();
		unregisterReceiver(wifiApStateReceiver);
		unregisterReceiver(wifiStateReceiver);
	}
	public class WifiApStateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

        	//
            int state = intent.getIntExtra(WifiApServerManager.EXTRA_WIFI_AP_STATE, -1);
            // 判断Wifi AP状态（由于在不同的API level中，值的定义不一致，反射获取较麻烦，此处直接用magic number判断）
            switch (state)
            {
                case 0:
                case 10:
                {
                  
                    break;
                }
                case 1:
                case 11:
                {
          
                    
                    break;
                }
                case 2:
                case 12:
                {
                 
                    break;
                }
                case 3:
                case 13:
                {
                
                    break;
                }
                case 4:
                case 14:
                {
                  
                    break;
                }
                default:
                {
                  
                    break;
                }
            }
        
		}
		
	}
	public class WifiStateReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

            String action = intent.getAction();
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action))
            {
            	//下面俩种方法都能得到wifi的状态
//            	int state = mWifiManager.getWifiState();
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                switch (state)
                {
                    case WifiManager.WIFI_STATE_DISABLING:
                    {
                        
                        break;
                    }
                    case WifiManager.WIFI_STATE_DISABLED:
                    {
                       
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLING:
                    {
                      
                        break;
                    }
                    case WifiManager.WIFI_STATE_ENABLED:
                    {
                
                        mMac = wifiManager.getConnectionInfo().getMacAddress();
                        // Wifi已上报MAC，关闭Wifi开关
                        wifiManager.setWifiEnabled(false);

                        break;
                    }
                    case WifiManager.WIFI_STATE_UNKNOWN:
                    {
                      
                        break;
                    }
                    default:
                    {
                       
                        break;
                    }
                }
            }
        
		}
		
	}
}
