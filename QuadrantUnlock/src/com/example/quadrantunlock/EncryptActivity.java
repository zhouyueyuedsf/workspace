package com.example.quadrantunlock;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EncryptActivity extends Activity {
	LinearLayout layout;
	private int[] pwdI = {0,0,0,0,0,0,0,0,0,0};
	private Point[] point = new Point[10];
	int count = -1;
	Timer timer;
	TimeBroadcast broadcast;
	String pwdS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encrypt);
		
		Point p = new Point();
		p.x = 0;
		p.y = 0;
		for(int i = 0; i < 10; i++){
			point[i] = p;
		}
		pwdS = SharedPreferencesUtils.getString(EncryptActivity.this, "password");	
		layout = (LinearLayout)this.findViewById(R.id.click_layout);
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					count++;
					point[count] = getPoint(event);			
					if(timer != null){
						timer.cancel();
					}
					if(count >= 3){
						//2s之内如果有点击事件,就停止计时器
						timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
									//计算原点坐标,确定象限,计算密码是否符合
									Point O = new Point();
									O.x = 0;
									O.y = 0;
									for(int i =0; i <= count; i++){
										O.x = O.x + point[i].x;
										O.y = O.y + point[i].y;
										Log.v(printf(point[i]),"------>");
									}
									O.x = O.x/(count+1);
									O.y = O.y/(count+1);
									Log.v(printf(O),"------>");
									
									//确定象限
									for(int i = 0; i <= count; i++){
										if(O.x < point[i].x && O.y > point[i].y){
											//第一象限
											pwdI[i] = 1;
										}
										if(O.x > point[i].x && O.y > point[i].y){
											//第二象限
											pwdI[i] = 2;
										}
										if(O.x > point[i].x && O.y < point[i].y){
											//第三象限
											pwdI[i] = 3;
										}
										if(O.x < point[i].x && O.y < point[i].y){
											//第四象限
											pwdI[i] = 4;
										}
									}
									String pwd ="";
									for(int i = 0; i < 10; i++){
										if(pwdI[i] != 0){
											pwd = pwd + String.valueOf(pwdI[i]);
										}
									}
									for(int i = 0; i < 10; i++){
										pwdI[i] = 0;
									}
								String p = Encrypt.MD5(pwd);//将所输入的密码进行加密,与md5加密后的序列比较
									Log.v(pwd, "------>");
									count = -1;
									if(pwdS.equals(p)){
										Intent intent = new Intent(EncryptActivity.this, MainActivity.class);
										startActivity(intent);
									}
								}
							private String printf(Point o) {
								// TODO Auto-generated method stub
								String x = String.valueOf(o.x);
								String y = String.valueOf(o.y);
								String str = "(" + x + "," + y + ")";
								return str;
							}
							
						}, 2000);
					}		
				
				
				}
				return false;
			}

			private Point getPoint(MotionEvent event) {
				// TODO Auto-generated method stub
					Point point = new Point();
					
					point.x = event.getX();
					point.y = event.getY();	
				return point;
			}
		});
	}
	public class TimeBroadcast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
