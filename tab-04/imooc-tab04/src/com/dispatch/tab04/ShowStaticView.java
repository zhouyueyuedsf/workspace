package com.dispatch.tab04;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.imooc.tab04.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class ShowStaticView extends Activity implements OnClickListener {
	private LinearLayout linearLayout;
	private Button save, compare, clear;
	ChartUtil chartUtil;
	Boolean flag;
	GraphicalView graphicalView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_static_view);
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		save.setOnClickListener(this);
		compare.setOnClickListener(this);
		clear.setOnClickListener(this);
	}

	private void initData() {
		chartUtil = new ChartUtil(ShowStaticView.this);
		TimeSeries xyTimeSeries = (TimeSeries) getIntent()
				.getSerializableExtra("xyTime");
		flag = getIntent().getBooleanExtra("flag", true);
		chartUtil.setXyTimeSeries(xyTimeSeries);
		chartUtil.setXYRenderer(60, 100d, "IO性能", "时间s", "IO利用率%");
		chartUtil.getBeforeView(60);
		graphicalView = chartUtil.getXyGraphicalView();
		linearLayout.addView(graphicalView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	private void initView() {
		linearLayout = (LinearLayout) this.findViewById(R.id.view_layout);
		save = (Button) this.findViewById(R.id.save_db);
		compare = (Button) this.findViewById(R.id.compare);
		clear = (Button) this.findViewById(R.id.clear);
	}

	public void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ShowStaticView.this).setTitle("在比较后请手动清除数据");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				compare();
			}
		});
		builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				compare();
			}
		});
		builder.create().show();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear:
			SharedPreferencesUtils.clearData(ShowStaticView.this, "manu");
			SharedPreferencesUtils.clearData(ShowStaticView.this, "auto");
			Toast.makeText(ShowStaticView.this, "数据已删除", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.compare:
			showDialog();
			break;
		case R.id.save_db:
			//想要保存数据的话,得把之前的数据删除
			Map<Integer, Float> map = xySeriesToMap(chartUtil.getXySeries());
			if (flag) {
				SharedPreferencesUtils.clearData(ShowStaticView.this, "manu");
				SharedPreferencesUtils.putMapObject(this, map, "manu");
			} else {
				SharedPreferencesUtils.clearData(ShowStaticView.this, "auto");
				SharedPreferencesUtils.putMapObject(this, map, "auto");
			}
			Toast.makeText(ShowStaticView.this, "数据已写入", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	private void compare() {
		float manu = SharedPreferencesUtils.getFloat(ShowStaticView.this, 0+"",
				"manu");
		float auto = SharedPreferencesUtils.getFloat(ShowStaticView.this, 0+"",
				"auto");
		if (manu == -1f || auto == -1f) {
			Toast.makeText(ShowStaticView.this, "无数据,请收集", Toast.LENGTH_SHORT)
					.show();
			if (manu == -1) {
				Toast.makeText(ShowStaticView.this, "手动模式下无数据,请收集",
						Toast.LENGTH_SHORT).show();
			}
			if (auto == -1) {
				Toast.makeText(ShowStaticView.this, "自动模式下无数据,请收集",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			XYSeries xySeriesManu = getXySeriesFromSp("manu");
			xySeriesManu.setTitle("手动");//设置
			XYSeries xySeriesAuto = getXySeriesFromSp("auto");
			xySeriesAuto.setTitle("自动");
			chartUtil.setXyMsd(xySeriesManu);//对应RED
			chartUtil.setXyMsd(xySeriesAuto);//对应BLUE
			XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
			renderer.setChartTitle("性能比较");
			renderer.setYAxisMax(100);
			renderer.setYLabelsAlign(Align.RIGHT);
			renderer.setXTitle("时间");
			renderer.setYTitle("IO利用率");
			renderer.setAxisTitleTextSize(26f);
			renderer.setChartTitleTextSize(36f);
			renderer.setLabelsTextSize(20f);
			int[] margins = { 100, 70, 0, 0 };
			renderer.setMargins(margins);
			//画图
			chartUtil.addRenderer(renderer,Color.RED, PointStyle.CIRCLE);
			chartUtil.addRenderer(renderer,Color.BLUE, PointStyle.TRIANGLE);
			chartUtil.getXyMultipleGraphicalView();
			renderer.removeAllRenderers();
			xySeriesAuto.clear();
			xySeriesManu.clear();
		}
	}
/**
 * 
 * @param model:自动模式,手动模式
 * @return xyseries
 */
	private XYSeries getXySeriesFromSp(String model) {
		XYSeries xySeries = new XYSeries("");
		float f;
		for (int i = 0; i < 60; i++) {
			if ((f = SharedPreferencesUtils.getFloat(ShowStaticView.this, i
					+ "", model)) != -1) {
				xySeries.add(i, f);
			} else {
				return xySeries;
			}
			
		}
		return xySeries;
	}
	/**
	 * xySeries类型转换为map
	 * @param xySeries
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	private Map<Integer, Float> xySeriesToMap(XYSeries xySeries) {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		int j = xySeries.getItemCount();
		for (int i = 0; i < j; i++) {
			map.put(i, (float) xySeries.getY(i));
		}
		return map;
	}

}
