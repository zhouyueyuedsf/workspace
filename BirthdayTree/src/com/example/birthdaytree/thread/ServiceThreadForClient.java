package com.example.birthdaytree.thread;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class ServiceThreadForClient extends Thread{
	Context context;
	
	public ServiceThreadForClient(Context context){
		this.context = context;
	}
	@Override
	public void run() {
		WifiAdmin wifiAdmin = new WifiAdmin(context);			
		wifiAdmin.openWifi();
		while(true){
			if(wifiAdmin.IsExsits("birthdayTree") == null){
				continue;
			}else{
			wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo("birthdayTree", "", 1));
			break;
			}
		}
			
	}
}
