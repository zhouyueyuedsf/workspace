package com.dispatch.tab04;

import java.util.Date;
import java.util.Queue;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.widget.Toast;

public class ChartUtil {
	private GraphicalView graphicalView;
	public  XYMultipleSeriesDataset xyMultipleSeriesDataset;
	private  XYMultipleSeriesRenderer xyMultipleSeriesRenderer;
	public TimeSeries timeSeries, xyTimeSeries;
	public XYSeries xySeries;
	private XYSeriesRenderer xySeriesRenderer;
	private Context context;
	private static int flag = 1;
	/**
	 * 用于一个坐标系多条直线
	 */
	private static XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	private static XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	public ChartUtil() {

	}

	public ChartUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获取图表
	 * 
	 * @return
	 */
	public GraphicalView getGraphicalView() {
		graphicalView = ChartFactory.getTimeChartView(context,
				xyMultipleSeriesDataset, xyMultipleSeriesRenderer, "mm:ss");
		return graphicalView;
	}
	public GraphicalView getXyGraphicalView(){
		graphicalView = ChartFactory.getLineChartView(context, xyMultipleSeriesDataset, xyMultipleSeriesRenderer);
		return graphicalView;
	}
	public void getXyMultipleGraphicalView(){
		Intent multiGraphicalView= ChartFactory.getLineChartIntent(context, dataset, renderer);
		context.startActivity(multiGraphicalView);
		dataset.clear();
		renderer.removeAllRenderers();
	}
	public void createDataSet(Queue<Long> x, Queue<Double> y) {
		timeSeries = new TimeSeries("");
		xyTimeSeries = new TimeSeries("");
		xyMultipleSeriesDataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < x.size(); i++) {
			timeSeries.add(new Date(x.poll()), y.poll());
		}
		xyMultipleSeriesDataset.addSeries(timeSeries);
		flag = 1;
	}
	public void setXyMsd(XYSeries xySeries){		
		dataset.addSeries(xySeries);
	}
	public void updateView(Queue<Long> x, Queue<Double> y) {
		xyMultipleSeriesDataset.removeSeries(timeSeries);
		xyMultipleSeriesDataset.removeSeries(xyTimeSeries);		
		for (int i = 0; i < x.size(); i++) {
			if(y.peek() != null){				
				xyTimeSeries.add(i, i, y.peek());// 查看,并不弹出
				timeSeries.add(new Date(x.poll()), y.poll());
				
			}			
		}
		xyMultipleSeriesDataset.addSeries(timeSeries);
		// if(MainActivity.updateFlag == 0){
		graphicalView.invalidate();
		// }
	}
	/**
	 * 得到之前的图
	 * @param beforeSecond
	 */
	public void getBeforeView(int beforeSecond) {
		xySeries = new XYSeries("");
		xyMultipleSeriesDataset = new XYMultipleSeriesDataset();
		if (beforeSecond > xyTimeSeries.getItemCount()) {
			Toast.makeText(context,
					"对不起,现在只记录了" + xyTimeSeries.getItemCount() + "s",
					Toast.LENGTH_LONG).show();
			for (int i = 0; i < xyTimeSeries.getItemCount() ; i++) {
				xySeries.add(i, i, xyTimeSeries.getY(i));	//FIFO			
			}
		} else {
			for (int i = 0; i < beforeSecond ; i++) {
				xySeries.add(i, i, xyTimeSeries.getY(i));								
			}
		}
		xyMultipleSeriesDataset.addSeries(xySeries);
	}
	public void setXYRenderer(int maxX, Double maxY, String chartTitle,
			String xTitle, String yTitle) {
		xyMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		if (chartTitle != null) {
			xyMultipleSeriesRenderer.setChartTitle(chartTitle);
		}
//		xyMultipleSeriesRenderer.setXAxisMax(maxX);
		xyMultipleSeriesRenderer.setYAxisMax(maxY);
		xyMultipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
		xyMultipleSeriesRenderer.setXTitle(xTitle);
		xyMultipleSeriesRenderer.setYTitle(yTitle);
		xyMultipleSeriesRenderer.setAxisTitleTextSize(26f);
		xyMultipleSeriesRenderer.setChartTitleTextSize(36f);
		xyMultipleSeriesRenderer.setLabelsTextSize(20f);
		int[] margins = { 100, 70, 0, 0 };
		xyMultipleSeriesRenderer.setMargins(margins);
		xySeriesRenderer = new XYSeriesRenderer();
		xySeriesRenderer.setColor(Color.RED);
		xySeriesRenderer.setPointStyle(PointStyle.CIRCLE);
		xyMultipleSeriesRenderer.addSeriesRenderer(xySeriesRenderer);
	}
	public void setXYRenderer(Double maxX, Double maxY, String chartTitle,
			String xTitle, String yTitle) {
		xyMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		if (chartTitle != null) {
			xyMultipleSeriesRenderer.setChartTitle(chartTitle);//设置整个图标名称
		}
		xyMultipleSeriesRenderer.setYAxisMax(maxY);//设置最大的纵坐标值
		xyMultipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);//设置Y轴标签居Y轴的方向
		xyMultipleSeriesRenderer.setXTitle(xTitle);//设置X标签
		xyMultipleSeriesRenderer.setYTitle(yTitle);//设置Y标签
		xyMultipleSeriesRenderer.setAxisTitleTextSize(26f);
		xyMultipleSeriesRenderer.setChartTitleTextSize(36f);
		xyMultipleSeriesRenderer.setLabelsTextSize(20f);
		int[] margins = { 100, 70, 0, 0 };
		xyMultipleSeriesRenderer.setMargins(margins);//设置长,左,下,右
		setRenderer(xyMultipleSeriesRenderer,Color.RED,PointStyle.CIRCLE);
	}
	/**
	 * 添加一条直线的渲染器
	 * @param color
	 * @param pointStyle
	 */
	public void addRenderer(XYMultipleSeriesRenderer renderer,int color, PointStyle pointStyle){
		setRenderer(renderer,color, pointStyle);
	}
	public void setRenderer(XYMultipleSeriesRenderer renderer,int color, PointStyle pointStyle){	
			xySeriesRenderer = new XYSeriesRenderer();
			xySeriesRenderer.setColor(color);//设置线的颜色
			xySeriesRenderer.setPointStyle(pointStyle);//设置折点的形状
			renderer.addSeriesRenderer(xySeriesRenderer);	
			this.renderer = renderer;
	}
	public XYMultipleSeriesDataset getXyMultipleSeriesDataset() {
		
		return xyMultipleSeriesDataset;
	}

	public void setXyMultipleSeriesDataset(
			XYMultipleSeriesDataset xyMultipleSeriesDataset) {
		this.xyMultipleSeriesDataset = xyMultipleSeriesDataset;
	}

	public XYMultipleSeriesRenderer getXyMultipleSeriesRenderer() {
		return xyMultipleSeriesRenderer;
	}

	public void setXyMultipleSeriesRenderer(
			XYMultipleSeriesRenderer xyMultipleSeriesRenderer) {
		this.xyMultipleSeriesRenderer = xyMultipleSeriesRenderer;
	}

	public TimeSeries getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(TimeSeries timeSeries) {
		this.timeSeries = timeSeries;
	}

	public TimeSeries getXyTimeSeries() {
		return xyTimeSeries;
	}

	public void setXyTimeSeries(TimeSeries xyTimeSeries) {
		this.xyTimeSeries = xyTimeSeries;
	}

	public XYSeries getXySeries() {
		return xySeries;
	}

	public void setXySeries(XYSeries xySeries) {
		this.xySeries = xySeries;
	}

	public XYSeriesRenderer getXySeriesRenderer() {
		return xySeriesRenderer;
	}

	public void setXySeriesRenderer(XYSeriesRenderer xySeriesRenderer) {
		this.xySeriesRenderer = xySeriesRenderer;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setGraphicalView(GraphicalView graphicalView) {
		this.graphicalView = graphicalView;
	}

}
