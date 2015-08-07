package com.example.wificlientdemo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	WifiManager wifiManager;
	WifiStateReceiver wifiStateReceiver;
	String mGatewayIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo == null){
        	wifiManager.setWifiEnabled(true);
        }
        wifiStateReceiver = new WifiStateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);//注册广播
    }

    public class WifiStateReceiver extends BroadcastReceiver{
    
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			 String action = intent.getAction();
			if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action))
            {
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                NetworkInfo.State state = networkInfo.getState();
                switch (state)
                {
                    case CONNECTED:
                    {
                        mGatewayIP = WifiApClientUtil.getGatewayIP(wifiManager);
                        Log.v(mGatewayIP, "------>");
                        ClientThread clientThread = new ClientThread(mGatewayIP);
                        clientThread.start();
                      
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
    
}
