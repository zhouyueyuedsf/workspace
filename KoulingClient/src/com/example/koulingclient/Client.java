package com.example.koulingclient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.sax2.Driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.EditText;
import com.mysql.jdbc.*;

public class Client extends Activity {
	private EditText editText;
	String username;
	String password;
	static String curDateStr;
	String sql;
	Statement stmt;
	Connection conn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client);
		editText = (EditText)this.findViewById(R.id.dtkl);
		Intent intent = getIntent();
		username = intent.getStringExtra("name");
		password = intent.getStringExtra("pwd");
		final Handler handler =  new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				editText.setText(getKouLing());
			}
		};
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Calendar ca = Calendar.getInstance(); 
				 ca.setTime(new java.util.Date());
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH:mm");
				curDateStr = format.format(ca.getTime());
				Log.v(curDateStr, "------>");
				
				handler.sendEmptyMessage(0);
			}
		}, 0, 30000 * 2);
		
	}
	private String getKouLing() {
		// TODO Auto-generated method stub
		String str = username+Encrypt.MD5(password)+curDateStr;
		str = str.toLowerCase();
		Log.v(str, "------->");
		String str1 = Encrypt.MD5(str).substring(0,8).toLowerCase();//这个改成了8为数
		return str1;
	}
}
