package com.example.downloaddemo.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;
import org.apache.http.HttpStatus;

import com.example.downloaddemo.bean.FileInfo;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.InputFilter.LengthFilter;
import android.util.Log;

public class DownLoadService extends Service {
	public static final String DOWNLOAD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/download/";
	public static final String ACTION_START = "ACTION_START";
	public static final String ACTION_END = "ACTION_END";
	public static final String ACTION_UPDATE = "ACTION_UPDATE";
	public static final int MSG_INIT = 0;
	private DownLoadTask downLoadTask;
	FileInfo fileInfo;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case MSG_INIT:
				FileInfo fileInfo = (FileInfo) msg.obj;
				Log.v(fileInfo.toString(),"------>");
				//启动下载任务
				downLoadTask = new DownLoadTask(DownLoadService.this, fileInfo);
				downLoadTask.download();
				break;

			default:
				break;
			}
    	};
    };
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (ACTION_START.equals(intent.getAction())) {
			fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
			InitThread initThread = new InitThread();
			initThread.start();
			Log.v("start service", "---->");
		} else if (ACTION_END.equals(intent.getAction())) {
			fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
			if(downLoadTask != null){
				downLoadTask.isPause = true;
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * 初始化的子线程,获取文件长度去设置本地文件存储的大小
	 */
	class InitThread extends Thread {

		HttpURLConnection conn = null;
		RandomAccessFile raf = null;
		public void run() {
			try {
				// 连接网络文件
				URL url = new URL(fileInfo.getUrl());
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");
				int length = -1;
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					// 获得文件长度
					length = conn.getContentLength();
				}
				if(length < 0){
					return;
				}
				File dir = new File(DOWNLOAD_PATH);
				if(!dir.exists()){
					dir.mkdir();
				}
				String fileName = fileInfo.getFileName();
				File file = new File(dir, fileName);
				 raf = new RandomAccessFile(file, "rwd");
				// 设置文件长度
				raf.setLength(length);
				fileInfo.setLength(length);
				handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				conn.disconnect();
				try {
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		
		}
	}
}
