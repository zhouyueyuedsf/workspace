package com.example.sqlite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dict extends Activity {
	private EditText editText, editText2, editText3;
	private Button button, button2;
	private MyDataBaseHelper baseHelper;
	int version = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dic);
		editText = (EditText) this.findViewById(R.id.wordedit);
		editText2 = (EditText) this.findViewById(R.id.detailedit);
		editText3 = (EditText) this.findViewById(R.id.searchedit);
		button = (Button) this.findViewById(R.id.insert);
		button2 = (Button) this.findViewById(R.id.search);
		// 表名为dict,数据库叫做myDict.db3
		baseHelper = new MyDataBaseHelper(this, "myDict.db3", null, version);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String word = editText.getText().toString();
				String detail = editText2.getText().toString();
				if (!word.equals(null)) {
					// 插入数据库
					String insertsql = "insert into dict  values(null,?,?)";
					baseHelper.getReadableDatabase().execSQL(insertsql,new String[]{word,detail});// 执行语句
					Toast.makeText(Dict.this, "添加单词成功", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(Dict.this, "word为空，添加单词失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		// 查询单词
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = editText3.getText().toString();
				String querysql = "select * from dict where word like ? or detail like ?";
				Cursor cursor = baseHelper.getReadableDatabase().rawQuery(
						querysql, new String[] { "%"+key+"%", "%"+key+"%" });
				if(cursor.getCount()!=0)
				{
					Bundle data =new Bundle();
					data.putSerializable("data", converCursorToList(cursor));
					Intent intent=new Intent(Dict.this,result.class);
					intent.putExtras(data);
					startActivity(intent);
				}
				else{
					Toast.makeText(Dict.this, "没有此单词",
							Toast.LENGTH_LONG).show();
				}
			}

			private ArrayList<Map<String,String>> converCursorToList(Cursor cursor) {
				// TODO Auto-generated method stub
				ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
				while(cursor.moveToNext()){
					Map<String,String> map =new HashMap<String, String>();
					map.put("word",cursor.getString(1));
					map.put("detail",cursor.getString(2));
					result.add(map);
				}
				
				return result;
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(baseHelper!=null){
			baseHelper.close();
		}
	}
}
