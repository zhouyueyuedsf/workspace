package com.example.bundletest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class BundleTest extends Activity {
	EditText editText ;
	ImageView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bundle);
		Intent intent = getIntent();
		Person person = (Person) intent.getParcelableExtra("person");
		editText = (EditText)this.findViewById(R.id.editText1);
		view  = (ImageView)this.findViewById(R.id.imageView1);
		editText.setText(person.getStr());
		view.setImageBitmap(person.getBit());
	};
}
