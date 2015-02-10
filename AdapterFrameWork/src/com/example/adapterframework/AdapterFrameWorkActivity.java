package com.example.adapterframework;

import com.bmob.lostfound.adapter.BaseAdapterHelper;
import com.bmob.lostfound.adapter.QuickAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class AdapterFrameWorkActivity extends ActionBarActivity {
	private QuickAdapter<MixTure> adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adapter_frame_work);
		listView = (ListView) this.findViewById(R.id.listview);
		adapter = new QuickAdapter<MixTure>(this, R.layout.adapterframework) {

			@Override
			protected void convert(BaseAdapterHelper helper, MixTure item) {
				// TODO Auto-generated method stub
				helper.setBackgroundRes(R.id.userLogo, R.drawable.ic_launcher)
						.setText(R.id.userName, "zhouyueyue")
						.setText(R.id.context, "���е㻳����")
						.setText(R.id.zhuanfa, "ת��")
						.setText(R.id.pinglun, "����").setText(R.id.zan, "��");
			}
		};
		adapter.add(null);
		listView.setAdapter(adapter);
	}
}
