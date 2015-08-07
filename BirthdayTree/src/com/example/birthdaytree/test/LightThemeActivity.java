package com.example.birthdaytree.test;

import java.util.Calendar;

import com.example.birthdaytree.R;
import com.example.birthdaytree.datepicker.DatePicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//如果在andoridManifest声明为dialog的话显示不出来
public class LightThemeActivity extends Dialog {
	private Context context;
	  private String title;
	  private String confirmButtonText;
	  private String cacelButtonText;
	public LightThemeActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public LightThemeActivity(Context context, int theme,String title, String confirmButtonText, String cacelButtonText) {
	        super(context, theme);
	        this.context = context;
	        this.title = title;
	        this.confirmButtonText = confirmButtonText;
	        this.cacelButtonText = cacelButtonText;
	       
	    }
	DatePicker datePicker;
	Calendar mCalendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light);
		mCalendar = Calendar.getInstance();
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		  Window dialogWindow = getWindow();
		      WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		          DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		           lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		           dialogWindow.setAttributes(lp);
	}
}
