package com.example.slidedatetimepicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private Button start;
	private EditText birthday;
	AlarmManager aManager;
	private SlideDateTimeListener listener;
	private SimpleDateFormat mFormatter = new SimpleDateFormat(
			"MMMM dd yyyy hh:mm aa");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aManager = (AlarmManager) this.getSystemService(Service.ALARM_SERVICE);
		start = (Button) this.findViewById(R.id.start);
		birthday = (EditText) this.findViewById(R.id.birthday);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SlideDateTimePicker.Builder(getSupportFragmentManager())
						.setListener(listener).setInitialDate(new Date())
						.build().show();
			}
		});
		listener = new SlideDateTimeListener() {

			@Override
			public void onDateTimeSet(Date date) {
				// TODO Auto-generated method stub
				birthday.setText(mFormatter.format(date));
				Intent intent = new Intent(MainActivity.this,
						AlarmActivity.class);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this,
						0, intent, 0);

				aManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pi);
				Toast.makeText(MainActivity.this, "�趨���ӳɹ�", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onDateTimeCancel() {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "Canceled",
						Toast.LENGTH_SHORT).show();
			}
		};
	}

}
