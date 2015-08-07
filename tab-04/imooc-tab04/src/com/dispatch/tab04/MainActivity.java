package com.dispatch.tab04;

import java.util.List;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.imooc.tab04.R;
import com.imooc.tab04.R.id;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity implements
		android.widget.RadioGroup.OnCheckedChangeListener {
	private ViewPager mViewPager;
	private TabPageIndicator mTabPageIndicator;
	private TabAdapter mAdapter;

	private RadioGroup radioGroup;
	private RadioButton radio1, radio2;
	public static int updateFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageScrollStateChanged(int arg0) {
				if(arg0 == 2){
					int pos = mViewPager.getCurrentItem();
					mTabPageIndicator.setViewPager(mViewPager, pos);
					switch (pos) {		
					case 0:
						LinuxSystemTool.setIOWay("noop");
						Toast.makeText(MainActivity.this, "启用noop算法", Toast.LENGTH_LONG).show();
						SharedPreferencesUtils.putString(MainActivity.this,"IOway", "noop");
						break;

					case 1:
						LinuxSystemTool.setIOWay("deadline");
						Toast.makeText(MainActivity.this, "启用deadline算法", Toast.LENGTH_LONG).show();
						SharedPreferencesUtils.putString(MainActivity.this,"IOway", "deadline");
						break;
						
					case 2:
						LinuxSystemTool.setIOWay("cfq");
						Toast.makeText(MainActivity.this, "启用cfq算法", Toast.LENGTH_LONG).show();
						SharedPreferencesUtils.putString(MainActivity.this,"IOway", "cfq");
						break;
					}
				}				
			}
		});
	}

	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		int pos = getViewPagerPosition();	
		//还未测试?
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
		if(SharedPreferencesUtils.getInt(this, "model") == 0){
			mViewPager.setAdapter(mAdapter);
			mTabPageIndicator.setViewPager(mViewPager, 1);
		}else{
			mAdapter = new TabAdapter(this,mViewPager, getSupportFragmentManager(), 0);
			mViewPager.setAdapter(mAdapter);
			mTabPageIndicator.setViewPager(mViewPager, pos);	
		}
	
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio1:
			radio1.setBackgroundColor(Color.BLACK);
			showDialog("即将进入手动模式", 0);
			break;

		case R.id.radio2:
			showDialog("即将进入自动模式", 1);
			break;
		}

	}
	/**
	 * 根据共享参数里面的算法得到pos,这里0,1,2
	 * @return
	 */
	private int getViewPagerPosition() {
		String ioWay = SharedPreferencesUtils.getString(this, "IOway");
		if(ioWay.equals("noop")){
			return 0;
		}else if(ioWay.equals("deadline")){
			return 1;
		}else if(ioWay.equals("cfq")){			
			return 2;
		}else{
			return 0;
		}
	}

	public void showDialog(String title, final int flag) {

		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
				.setTitle(title);
		builder.setPositiveButton("确定", new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// 判断系统运行的算法,设置初始位置
				int pos = getViewPagerPosition();
				if (flag == 1) {
					mAdapter = new TabAdapter(MainActivity.this,mViewPager,
							getSupportFragmentManager(), 1);
					radio1.setBackgroundColor(Color.parseColor("#cccccc"));
					radio2.setBackgroundColor(Color.BLACK);
					if(!isServiceRun(getApplicationContext(),
							"com.dispatch.tab04.OpenAppListener")){
						Intent startIntent = new Intent(MainActivity.this,OpenAppListener.class);
						startService(startIntent);
					}
				} else {
					mAdapter = new TabAdapter(MainActivity.this,mViewPager,
							getSupportFragmentManager(), 0);
					radio2.setBackgroundColor(Color.parseColor("#cccccc"));
					radio1.setBackgroundColor(Color.BLACK);
					if (isServiceRun(getApplicationContext(),
							"com.dispatch.tab04.OpenAppListener")) {
						Intent stopIntent = new Intent(MainActivity.this,OpenAppListener.class);
						stopService(stopIntent);
					}
				}
				mViewPager.setAdapter(mAdapter);
				mTabPageIndicator.setViewPager(mViewPager, pos);
			}
		});
		builder.setNegativeButton("关闭", new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// updateFlag = 0;
				radio1.setChecked(true);
			}
		});
		builder.create().show();

	}

	/**
	 * 判断服务是否后台运行
	 * 
	 * @param context
	 *            Context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRun(Context mContext, String className) {
		boolean isRun = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(40);
		int size = serviceList.size();
		for (int i = 0; i < size; i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRun = true;
				break;
			}
		}
		return isRun;
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	public void onPageScrollStateChanged(int arg0) {
		if(arg0 == 2){
			int pos = mViewPager.getCurrentItem();
			switch (pos) {		
			case 0:
				LinuxSystemTool.setIOWay("noop");
				Toast.makeText(MainActivity.this, "启用noop算法", Toast.LENGTH_LONG).show();
				SharedPreferencesUtils.putString(MainActivity.this,"IOway", "noop");
				break;

			case 1:
				LinuxSystemTool.setIOWay("deadline");
				Toast.makeText(MainActivity.this, "启用deadline算法", Toast.LENGTH_LONG).show();
				SharedPreferencesUtils.putString(MainActivity.this,"IOway", "deadline");
				break;
				
			case 2:
				LinuxSystemTool.setIOWay("cfq");
				Toast.makeText(MainActivity.this, "启用cfq算法", Toast.LENGTH_LONG).show();
				SharedPreferencesUtils.putString(MainActivity.this,"IOway", "cfq");
				break;
			}
		}
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int pos) {
		switch (pos) {		
		case 0:
			LinuxSystemTool.setIOWay("noop");
			Toast.makeText(MainActivity.this, "启用noop算法", Toast.LENGTH_LONG).show();
			SharedPreferencesUtils.putString(MainActivity.this,"IOway", "noop");
			break;

		case 1:
			LinuxSystemTool.setIOWay("deadline");
			Toast.makeText(MainActivity.this, "启用deadline算法", Toast.LENGTH_LONG).show();
			SharedPreferencesUtils.putString(MainActivity.this,"IOway", "deadline");
			break;
			
		case 2:
			LinuxSystemTool.setIOWay("cfq");
			Toast.makeText(MainActivity.this, "启用cfq算法", Toast.LENGTH_LONG).show();
			SharedPreferencesUtils.putString(MainActivity.this,"IOway", "cfq");
			break;
		}
	}

}
