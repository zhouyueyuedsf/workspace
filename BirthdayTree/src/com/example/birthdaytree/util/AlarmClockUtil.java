package com.example.birthdaytree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Time;

import com.example.birthdaytree.R;
import com.example.birthdaytree.ShowPersonBirthday;
import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.bean.RemindTime;
import com.example.birthdaytree.dialog.AlarmDialogActivity;

public class AlarmClockUtil {

	Calendar calendar = Calendar.getInstance();
	String classfiy;
	/**
	 * 生日时间
	 */
	long bDate;
	/**
	 * 提醒时间
	 */
	int rDay;
	Context context;
	String TITLE = "生日提醒";
	long year;

	public AlarmClockUtil(Context context) {
		this.context = context;
		year = calendar.get(Calendar.YEAR);

	}

	// 获得分类人的date,然后根据用户设置的提醒时间算出几月几号提醒
	/**
	 * 设置单个人的闹钟
	 * 
	 * @param person
	 * @param time
	 *            生日时间减去用户所设置的提前提醒天数
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public void setAlarm(final Person person, final RemindTime time)//该类还需要测试
			throws ParseException {
		List<DateTime> rTimes = getRemindedTime(person, time);
		Intent intent = new Intent(context, AlarmDialogActivity.class);// 当到达到指定时间时,弹出dialog
		intent.putExtra("personName", person.getName());
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
//		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		// 转换成java.util.Calendar对象
		Iterator it = time.day.iterator();
		for (int i = 0; i < rTimes.size()&&it.hasNext(); i++) {
			intent.putExtra("clockNumber", it.hasNext());
			am.set(AlarmManager.RTC_WAKEUP, toMills(rTimes.get(i), time),
					getDistinctPendingIntent(intent, i + 1));
		}

	}

	/**
	 * 设置通知
	 * 
	 * @param person
	 * @param time
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void setNotifaction(String personName,int time) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
			Notification notification = new Notification();
			notification.icon = icon;
			notification.tickerText = TITLE;
			notification.when = System.currentTimeMillis();
			
			//会遇到兼容性问题
//			Notification.Builder builder = new Notification.Builder(context);
			String contentTitle = TITLE;
			String contentText = "离" + personName + "的生日还有" + time
					+ "天";
			Intent intent = new Intent(context,
					ShowPersonBirthday.class);
			intent.putExtra("class", classfiy);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
					intent, 0);
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//			notification = builder.setTicker(TITLE)
//					.setSmallIcon(icon)
//					.setWhen(toMills(rTimes.get(i), time))//为何设置时间没用?
//					.setContentIntent(contentIntent)
//					.setContentTitle(contentTitle).setContentText(contentText)
//					.setDefaults(Notification.DEFAULT_VIBRATE).build();
//			
			notificationManager.notify(1, notification);
	}

	/**
	 * DateTime转毫秒数
	 * 
	 * @param dateTime
	 * @param time
	 * @return
	 */
	private long toMills(DateTime dateTime, RemindTime time) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		Date tempDate = dateTime.toDate();
		c.setTimeInMillis(tempDate.getTime());// 我只是设置了月和日,并没有设置小时
		c.set(Calendar.HOUR_OF_DAY, time.time);// 提醒的小时
		c.set(Calendar.MINUTE, 10);// 提醒的时间
		c.set(Calendar.SECOND, 0);// 提醒的秒数
		return c.getTimeInMillis();
	}
	public static String toStandardFormat(String str){
		String[] s = str.split("-");
		String tailStr;
		if(s[1].length() == 1 ){			
			 tailStr = "0" + s[1] + s[2];
		}else{
			
			 tailStr =s[1] + s[2];
		}

		String headStr =   Calendar.getInstance().get(Calendar.YEAR)+"";
		return headStr + tailStr;
	}
	/**
	 * 算出提醒的准确时间
	 * 
	 * @param person
	 * @param time
	 * @return
	 */
	private List<DateTime> getRemindedTime(Person person, RemindTime time) {
		// TODO Auto-generated method stub
		// 算出提醒时间
		DateTimeZone.setDefault(DateTimeZone.forID("Asia/Shanghai"));// 设置默认时区
		Calendar c = Calendar.getInstance();
		String str = person.getDate();
		str = toStandardFormat(str);
		bDate = TimeUtil.getDateTime(str);
		List<DateTime> rTimes = new ArrayList<DateTime>();
		long curMills = System.currentTimeMillis();
		if (bDate > curMills) {
			DateTime dt = new DateTime(bDate);
			Iterator<Integer> it = time.day.iterator();

			while (it.hasNext()) {
				rTimes.add(dt.minusDays(it.next())); // 设置了提醒的时间
			}
		} else {// 生日的日期小于当前时间
			String[] s = person.getDate().split("-");
			String tailStr;
			if(s[1].length() == 1 ){			
				 tailStr = "0" + s[1] + s[2];
			}else{
				
				 tailStr =s[1] + s[2];
			}
			String headStr = year + 1 + "";
			str = headStr + tailStr;
			bDate = TimeUtil.getDateTime(str);
			DateTime dt = new DateTime(bDate);
			Iterator<Integer> it = time.day.iterator();
			while (it.hasNext()) {
				rTimes.add(dt.minusDays(it.next())); // 设置了提醒的时间
			}
		}
		return rTimes;
	}

	/**
	 * 设置分类整体的闹钟
	 * 
	 * @param classfiy
	 * @throws ParseException
	 */
	public void setAlarm(String classfiy, RemindTime time)
			throws ParseException {
		// 遍历整个类
		DbOperation dbOperation = new DbOperation(context, "birthdayTree.db");

		Map<Integer, Person> persons = dbOperation.queryAllData(classfiy);
		this.classfiy = classfiy;
		for(Map.Entry<Integer, Person>entry:persons.entrySet()){
			Person person = entry.getValue();
			setAlarm(person, time);
			
		}
	}

	/**
	 * 设置生日树所有人的闹钟,相应在侧滑界面设置的情况
	 */
	public void setAlarm(RemindTime time) {

	}

	PendingIntent getDistinctPendingIntent(Intent intent, int requestId) {
		PendingIntent pi = PendingIntent.getActivity(context, requestId,
				intent, 0);
		return pi;
	}
}
