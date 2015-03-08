package com.example.birthdaytree;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	private int screenWidth;// 屏幕像素宽度
	private int screenHeight;// 屏幕像素高度
	private float density;// 屏幕密度
	String table;
	private ImageView bothers, classmates, colleague, elders, friends, lover,
			parents;
	private RelativeLayout.LayoutParams parentsParams, bothersParams,
			classmatesParams, colleagueParams, eldersParams,friendsParams,loverParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String dialogName = "朋友生日概览";
				table = "friends";//查询那个数据库
				int bpacmc ;//该月生日的人数
				int birthdayingPerson;//即将生日的人
				showDialog(dialogName);
				
			}
		});
	
	}
/*
 * 动态布局已适应不同屏幕
 */
	public void setContentView() {
		// TODO Auto-generated method stub
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setBackgroundResource(R.drawable.birthdaytreetwo);
		relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		initViews();
	
		screenWidth = getScreenPixWidth();
		screenHeight = getScreenPixHeight();
	
		density = getDensity();
		Log.v(String.valueOf(density), "====");

		initData();
		relativeLayout.addView(parents, parentsParams);
		relativeLayout.addView(bothers,bothersParams);
		relativeLayout.addView(classmates,classmatesParams);
		relativeLayout.addView(colleague,colleagueParams);
		relativeLayout.addView(elders,eldersParams);
		relativeLayout.addView(friends,friendsParams);
		relativeLayout.addView(lover,loverParams);
		
		setContentView(relativeLayout);
	}
/*
 * 获取dpi函数
 */
	private float getDensity() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return  dm.density;
	}
/*
 * 获取像素高度
 */
	private int getScreenPixHeight() {
		// TODO Auto-generated method stub
//		WindowManager windowManager = getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
//		return display.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int heightPixels= dm.heightPixels;
	
		return heightPixels;
	}
/*
 * 获取像素宽度
 */
	private int getScreenPixWidth() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels= dm.widthPixels;
		return widthPixels;
//		WindowManager windowManager = getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
//		return display.getWidth();
	}

	public void initViews() {
		// TODO Auto-generated method stub
		bothers = new ImageView(this);
		classmates = new ImageView(this);
		colleague = new ImageView(this);
		elders = new ImageView(this);
		friends = new ImageView(this);
		lover = new ImageView(this);
		parents = new ImageView(this);

	}

	public void initListeners() {
		// TODO Auto-generated method stub

	}

	public void initData() {
		// TODO Auto-generated method stub
		parents.setBackgroundResource(R.drawable.parents);
		bothers.setBackgroundResource(R.drawable.bothers);
		classmates.setBackgroundResource(R.drawable.classmates);
		colleague.setBackgroundResource(R.drawable.colleague);
		elders.setBackgroundResource(R.drawable.elders);
		friends.setBackgroundResource(R.drawable.friends);
		lover.setBackgroundResource(R.drawable.lover);
		//背景图未553*990,而组件大小也已知,所以可以得到组件在不同屏幕上的粗略大小,可以适配所有的屏幕
		 parentsParams = new RelativeLayout.LayoutParams(
					(int) (73/(553.0/screenWidth)), (int) (134/(990.0/screenHeight)));
	
//		 利用组件在原图上的比例计算,在不同屏幕上的位置
		 parentsParams.setMargins((int) (0.254 * screenWidth),
					(int) (0.449 * screenHeight), 0, 0);
		 parents.setId(1);
		 
		 bothersParams = new RelativeLayout.LayoutParams(
					(int) (97/(553.0/screenWidth)),(int) (125/(990.0/screenHeight)));
		 bothersParams.setMargins((int) (0.698 * screenWidth),
					(int) (0.322 * screenHeight), 0, 0);
		 bothers.setId(2);
		 
		 classmatesParams = new RelativeLayout.LayoutParams(
				 (int)(93/(553.0/screenWidth)), (int)(130/(990.0/screenHeight)));
		 classmatesParams.setMargins((int) (0.195* screenWidth),
					(int) (0.204 * screenHeight), 0, 0);
		 classmates.setId(3);
		 
		 colleagueParams = new RelativeLayout.LayoutParams(
				 (int)(76/(553.0/screenWidth)), (int)(127/(990.0/screenHeight)));
		 colleagueParams.setMargins((int) (0.249 * screenWidth),
					(int) (0.061 * screenHeight), 0, 0);
		 colleague.setId(4);
		 
		 eldersParams = new RelativeLayout.LayoutParams(
				 (int)(147/(553.0/screenWidth)),(int) (67/(990.0/screenHeight)));
		 eldersParams.setMargins((int) (0.696 * screenWidth),
					(int) (0.222* screenHeight), 0, 0);
		 elders.setId(5);
		 
		 friendsParams = new RelativeLayout.LayoutParams(
				 (int)(97/(553.0/screenWidth)), (int)(111/(990.0/screenHeight)));
		 friendsParams.setMargins((int) (0.655 * screenWidth),
					(int) (0.078 * screenHeight), 0, 0);
		 friends.setId(6);
		 
		 loverParams = new RelativeLayout.LayoutParams(
				 (int)(60/(553.0/screenWidth)), (int)(123/(990.0/screenHeight)));
		 loverParams.setMargins((int) (0.465 * screenWidth),
					((int) (0.003 * screenHeight)), 0, 0);
		 lover.setId(7);
	}

	private void showDialog(String dialogName) {
		// TODO Auto-generated method stub
		LinearLayout dialog = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(dialogName).setView(dialog).setPositiveButton("查看详细", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ShowPersonBirthday.class);
				Bundle bundle = new  Bundle();
				bundle.putString("class", table);//传送分类信息
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}).setNeutralButton("添加生日", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent  intent = new Intent(MainActivity.this,AddPersonBirthday.class);
				Bundle bundle = new  Bundle();
				bundle.putString("class", table);//传送分类信息
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		}).create().show();
	}

}
