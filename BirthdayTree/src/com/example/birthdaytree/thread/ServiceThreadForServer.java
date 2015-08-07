package com.example.birthdaytree.thread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.birthdaytree.util.Encrypt;

public class ServiceThreadForServer extends Thread{
	private WifiApServerManager apServerManager;
	private String mMac;
	private WifiManager wifiManager;
	public ServiceThreadForServer(WifiApServerManager apServerManager, String mMac) {
		// TODO Auto-generated constructor stub
		this.apServerManager = apServerManager;
		this.mMac = mMac;
	}
	@Override
	public void run() {
				apServerManager.setWifiApEnabled(apServerManager.generateWifiConfiguration(
                WifiApServerManager.AuthenticationType.TYPE_NONE, "birthdayTree", mMac, null), true);
//		apServerManager.setWifiApEnabled(apServerManager.generateWifiConfiguration(
//                WifiApServerManager.AuthenticationType.TYPE_NONE, "birthdayTree", mMac, null), true);    
	 
	}
	
}
