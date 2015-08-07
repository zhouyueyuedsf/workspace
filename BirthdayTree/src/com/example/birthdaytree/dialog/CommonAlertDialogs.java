package com.example.birthdaytree.dialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.birthdaytree.MainActivity;
import com.example.birthdaytree.R;
import com.example.birthdaytree.ShowPersonBirthdayDetail;
import com.example.birthdaytree.bean.RemindTime;
import com.example.birthdaytree.config.BirthdaytreeConstant;
import com.example.birthdaytree.datepicker.DatePicker;
import com.example.birthdaytree.util.AlarmClockUtil;

public class CommonAlertDialogs {
	 static int item;
	public static String classfiy = "";
	/**
	 * 时间提醒弹出对话框
	 * @param context
	 */
	public static void chooseRemindDateDialog(final Context context){

		final Activity the = (Activity) context;	
		final CheckBox dateBefore0,dateBefore1,dateBefore3,dateBefore5,dateBefore7;
		
		RelativeLayout dialog = (RelativeLayout)the.getLayoutInflater().inflate(
				R.layout.choose_remind_date_dialog, null);
		dateBefore0 = (CheckBox)dialog.findViewById(R.id.datebefore0);
		dateBefore1 = (CheckBox)dialog.findViewById(R.id.datebefore1);
		dateBefore3 = (CheckBox)dialog.findViewById(R.id.datebefore3);
		dateBefore5 = (CheckBox)dialog.findViewById(R.id.datebefore5);
		dateBefore7 = (CheckBox)dialog.findViewById(R.id.datebefore7);
		//这里或许能够用设计模式优化
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("选择提醒时间")
				.setView(dialog)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								RemindTime time = new RemindTime() ;
								TreeSet<Integer> days =  new TreeSet<Integer>();
								if(dateBefore0.isChecked()){
									days.add(0);
								}
								if(dateBefore1.isChecked()){
									days.add(1);
								}
								if(dateBefore3.isChecked()){
									days.add(3);
								}
								if(dateBefore5.isChecked()){
									days.add(5);
								}
								if(dateBefore7.isChecked()){
									days.add(7);
								}	
								time.day = days;
						
								setAlarm(time);
								
							}

							private void setAlarm(RemindTime time) {
								// TODO Auto-generated method stub
								AlarmClockUtil alarmClockUtil = new AlarmClockUtil(the);
								try {
									alarmClockUtil.setAlarm(classfiy, time);
									Toast.makeText(context, "闹钟已设置", Toast.LENGTH_LONG).show();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							}
						})
				.setNeutralButton("关闭",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
							}
						}).create().show();
	
	}
	/**
	 * 
	 * @param context
	 */
	public static void datePickerDialog(Context context){
		Activity the = (Activity) context;
		DatePicker datePicker;
		Calendar mCalendar;

		LinearLayout dialog = (LinearLayout)the.getLayoutInflater().inflate(
				R.layout.activity_light, null);
		
		mCalendar = Calendar.getInstance();
		datePicker = (DatePicker)dialog.findViewById(R.id.datePicker);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("选择提醒时间")
				.setView(dialog)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}})
				.setNeutralButton("关闭",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}}).create().show();
	
	
	}
	public static void showCommonDialog(final Context context,final String flag,final int id){

		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context).setTitle("面对面验证").setMessage(
				"添加一个朋友");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("flag", flag);
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			intent.setAction("com.example.birthdaytree.service.ServerVerifyService");
			context.startService(intent);
			}
		});
		builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
			}
		});
		builder.create().show();
	
		
	}

	public static void showSingleDialog(final Context context) {
		 final String[] items = new String[] { "父母", "同学", "同事", "恋人", "朋友", "长辈", "兄弟" }; 
		
         new AlertDialog.Builder(context). 
                setTitle("数据已接受,设置分类").
                setIcon(R.drawable.ic_launcher) 
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() { 
  
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	item = which;
                    } 
                }). 
                setPositiveButton("确认", new DialogInterface.OnClickListener() { 
 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	String classfiy = "";
                    	switch (item) {
                    	case 0:
                    		 classfiy = "parents";
                    		break;
						case 1:
							 classfiy = "classmates";
							
							break;
						case 2:
							
							 classfiy = "colleague";
							break;
						case 3:
							 classfiy = "lover";
							
							break;
						case 4:
							 classfiy = "friends";
							
							break;
						case 5:
							classfiy = "elders";
							
							break;
						case 6:
							classfiy = "brothers";
							
							break;

						}
                    
                    	  	Intent intent = new Intent();
                        	intent.setAction(BirthdaytreeConstant.GET_CLASS);
                        	intent.putExtra("tableName", classfiy);
                        	context.sendBroadcast(intent);
                    	
                  
                    } 
                }). 
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }). 
                create().show(); 
	}


}
