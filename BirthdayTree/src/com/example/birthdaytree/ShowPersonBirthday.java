package com.example.birthdaytree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.example.birthdaytree.adapterframe.BaseAdapterHelper;
import com.example.birthdaytree.adapterframe.QuickAdapter;
import com.example.birthdaytree.adapterframe.SwipeListView;
import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.dialog.CommonAlertDialogs;
import com.example.birthdaytree.util.AlarmClockUtil;
import com.example.birthdaytree.util.BitmapUtil;
import com.example.birthdaytree.util.TimeUtil;

import android.app.Activity;
import android.app.ApplicationErrorReport.CrashInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ShowPersonBirthday extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private Person person;
	private String flag;
	private DbOperation dbOperation;
	private SwipeListView listView;
	private QuickAdapter<Person> adapter;
	private TextView back, addFriend;
	private Map<Integer, Person> mapFriendsPersons;
	int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.show_birthday_listview_frame);
		Intent intent = getIntent();
		flag = intent.getStringExtra("class");
		dbOperation = new DbOperation(ShowPersonBirthday.this,
				"birthdayTree.db");

	}

	private void setListView() {
		// TODO Auto-generated method stub
		mapFriendsPersons = dbOperation.queryAllData(flag);
		List<Person> friendsPersons = new ArrayList<Person>();
		for (Map.Entry<Integer, Person> entry : mapFriendsPersons.entrySet()) {
			// Person friendsPerson=entry.getValue();
			friendsPersons.add(entry.getValue());
		}
		showListView(friendsPersons);
		// listView = (ListView) this.findViewById(R.id.listview);

	}

	private void showListView(List<Person> friendsPersons) {
		// TODO Auto-generated method stub
		adapter = new QuickAdapter<Person>(this, R.layout.show_bithday_listview) {

			@Override
			protected void convert(final BaseAdapterHelper helper,
					final Person item) {

				if (item.getHeadImage() == null) {
					helper.setBackgroundRes(R.id.portrait,
							android.R.drawable.ic_menu_gallery);
				} else {
					helper.setImageBitmap(R.id.portrait, item.getHeadImage());
				}
				
				position = helper.getPosition();
				helper.setText(R.id.birthName, item.getName())
						.setText(R.id.calendarFlag, "新历")
						.setText(R.id.date, item.getDate())
						.setText(
								R.id.apartDay,
								TimeUtil.getSurplus(Calendar.getInstance()
										.getTimeInMillis(), TimeUtil
										.getDateTime(AlarmClockUtil
												.toStandardFormat(item
														.getDate())))
										+ "天后");
				helper.setBackgroundRes(R.id.item_right, R.color.right_bg);
				helper.setText(R.id.item_right_txt, "删除");
				helper.getView(R.id.item_right).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								listView.deleteItem(listView
										.getChildAt(position));
								dbOperation.delete(item, flag);
								setListView();// 这里把我逗了,直接setlistVEIW();
								ShowToast("已删除此项");
							}
						});
			}
		};
		adapter.addAll(friendsPersons);
		listView.setAdapter(adapter);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		back = (TextView) this.findViewById(R.id.back_showbirthday);
		addFriend = (TextView) this.findViewById(R.id.save);
		listView = (SwipeListView) this.findViewById(R.id.listview);
		setListView();
	}

	@Override
	public void initListeners() {
		back.setOnClickListener(this);
		addFriend.setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// bean类改后这里的性能有点问题
		Person person = adapter.getItem(position);
		int ID = 1;// 初始化id
		for (Map.Entry<Integer, Person> entry : mapFriendsPersons.entrySet()) {
			if (person.equals(entry.getValue())) {
				ID = entry.getKey();
			}
		}
		Bundle bundle = new Bundle();
		bundle.putInt("ID", ID);
		bundle.putString("flag", flag);
		Intent intent = new Intent(ShowPersonBirthday.this,
				ShowPersonBirthdayDetail.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.back) {
			Back(ShowPersonBirthday.this, MainActivity.class);
		} else if (v.getId() == R.id.save) {
			CommonAlertDialogs.showCommonDialog(ShowPersonBirthday.this, flag,
					1);
		}

	}

}
