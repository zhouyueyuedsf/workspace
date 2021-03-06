package com.example.viewpager1;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class MainActivity extends Activity {
	private View view,view2,view3;
	private List<View> views;
	private ViewPager viewPager;
	private List<ImageView> points = new ArrayList<ImageView>();
	private int currPos = 0;
//	private PagerTabStrip pagerTabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        viewPager = (ViewPager)this.findViewById(R.id.viewpager);
//        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
//        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold));  
//        pagerTabStrip.setDrawFullUnderline(false); 
//        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure)); 
//        pagerTabStrip.setTextSpacing(50); 
        points.add((ImageView)this.findViewById(R.id.point1)) ;
        points.add( (ImageView)this.findViewById(R.id.point2));
        points.add( (ImageView)this.findViewById(R.id.point3));
        points.get(0).setBackgroundResource(R.drawable.abc_btn_radio_to_on_mtrl_000);
        points.get(1).setBackgroundResource(R.drawable.abc_btn_switch_to_on_mtrl_00012);
        points.get(2).setBackgroundResource(R.drawable.abc_btn_switch_to_on_mtrl_00012);
        LayoutInflater inflater = getLayoutInflater().from(this);
        view = inflater.inflate(R.layout.splashactivity1, null);
        view2 = inflater.inflate(R.layout.splashactivity2, null);
        view3 = inflater.inflate(R.layout.splashactivity3, null);
        views = new ArrayList<View>();// 将要分页显示的View装入数组中   
        views.add(view);  
        views.add(view2);  
        views.add(view3);  
        MyPagerAdapter adapter = new MyPagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currPos);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				//当滑动停止时
				if(arg0 == 2){
				currPos	= viewPager.getCurrentItem();
				setbg(currPos);
				}
			}

			private void setbg(int currPos) {
				// TODO Auto-generated method stub
			for(int i = 0;i<3;i++){
				if(i==currPos){
					points.get(i).setBackgroundResource(R.drawable.abc_btn_radio_to_on_mtrl_000);
				}else{
					points.get(i).setBackgroundResource(R.drawable.abc_btn_switch_to_on_mtrl_00012);
				}
			}
				
			}
			
		});
    }



}
