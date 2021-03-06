package com.example.birthdaytree;

import java.io.Serializable;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * ����
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-20 ����9:55:34
 */
public abstract class BaseActivity extends Activity {

	protected int mScreenWidth;//��Ļ���	
	protected int mScreenHeight;//��Ļ�߶�
	int item = 0;
	public static final String TAG = "bmob";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		
		setContentView();
		initViews();
		initListeners();
		initData();
	}
	public abstract void setContentView();
	public abstract void initViews();
	public abstract void initListeners();
	public abstract void initData();
	Toast mToast;
	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_LONG);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	/** 
	  * getStateBar
	  * @Title: getStateBar
	  * @throws
	  */
	public  int getStateBar(){
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	public static int dip2px(Context context,float dipValue){
		float scale=context.getResources().getDisplayMetrics().density;		
		return (int) (scale*dipValue+0.5f);		
	}
	public void Back(Context res,Class des){
		Intent intent = new Intent(res,des);
		startActivity(intent);
	}
	public void sendData(Context res,Class des,String key,Serializable value){
		Bundle bundle = new Bundle();	
		bundle.putSerializable(key, value);
		Intent intent = new Intent(res,des);
		intent.putExtras(bundle);
		startActivity(intent);
	}

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
