package com.example.android_safe_project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Set2Activity extends Activity {
	private EditText et_setup2_number;// ���ð󶨵İ�ȫ����
	private Button button;
	private Button button2;
	private SharedPreferences sp;// ���ڴ洢��ȫ���뼰��ȫ����Ļ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set2);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.v("s", "-->");
		et_setup2_number = (EditText) findViewById(R.id.et_setup2_number);
		button = (Button) this.findViewById(R.id.button3);
		button2 = (Button) this.findViewById(R.id.button2);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// ���ݵĻ��ԡ����û�б������ȫ���룬���Ե��ǿ�
		String number = sp.getString("safemuber", "");
		et_setup2_number.setText(number);
		// �O����һ���İ��o
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String number = et_setup2_number.getText().toString().trim();
				if (TextUtils.isEmpty(number)) {
					Toast.makeText(Set2Activity.this, "��ȫ���벻��Ϊ��", 0).show();
					return;
				}
				// ��EditText�еİ�ȫ����־û�������Ҳ�������ݵĻ���
				Editor editor = sp.edit();
				editor.putString("safemuber", number);
				editor.commit();

				Intent intent = new Intent(Set2Activity.this,
						Set3Activity.class);
				startActivity(intent);
				finish();

			}
		});
		// �O����һ���İ��o
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Set2Activity.this,
						Set1Activity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set2, menu);
		return true;
	}

}
