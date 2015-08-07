package com.example.koulingclient;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.mysql.jdbc.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button button,button2;
	private EditText editText,editText2;
	Connection conn;
	Person person;
	private String object_id;
	private String username;
	private String password;
	String sql;
	Statement stmt;
	Handler handler;
	String ReturnFlag;

	private static String url="http://10.0.2.2/xampp/site/javaRequst/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub    		
        		Toast.makeText(MainActivity.this,ReturnFlag, Toast.LENGTH_LONG).show(); 		
        	}
        };
        initView();
    }


    private void initView() {
		// TODO Auto-generated method stub
        button = (Button)this.findViewById(R.id.button1);
        button2 = (Button)this.findViewById(R.id.button2);
        editText = (EditText)this.findViewById(R.id.editText1);
        editText2 = (EditText)this.findViewById(R.id.editText2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		//登录
		case R.id.button1:
			person = getPerson();
			person.getPerson(this, object_id, new GetPersonListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,"登录成功", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this, Client.class);
					intent.putExtra("name", person.getUsername());
					intent.putExtra("pwd", person.getPassword());
					startActivity(intent);
				}
				
				@Override
				public void onFailed() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,"登录失败", Toast.LENGTH_LONG).show();
				}
			});
		 break;
		//注册
		case R.id.button2:
			 person = getPerson();
			 person.save(this, new SavePersonListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,"注册成功", Toast.LENGTH_LONG).show(); 
					Intent intent = new Intent(MainActivity.this, Client.class);
					intent.putExtra("name", person.getUsername());
					intent.putExtra("pwd", person.getPassword());
					startActivity(intent);
				}
				
				@Override
				public void onFailed() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this,"注册失败,有重复的名字", Toast.LENGTH_LONG).show();
				}
			});
			break;
		}
	}


	private Person getPerson() {
		// TODO Auto-generated method stub
		username = editText.getText().toString().trim();
		password = editText2.getText().toString().trim();
		object_id = Encrypt.SHA(username + password).substring(0,11);//数据库唯一主键
		Person person = new Person();
		person.setObject_id(object_id);
		person.setPassword(password);
		person.setUsername(username);
		return person;
	}


	private void Post(String flag) {
		// TODO Auto-generated method stub
		PostThread postThread = new PostThread(url,username,password,flag,handler);
		postThread.start();
	}
}
