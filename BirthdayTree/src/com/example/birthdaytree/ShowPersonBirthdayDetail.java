package com.example.birthdaytree;

import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;
import com.example.birthdaytree.chat.ChatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowPersonBirthdayDetail extends BaseActivity implements
		OnClickListener {
	Person person;
	private ImageView save;
	private TextView chat;// 验证按钮
	private ImageView GuestHeadImage;
	private LineEditText GuestName, GuestHobby, GuestBirthday;
	private LineEditText GuestSex;
	private TextView back;
	DbOperation dbOperation;
	int id;
	String flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.show_person_birthday_detail);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		save = (ImageView) this.findViewById(R.id.save);
		back = (TextView) this.findViewById(R.id.back);
//		chat = (TextView) this.findViewById(R.id.chat);
		GuestHeadImage = (ImageView) this.findViewById(R.id.GuestHeadImage);
		GuestName = (LineEditText) this.findViewById(R.id.GuestName);
		GuestHobby = (LineEditText) this.findViewById(R.id.GuestHobby);
		GuestSex = (LineEditText) this.findViewById(R.id.GuestSex);
		// GuestOther = (LineEditText)this.findViewById(R.id.GuestOther);
		GuestBirthday = (LineEditText) this.findViewById(R.id.GuestBirthday);

	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		chat.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		dbOperation = new DbOperation(ShowPersonBirthdayDetail.this,
				"birthdayTree.db");
		id = (Integer) intent.getSerializableExtra("ID");
		flag = intent.getStringExtra("flag");// 用来标志应该查哪个表?
		person = dbOperation.queryFromID(flag, id);
		setPerson();
	}

	private void setPerson() {
		// TODO Auto-generated method stub
		GuestName.setText(person.getName());
		GuestBirthday.setText(person.getDate());
		GuestHobby.setText(person.getHobby());
		GuestSex.setText(person.getSex());//这里性别用的界面是textview,应该界面设计一下用raddionbutton

		GuestHeadImage.setImageBitmap(person.getHeadImage());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save:
			person = getPerson();
			dbOperation.update(person, flag);
			ShowToast("保存成功");
			break;
		// 面对面验证最关键的功能
//		case R.id.chat:
//			if (person.getChatId() == null) {
//				showDialog();
//			}else{
//				Intent intent = new Intent(ShowPersonBirthdayDetail.this, ChatActivity.class);
//				startActivity(intent);
//			}
//			break;

		case R.id.back:
			break;
		}
	}

	private Person getPerson() {
		// TODO Auto-generated method stub
		person.setName(GuestName.getText().toString().trim());
		person.setDate(GuestBirthday.getText().toString().trim());
		person.setHobby(GuestHobby.getText().toString().trim());
		Bitmap image = ((BitmapDrawable)GuestHeadImage.getDrawable()).getBitmap();
		person.setHeadImage(image);
		return person;
	}

	private void showDialog() {}

}
