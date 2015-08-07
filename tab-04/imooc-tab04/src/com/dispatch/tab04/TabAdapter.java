package com.dispatch.tab04;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TabAdapter extends FragmentStatePagerAdapter
{
	int count = 0;
	Context context;
	ViewPager viewPager;
	public static String[] TITLES = new String[]
	{ "NOOP", "DEADLINE", "CFQ"};
	private static int flag = 0;
	//	private static List<TabFragment> fragments = new ArrayList<TabFragment>();
	public TabAdapter(Context context,ViewPager viewPager,FragmentManager fm,int flag)
	{
		super(fm);
		this.flag = flag;
		this.viewPager = viewPager; 
		this.context = context;
	}

	@Override
	public Fragment getItem(int position)
	{
		Fragment fragment;
		if(flag == 1){
			fragment = new TabFragment(position);
			SharedPreferencesUtils.putInt(context, "model", 1);
		}else{	
			SharedPreferencesUtils.putInt(context, "model", 0);
			fragment = new TabFragment(position);	
		}
//		if(fragments.size() < TITLES.length){			
//			fragments.add(fragment);
//		}
		return fragment;
	}

	@Override
	public int getCount()
	{
		return TITLES.length;
	}
	
	@Override
	public void startUpdate(ViewGroup container) {
		super.startUpdate(container);
	}
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int pos) {
		return super.instantiateItem(container, pos);		
	}
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}


}
