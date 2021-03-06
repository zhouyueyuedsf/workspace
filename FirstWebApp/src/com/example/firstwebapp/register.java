package com.example.firstwebapp;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.SaveListener;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

public class register extends Activity implements OnClickListener {
	EditText name,password,email;
	Button sure;
	BmobUser person,person2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "fcf58aecfa90a19ef54e8898ee5a6344");
		if((person2=BmobUser.getCurrentUser(this))!=null)
		{
			Bundle bundle =new Bundle();
			
			bundle.putSerializable("person", person2);
			Intent intent = new Intent(register.this,Login.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}else{
			setContentView(R.layout.activity_main);
			name=(EditText)this.findViewById(R.id.username);
			password=(EditText)this.findViewById(R.id.password);
			email=(EditText)this.findViewById(R.id.email);
			sure=(Button)this.findViewById(R.id.sure);
			sure.setOnClickListener(this);
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sure:
			handle();
			break;

		default:
			break;
		}
	}
	private void handle() {
		person = new BmobUser();
		person.setUsername(name.getText().toString());
		person.setPassword(password.getText().toString());
		person.setEmail(email.getText().toString());
		/*
		 * 注册账号,后台会检查是否改用户名与邮件是独一无二的你也可以要求用户使用Email做为用户名注册，你
		 * 在提交信息的时候可以将输入的“用户名“默认设置为用户的Email地址，以后在用户忘记密码的情况下可以使用Bmob提供重置密码功能。
		 */
		person.signUp(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
				//这个函数可以封装
				Toast.makeText(register.this, "写入数据成功", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(register.this,MainActivity.class);
				startActivity(intent);
			}
			
			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(register.this, "写入数据失败"+msg, Toast.LENGTH_LONG).show();
			}
		});
	}

}
