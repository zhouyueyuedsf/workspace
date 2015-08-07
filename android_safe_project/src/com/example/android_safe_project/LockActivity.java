package com.example.android_safe_project;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guoshisp.apdd.db.dao.AppLockDao;
import com.guoshisp.apdd.domain.AppInfo;
import com.guoshisp.apdd.engine.AppInfoProvider;

public class LockActivity extends Activity {
	private ListView lv_applock;
	private LinearLayout ll_loading;
	private AppInfoProvider provider;
	private List<AppInfo> appinfos;
	private AppLockDao dao;
	private List<String> lockedPacknames;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ll_loading.setVisibility(View.INVISIBLE);
			lv_applock.setAdapter(new AppLockAdapter());
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
		provider = new AppInfoProvider(this);
		lv_applock = (ListView) findViewById(R.id.lv_applock);
		ll_loading = (LinearLayout) findViewById(R.id.ll_applock_loading);
		dao = new AppLockDao(this);
		lockedPacknames = dao.findAll();
		ll_loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				appinfos = provider.getInstalledApps();
				handler.sendEmptyMessage(0);
			};
		}.start();
		lv_applock.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AppInfo appinfo = (AppInfo) lv_applock
						.getItemAtPosition(position);
				String packname = appinfo.getPackname();
				Log.v(packname, "--->");
				ImageView iv = (ImageView) view
						.findViewById(R.id.iv_applock_status);
				TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0.2f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0);
				ta.setDuration(200);
				Log.v("?", "--->");
				if (lockedPacknames.contains(packname)) {
					Log.v("?1", "--->");
					dao.delete(packname);

					iv.setImageResource(R.drawable.unlock);
					lockedPacknames.remove(packname);
				} else {

					dao.add(packname);

					iv.setImageResource(R.drawable.lock);

					lockedPacknames.add(packname);

				}
				view.startAnimation(ta);
				if (lockedPacknames.contains(appinfo.getPackname())) {
					String str = appinfo.getPackname();
					Bundle data = new Bundle();
					data.putSerializable("packname", str);
					Intent intent = new Intent(LockActivity.this,
							SolutionActivity1.class);

					intent.putExtras(data);
					startActivity(intent);

				}
			}

		});
	}

	// 自定义适配器对象
	private class AppLockAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return appinfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO 自动生成的方法存根
			return appinfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO 自动生成的方法存根
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.app_lock_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_applock_icon);
				holder.iv_status = (ImageView) view
						.findViewById(R.id.iv_applock_status);
				holder.iv_name = (TextView) view
						.findViewById(R.id.tv_applock_appname);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			AppInfo appInfo = appinfos.get(position);
			holder.iv_icon.setImageDrawable(appInfo.getAppicon());
			holder.iv_name.setText(appInfo.getAppname());
			if (lockedPacknames.contains(appInfo.getPackname())) {
				holder.iv_status.setImageResource(R.drawable.lock);
			} else {
				holder.iv_status.setImageResource(R.drawable.unlock);
			}
			return view;
		}
	}

	public static class ViewHolder {
		ImageView iv_icon;
		ImageView iv_status;
		TextView iv_name;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
