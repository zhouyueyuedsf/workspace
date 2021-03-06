package com.example.slidedatetimepicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmActivity extends Activity {
	MediaPlayer alarmMusic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		alarmMusic = MediaPlayer.create(this, R.raw.BOBO);
		alarmMusic.setLooping(true);
		alarmMusic.start();
		new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟").setMessage(
				"你的生日到了").setPositiveButton("确定",new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						alarmMusic.stop();
						AlarmActivity.this.finish();
					}
				}).show();
	}
}
