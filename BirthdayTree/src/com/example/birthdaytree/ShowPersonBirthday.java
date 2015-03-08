package com.example.birthdaytree;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.birthdaytree.adapterframe.BaseAdapterHelper;
import com.example.birthdaytree.adapterframe.QuickAdapter;
import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.util.BitmapUtil;

import android.app.Activity;
import android.app.ApplicationErrorReport.CrashInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
	private ListView listView;
	private QuickAdapter<Person> adapter;
	private TextView back;

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
		flag = (String) intent.getSerializableExtra("class");
		dbOperation = new DbOperation(ShowPersonBirthday.this,
				"birthdayTree.db");
		setListView();
	}

	private void setListView() {
		// TODO Auto-generated method stub
		if (flag.equals("friends")) {
			ArrayList<Person> friendsPersons = dbOperation.queryAllData(flag);
			listView = (ListView) this.findViewById(R.id.listview);
			adapter = new QuickAdapter<Person>(this,
					R.layout.show_bithday_listview) {

				@Override
				protected void convert(BaseAdapterHelper helper, Person item) {
					if (item.getHeadImage() == null) {
						helper.setBackgroundRes(R.id.portrait,
								android.R.drawable.ic_menu_gallery);
					} else {
						helper.setImageBitmap(R.id.portrait,
								item.getHeadImage());
					}
					helper.setText(R.id.birthName, item.getName())
							.setText(R.id.calendarFlag, "新历")
							.setText(R.id.date, item.getDate())
							.setText(R.id.apartDay, "明天");
				}
			};

			adapter.addAll(friendsPersons);
			listView.setAdapter(adapter);

		}

	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		back = (TextView) this.findViewById(R.id.back_showbirthday);

	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	//listView监听器
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Person person1 = adapter.getItem(position);
			Bundle bundle = new Bundle();
			bundle.putString("name", person1.getName());
			bundle.putString("date", person1.getDate());
			bundle.putString("hobby", person1.getHobby());
			bundle.putString("sex",person1.getSex());
			if(person1.getHeadImage() != null){
				bundle.putByteArray("headimage", BitmapUtil.bmpTobytes(person1.getHeadImage()));
			}else{
				bundle.putByteArray("headimage", null);
			}
			
			Intent intent = new Intent(ShowPersonBirthday.this,ShowPersonBirthdayDetail.class);
			intent.putExtras(bundle);
			startActivity(intent);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.back) {
			Back(ShowPersonBirthday.this, MainActivity.class);
		}
	}
}