package com.example.firstwebapp;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	private EditText loginusername, loginpassword;
	private Button login;
	BmobUser person;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Intent intent = getIntent();
		
		person = (BmobUser)intent.getSerializableExtra("person");
		
		loginusername = (EditText) this.findViewById(R.id.loginusername);
		loginpassword = (EditText) this.findViewById(R.id.loginpassword);
		login = (Button) this.findViewById(R.id.login);
		login.setOnClickListener(this);
		Log.v(person.getPassword(),"--->");
		loginusername.setText(person.getUsername());
		loginpassword.setText(person.getPassword());
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			
			login();
			break;

		default:
			break;
		}
	}

	private void login() {
		// TODO Auto-generated method stub
	
		person.setUsername(loginusername.getText().toString());
		person.setPassword(loginpassword.getText().toString());
		person.login(this, new SaveListener() {
		
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
				Toast.makeText(Login.this, "��½�ɹ�", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				
				Toast.makeText(Login.this, "��½ʧ��"+msg, Toast.LENGTH_LONG).show();
			}
		});
	}

}
