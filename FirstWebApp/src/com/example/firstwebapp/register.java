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
		 * ע���˺�,��̨�����Ƿ���û������ʼ��Ƕ�һ�޶�����Ҳ����Ҫ���û�ʹ��Email��Ϊ�û���ע�ᣬ��
		 * ���ύ��Ϣ��ʱ����Խ�����ġ��û�����Ĭ������Ϊ�û���Email��ַ���Ժ����û��������������¿���ʹ��Bmob�ṩ�������빦�ܡ�
		 */
		person.signUp(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
				//����������Է�װ
				Toast.makeText(register.this, "д�����ݳɹ�", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(register.this,MainActivity.class);
				startActivity(intent);
			}
			
			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(register.this, "д������ʧ��"+msg, Toast.LENGTH_LONG).show();
			}
		});
	}

}