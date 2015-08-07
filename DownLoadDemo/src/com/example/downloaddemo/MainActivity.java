package com.example.downloaddemo;

import com.example.downloaddemo.bean.FileInfo;
import com.example.downloaddemo.service.DownLoadService;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	private Button btStart, btStop;
	private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //创建对象
        int id = 0;
        String url = "http://dlsw.baidu.com/sw-search-sp/soft/42/16490/QQBrowser_Setup_V8.1.3483.400.1427796244.exe";
        String fileName = "software";
        int length = 0;
        int finished = 0;
        final FileInfo fileInfo = new FileInfo(id, fileName, url, length, finished);
        btStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, DownLoadService.class);
				intent.setAction(DownLoadService.ACTION_START);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
        btStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, DownLoadService.class);
				intent.setAction(DownLoadService.ACTION_END);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
    }


    private void initView() {
		// TODO Auto-generated method stub
		btStart = (Button)this.findViewById(R.id.btStart);
		btStop = (Button)this.findViewById(R.id.btStop);
		progressBar = (ProgressBar)this.findViewById(R.id.progressBar1);
		progressBar.setMax(100);
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownLoadService.ACTION_UPDATE);
		registerReceiver(broadcastReceiver, filter);
	}
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(broadcastReceiver);
    };
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(DownLoadService.ACTION_UPDATE.equals(intent.getAction())){
				int finished = intent.getIntExtra("finished", 0);
				progressBar.setProgress(finished);
			}
		}
	};
}
