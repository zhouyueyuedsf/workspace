package com.example.birthdaytree.dialog;

import com.example.birthdaytree.R;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.util.AlarmClockUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AlarmDialogActivity extends Activity {
	TextView reText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_remind_dialog);
		reText = (TextView) this.findViewById(R.id.remindText);
		Intent intent = getIntent();

		new AlarmClockUtil(this).setNotifaction(
				 intent.getStringExtra("personName"),
				intent.getIntExtra("clockNumber", 0));
	}
}
