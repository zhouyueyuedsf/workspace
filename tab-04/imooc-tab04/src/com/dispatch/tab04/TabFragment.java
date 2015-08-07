package com.dispatch.tab04;


import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;

import com.imooc.tab04.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment
{
	private int pos;
	Queue<Long> x = new ArrayBlockingQueue<Long>(60);
	Queue<Double> y = new ArrayBlockingQueue<Double>(60);
	GraphicalView graphicalView;
	TimerTask timerTask;
	ChartUtil chartUtil;
	View view;
	Handler mainHandler;
	private boolean isVisible;
	@SuppressLint("ValidFragment")
	public TabFragment(int pos)
	{
		this.pos = pos;//记录是哪一个fragment
	}
	//点击Home键的时候执行
	@Override
	public void onStop() {
		super.onStop();
		timerTask.run();
	}
	//activity切换的时候执行
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timerTask.cancel();
	}
	//点击返回键退出的时候执行
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timerTask.cancel();
	}
	
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		 if(getUserVisibleHint()) {
			 	//fragment可见时执行加载数据或者进度条等
	        } else {
	        	//不可见时不执行操作
	        }
	}
	//fragment显示的数据
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.frag, container, false);
		LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.gview);
		final RadioButton radio = (RadioButton)getActivity().findViewById(R.id.radio1);
		Button button = (Button)v.findViewById(R.id.show_before_view);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ShowStaticView.class);
				intent.putExtra("flag", radio.isChecked());//判断是自动模式还是手动模式
				intent.putExtra("xyTime", chartUtil.xyTimeSeries);
				startActivity(intent);
			}
		});
		chartUtil = new ChartUtil(getActivity());
		chartUtil.setXYRenderer((double) System.currentTimeMillis(), 1.00, "IO利用率", "时间", "单位%");
		x.add(new Date().getTime());
		y.add((double) LinuxSystemTool.getIOofDisk());
		chartUtil.createDataSet(x, y);
		graphicalView = chartUtil.getGraphicalView();
		mainHandler = new Handler(){
			public void handleMessage(android.os.Message msg) {				
				chartUtil.updateView(x, y);
			};
		};
		timerTask = new TimerTask() {		
			@Override
			public void run() {
				x.add(new Date().getTime());
				y.add((double) LinuxSystemTool.getIOofDisk());
				mainHandler.sendMessage(mainHandler.obtainMessage());
			}
		};
		new Timer().schedule(timerTask, 0,1000);
		linearLayout.addView(graphicalView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		return v;
	}
}
