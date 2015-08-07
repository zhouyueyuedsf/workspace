package com.dispatch.tab04;

import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import org.achartengine.GraphicalView;

import com.imooc.tab04.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TabFragmentAuto extends Fragment
{
	private int pos;
	Queue<Long> x = new ArrayBlockingQueue<Long>(60);
	Queue<Double> y = new ArrayBlockingQueue<Double>(60);
	GraphicalView graphicalView;
	ChartUtil chartUtil;
	View view;
	Handler mainHandler;

	@SuppressLint("ValidFragment")
	public TabFragmentAuto(int pos)
	{
		this.pos = pos;//记录是那一个fragment
	}
	//fragment显示的数据
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag, container, false);
		chartUtil = new ChartUtil(getActivity());
		chartUtil.setXYRenderer((double) System.currentTimeMillis(), 1.00, "IO利用率", "时间", "单位%");
		x.add(new Date().getTime());
		y.add((double) LinuxSystemTool.getIOofDisk());
		chartUtil.createDataSet(x, y);
		graphicalView = chartUtil.getGraphicalView();
		mainHandler = new Handler(){
			public void handleMessage(android.os.Message msg) {		
				x.add(new Date().getTime());
				y.add((double) LinuxSystemTool.getIOofDisk());
				chartUtil.updateView(x, y);
			};
		};
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				//用得子线程更新UI 
				mainHandler.sendMessage(mainHandler.obtainMessage());
			}
		}, 0,1000);
		view = graphicalView;
		return graphicalView;
	}
}
