package com.example.android_safe_project;

import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class Set1Activity extends Activity {

	private Button button;
	private Switch switch1;
	private SharedPreferences sp;// 用于保存sim卡是否被绑定的信息，以便程序下次加载时使用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set1);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.v("s", "-->");
		button = (Button) this.findViewById(R.id.button1);
		Log.v("s", "-->");
		switch1 = (Switch) this.findViewById(R.id.switch1);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		String simseral = sp.getString("simserial", "");
		Log.v("s", "-->");

		if (TextUtils.isEmpty(simseral)) {
			switch1.setChecked(false);
		} else {
			switch1.setChecked(true);
		}
		Log.v("s", "---->");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Set1Activity.this,
						Set2Activity.class);
				 startActivity(intent);
				 final Intent intent2=new
				 Intent(Set1Activity .this,LockProtect.class);
				 intent2.setAction("org.crazyit.service.FiRST_SERVICE");
				 startService(intent2);
			}
		});

		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				String simseral = sp.getString("simserial", "");
				if (TextUtils.isEmpty(simseral)) {// sim卡未绑定
					Editor editor = sp.edit();
					editor.putString("simserial", getSimSerial());
					editor.commit();
					// 因为sim卡的状态是未被绑定，所以，点击条目后应该设置为绑定的状态
					switch1.setChecked(true);
				} else {
					Editor editor = sp.edit();
					editor.putString("simserial", "");
					editor.commit();
					switch1.setChecked(false);
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String getSimSerial() {
		// sim卡是与电话相关的。需要在清单文件中配置权限：<uses-permission
		// android:name="android.permission.READ_PHONE_STATE" />
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// 返回sim卡的串号
		return tm.getSimSerialNumber();
	}
}
