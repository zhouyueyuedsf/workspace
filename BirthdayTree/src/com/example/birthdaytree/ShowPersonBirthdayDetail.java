package com.example.birthdaytree;

import com.example.birthdaytree.bean.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowPersonBirthdayDetail extends AddPersonBirthday implements OnClickListener{
	Person person;
//	RadioButton man,woman;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);			
			//man = (RadioButton)this.findViewById(R.id.man);
//			//woman =(RadioButton)this.findViewById(R.id.woman);
		person = new Person();
		Intent intent = getIntent();
//		Log.v("ShowPersonBirthdayDetail", "------>");
		person.setName(intent.getStringExtra("name"));
		
		person.setDate((String)intent.getSerializableExtra("date"));
		person.setHobby((String)intent.getSerializableExtra("hobby"));
		person.setSex((String)intent.getSerializableExtra("sex"));
		
	    byte[] data = intent.getByteArrayExtra("headimage");
	    if(data != null){
//	    	GuestHeadImage.setImageResource(R.drawable.ic_launcher);
	    	person.setHeadImage(BitmapFactory.decodeByteArray(data, 0, data.length));
	    	GuestHeadImage.setImageBitmap(person.getHeadImage());
	    }else{
	    	GuestHeadImage.setImageResource(R.drawable.ic_launcher);
	    }
		
//		Log.v("ShowPersonBirthdayDetail", "------>");
			GuestName.setText(person.getName());
			GuestBirthday.setText(person.getDate());
//			GuestHeadImage.setImageBitmap(person.getHeadImage());
			GuestHobby.setText(person.getHobby());
//			if(person.getSex().equals("男")){
//				man.setFocusable(true);
//				woman.setFocusable(false);
//			}else{
//				man.setFocusable(false);
//				woman.setFocusable(true);
//			}
		}
}