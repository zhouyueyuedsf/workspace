package com.example.viewpager1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MyPagerAdapter extends PagerAdapter {
	private List<View> views;
	private List<String> titleList = new ArrayList<String>();//viewpager�ı���
	public MyPagerAdapter(List<View> views){
		this.views = views;
//		titleList.add("���«h");//ϵͳ�����ĵ��ã��磺д��ǰ���Ϊsplashactivity
//		titleList.add("������");
//		titleList.add("������");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	//ʵ����ҳ��,���Ϊ�Զ�����
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(views.get(position),0);

		return views.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(views.get(position));
	}
	
//	
//	@Override
//	public CharSequence getPageTitle(int position) {
//		// TODO Auto-generated method stub
//		return titleList.get(position);
//	}
	
}