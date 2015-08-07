package com.example.android_safe_project;

import java.io.File;
import java.util.List;
import java.util.Map;

import util.intService;
import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("NewApi")
public class OkActivity extends Activity {
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView ensure;
	 private ImageView cancel;
	private intService Share;
	private Map<String, ?> map1;
	SharedPreferences preferences;
	private Intent serviceIntent;
	private IService iService;
	private MyConn conn;
	private Float bottom, top, left, right;
	RelativeLayout.LayoutParams  layoutParams,layoutParams2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	serviceIntent=new Intent(this,LockProtect.class);
conn=new MyConn();
bindService(serviceIntent, conn,BIND_AUTO_CREATE);
		Share = new intService(this);
		map1 = Share.readboolean();
    
		if (map1 != null && !map1.isEmpty()) {
			bottom = Float.parseFloat(map1.get("Bottom").toString());
			Log.v(Float.toString(bottom), "------>");
			top = Float.parseFloat(map1.get("Top").toString());
			Log.v(Float.toString(top), "------>");
			left = Float.parseFloat(map1.get("Left").toString());
			Log.v(Float.toString(left), "------>");
			right = Float.parseFloat(map1.get("Right").toString());
			Log.v(Float.toString(right), "------>");
		}


		
		RelativeLayout layout = new RelativeLayout(this);
	layoutParams = new RelativeLayout.LayoutParams(
				right.intValue() - left.intValue(), bottom.intValue()
						- top.intValue());
		layoutParams.setMargins(left.intValue(),top.intValue(),
				0,0);
		View view1 = this.getLayoutInflater().inflate(
				R.layout.activity_ok, null);
		layoutParams2=new LayoutParams(1080, 1920);
		layoutParams2.setMargins(0,0,0,0);
	View view2=this.getLayoutInflater().inflate(R.layout.huise, null);
	view2.setAlpha((float) 0.60);
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		RunningTaskInfo taskInfo = am.getRunningTasks(2).get(1);
		String packname = taskInfo.topActivity.getPackageName();
		String SavePath = getSDCardPath() + "/android_safe_project" + "/"
				+ packname + "/ScreenImage";
		String picturestr1 = SavePath + "/Screen_.jpeg";
		String picturestr2 = SavePath + "/cuted.jpeg";
		bitmap1 = BitmapFactory.decodeFile(picturestr1);
		imageView1=new ImageView(this);
       imageView1.setImageBitmap(bitmap1);
		bitmap2 = BitmapFactory.decodeFile(picturestr2);
		imageView2 = new ImageView(this);
		imageView2.setImageBitmap(bitmap2);
		 layout.addView(imageView1);
		 layout.addView(imageView2, layoutParams);
		 layout.addView(view2);
		 layout.addView(view1,layoutParams2);
         setContentView(layout); 
         ensure=(ImageView)this.findViewById(R.id.imageView2);
         cancel=(ImageView)this.findViewById(R.id.imageView3);
   ensure.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 preferences=getSharedPreferences("config", Context.MODE_PRIVATE);
		 String safenumber=preferences.getString("safemuber","" );
		SmsManager smsManager = SmsManager.getDefault();  
		List<String> divideContents = smsManager.divideMessage("warning");    
		for (String text : divideContents) {    
		    smsManager.sendTextMessage(safenumber, null, "����ֻ��ѱ���", null, null);    
		}  
	}
});
	cancel.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 preferences=getSharedPreferences("config", Context.MODE_PRIVATE);
			 String safenumber=preferences.getString("safemuber","" );
			SmsManager smsManager = SmsManager.getDefault();  
			List<String> divideContents = smsManager.divideMessage("warning");    
			for (String text : divideContents) {    
			    smsManager.sendTextMessage(safenumber, null, "����ֻ��ѱ���", null, null);   
			}
		}
	});	
		imageView2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=getIntent();
				 String packname=intent.getStringExtra("packname");
				 iService.callTempStopProtect(packname);
				 finish();
			}
		});
    
	}
	  public class MyConn implements ServiceConnection	
      {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			iService=(IService) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
    	  
      }
	  @Override
	  protected void onDestroy()
	  {
		  super.onDestroy();
		  unbindService(conn);
	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ok, menu);
		return true;
	}

	private String getSDCardPath() {
		File sdcardDir = null;
		// 获取sdcard路径
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}

		return sdcardDir.toString();
	}
}
