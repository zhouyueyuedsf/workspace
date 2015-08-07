package com.example.birthdaytree;

import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.config.BirthdaytreeConstant;
import com.example.birthdaytree.dialog.CommonAlertDialogs;
import com.example.birthdaytree.dialog.CommonDialogs;
import com.example.birthdaytree.slidingmenu.SlidingMenu;
import com.example.birthdaytree.util.AlarmClockUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.OnGestureListener;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener,OnTouchListener{
	private int screenWidth;// 灞忓箷鍍忕礌瀹藉害
	private int screenHeight;// 灞忓箷鍍忕礌楂樺害
	private float density;// 灞忓箷瀵嗗害
	String table;
	private ImageView brothers, classmates, colleague, elders, friends, lover,
			parents;
	private RelativeLayout showMyself, sendMyself, relView,setting,aboutMe,telMe;
	private RelativeLayout.LayoutParams parentsParams, bothersParams,
			classmatesParams, colleagueParams, eldersParams, friendsParams,
			loverParams;
	static public RelativeLayout relativeLayout;
	DbOperation dbOperation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		new AlarmClockUtil(this);//只要打开软件就执行一次,防止一年过后就没了
		registerForContextMenu(friends);
//		friends.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//			
//
//			}
//		});

	}

	public void setContentView() {
		// 得到主布局relativeLayout
		relativeLayout = (RelativeLayout) getPartView(R.layout.activity_main);
		// 动态初始化主布局relativeLayout里面的文件
		initViews();
		// initListeners();
		// 得到屏幕的像素宽度,高度,已更好的适应各种屏幕
		screenWidth = getScreenPixWidth();
		screenHeight = getScreenPixHeight();
		// 得到屏幕的dpi(每平方英寸有density个像素点)
		density = getDensity();
		initData();
		// 添加已经设置好参数的组件
		relativeLayout.addView(parents, parentsParams);
		relativeLayout.addView(brothers, bothersParams);
		relativeLayout.addView(classmates, classmatesParams);
		relativeLayout.addView(colleague, colleagueParams);
		relativeLayout.addView(elders, eldersParams);
		relativeLayout.addView(friends, friendsParams);
		relativeLayout.addView(lover, loverParams);
		// 动态布局与静态布局结合的技术,目的是为了向slidingmenu_main
		// 布局文件里面的子节点添加布局relativeLayout
		relView = (RelativeLayout) getPartView(R.layout.slidingmenu_main);
		
		SlidingMenu slideView = (SlidingMenu) relView.getChildAt(0);// 得到子布局
		LinearLayout childLayout = (LinearLayout) slideView.getChildAt(0);// 得到子布局的子布局.问题:为什么不直接getChild(1)?
		childLayout.addView(relativeLayout);
		setContentView(relView);
		// 初始化侧滑窗口的主件
		sendMyself = (RelativeLayout) relView.findViewById(R.id.send_myself);
		showMyself = (RelativeLayout) relView.findViewById(R.id.show_myself);
		setting = (RelativeLayout)relView.findViewById(R.id.setting);
		aboutMe = (RelativeLayout)relView.findViewById(R.id.about_me);
		telMe = (RelativeLayout)relView.findViewById(R.id.tel_me);
		setting.setOnClickListener(this);
		telMe.setOnClickListener(this);
		aboutMe.setOnClickListener(this);
		sendMyself.setOnClickListener(this);
		showMyself.setOnClickListener(this);
		
		//长按监听注册
		friends.setOnLongClickListener(this);
		classmates.setOnLongClickListener(this);
		classmates.setOnLongClickListener(this);
		elders.setOnLongClickListener(this);
		lover.setOnLongClickListener(this);
		parents.setOnLongClickListener(this);
		brothers.setOnLongClickListener(this);
		
		classmates.setOnClickListener(this);
		colleague.setOnClickListener(this);
		elders.setOnClickListener(this);
		lover.setOnClickListener(this);
		parents.setOnClickListener(this);
		friends.setOnClickListener(this);
		brothers.setOnClickListener(this);
		// 注册接受消息的广播
		dialogReceiver dReceiver = new dialogReceiver();
		IntentFilter filter = new IntentFilter(BirthdaytreeConstant.GET_CARD);
		registerReceiver(dReceiver, filter);
	}

	// 由布局文件得到view
	public View getPartView(int res) {
		// 将xml布局文件生成view对象通过LayoutInflater
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 将view对象挂载到那个父元素上，这里没有就为null
		return inflater.inflate(res, null);
	}

	/*
	 * 鑾峰彇dpi鍑芥暟
	 */
	private float getDensity() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

	/*
	 * 鑾峰彇鍍忕礌楂樺害
	 */
	private int getScreenPixHeight() {
		// TODO Auto-generated method stub
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// return display.getHeight();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int heightPixels = dm.heightPixels;

		return heightPixels;
	}

	/*
	 * 鑾峰彇鍍忕礌瀹藉害
	 */
	private int getScreenPixWidth() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		return widthPixels;
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// return display.getWidth();
	}

	public void initViews() {
		// TODO Auto-generated method stub
		brothers = new ImageView(this);
		classmates = new ImageView(this);
		colleague = new ImageView(this);
		elders = new ImageView(this);
		friends = new ImageView(this);
		lover = new ImageView(this);
		parents = new ImageView(this);

	}

	public void initListeners() {
		// TODO Auto-generated method stub
		sendMyself.setOnClickListener(this);
		
	}

	public void initData() {
		// TODO Auto-generated method stub
		parents.setBackgroundResource(R.drawable.parents);
		brothers.setBackgroundResource(R.drawable.bothers);
		classmates.setBackgroundResource(R.drawable.classmates);
		colleague.setBackgroundResource(R.drawable.colleague);
		elders.setBackgroundResource(R.drawable.elders);
		friends.setBackgroundResource(R.drawable.friends);
		lover.setBackgroundResource(R.drawable.lover);
		//已553*990为标准
		parentsParams = new RelativeLayout.LayoutParams(
				(int) (73 / (553.0 / screenWidth)),
				(int) (134 / (990.0 / screenHeight)));

		parentsParams.setMargins((int) (0.254 * screenWidth),
				(int) (0.449 * screenHeight), 0, 0);
		parents.setId(1);

		bothersParams = new RelativeLayout.LayoutParams(
				(int) (97 / (553.0 / screenWidth)),
				(int) (125 / (990.0 / screenHeight)));
		bothersParams.setMargins((int) (0.698 * screenWidth),
				(int) (0.322 * screenHeight), 0, 0);
		brothers.setId(2);

		classmatesParams = new RelativeLayout.LayoutParams(
				(int) (93 / (553.0 / screenWidth)),
				(int) (130 / (990.0 / screenHeight)));
		classmatesParams.setMargins((int) (0.195 * screenWidth),
				(int) (0.204 * screenHeight), 0, 0);
		classmates.setId(3);

		colleagueParams = new RelativeLayout.LayoutParams(
				(int) (76 / (553.0 / screenWidth)),
				(int) (127 / (990.0 / screenHeight)));
		colleagueParams.setMargins((int) (0.249 * screenWidth),
				(int) (0.061 * screenHeight), 0, 0);
		colleague.setId(4);

		eldersParams = new RelativeLayout.LayoutParams(
				(int) (147 / (553.0 / screenWidth)),
				(int) (67 / (990.0 / screenHeight)));
		eldersParams.setMargins((int) (0.696 * screenWidth),
				(int) (0.222 * screenHeight), 0, 0);
		elders.setId(5);

		friendsParams = new RelativeLayout.LayoutParams(
				(int) (97 / (553.0 / screenWidth)),
				(int) (111 / (990.0 / screenHeight)));
		friendsParams.setMargins((int) (0.655 * screenWidth),
				(int) (0.078 * screenHeight), 0, 0);
		friends.setId(6);

		loverParams = new RelativeLayout.LayoutParams(
				(int) (60 / (553.0 / screenWidth)),
				(int) (123 / (990.0 / screenHeight)));
		loverParams.setMargins((int) (0.465 * screenWidth),
				((int) (0.003 * screenHeight)), 0, 0);
		lover.setId(7);
	}

	private void showDialog(String dialogName) {
		
		LinearLayout dialog = (LinearLayout)this.getLayoutInflater().inflate(
				R.layout.common_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(dialogName)
				.setView(dialog)
				.setPositiveButton("查看详细",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MainActivity.this,
										ShowPersonBirthday.class);
								intent.putExtra("class", table);
								startActivity(intent);
							}
						})
				.setNeutralButton("添加生日",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MainActivity.this,
										AddPersonBirthday.class);
								Bundle bundle = new Bundle();
								bundle.putString("class", table);// 浼犻�佸垎绫讳俊鎭�
								intent.putExtras(bundle);
								startActivity(intent);

							}
						}).create().show();
	}
	
	@Override
	public void onClick(View v) {
		String dialogName = "生日情况概览";
		if(v == friends){
			// TODO Auto-generated method stub
			table = "friends";
			showDialog(dialogName);
		}
		if(v == elders){
			table = "elders";
			showDialog(dialogName);
		}
		if(v == classmates){
			table = "classmates";
			showDialog(dialogName);
			
		}
		if(v == parents){
			table = "parents";
			showDialog(dialogName);
			
		}
		if(v == colleague){
			table = "colleague";
			
			showDialog(dialogName);
		}
		if(v == lover){
			
			table = "lover";
			showDialog(dialogName);
		}
		if(v == brothers){
			
			table = "brothers";
			showDialog(dialogName);
		}
		switch (v.getId()) {
		case R.id.send_myself:
			// 客户端
			Intent intent = new Intent();
			intent.setAction("com.example.birthdaytree.service.ClientVerifyService");
			startService(intent);
			break;

		case R.id.show_myself:
			Bundle bundle = new Bundle();
			bundle.putInt("ID", 1);
			bundle.putString("flag", "user");
			Intent intent2 = new Intent(MainActivity.this,
					ShowPersonBirthdayDetail.class);
			intent2.putExtras(bundle);
			startActivity(intent2);
			break;
		case R.id.setting:
			
			break;
		case R.id.about_me:
			
			break;
		case R.id.tel_me:
			break;
		}
	}
/**
 * 调用菜单
 * @param keyCode
 */
	private void simulateKey(final int keyCode) {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run() {
				try {
					Instrumentation inst = new Instrumentation();
					inst.sendKeyDownUpSync(keyCode);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
	}
	public class dialogReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
				CommonAlertDialogs.showSingleDialog(MainActivity.this);
		}

	}
	
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if(v == friends){
			CommonAlertDialogs.classfiy = "friends";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == elders){
			CommonAlertDialogs.classfiy = "elders";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == classmates){
			CommonAlertDialogs.classfiy = "classmates";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == lover){
			CommonAlertDialogs.classfiy = "lover";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == brothers){
			CommonAlertDialogs.classfiy = "brothers";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == colleague){
			CommonAlertDialogs.classfiy = "colleague";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		if(v == parents){
			CommonAlertDialogs.classfiy = "parents";
			CommonAlertDialogs.chooseRemindDateDialog(MainActivity.this);
			return true;
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.send_myself:
			v.setBackgroundColor(Color.BLACK);
			break;

		default:
			break;
		}
		return false;
	}
}
