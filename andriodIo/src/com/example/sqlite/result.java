package com.example.sqlite;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
public class result extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		
				setContentView(R.layout.result);
				ListView listView = (ListView) findViewById(R.id.result);
				Intent intent = getIntent();
				// ��ȡ��intent��Я��������
				Bundle data = intent.getExtras();
				// ��Bundle���ݰ���ȡ������
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = (List<Map<String, String>>)
					data.getSerializable("data");
				// ��List��װ��SimpleAdapter
				SimpleAdapter adapter = new SimpleAdapter(result.this
					, list,
					R.layout.line, new String[] { "word", "detail" }
					, new int[] {R.id.word, R.id.detail });
				// ���ListView
				listView.setAdapter(adapter);
		}
}