package com.example.myview;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyView extends RelativeLayout{

	ListView listView;
	MyAdapter myAdapter;
	List<String> listString;
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}
	//默认加载该类
	public MyView(Context context, AttributeSet attrs) {
				super(context, attrs);
				
	}

	public MyView(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.myview, this, true); 
		listView = (ListView)view.findViewById(R.id.listview);
		listString = new ArrayList<String>();
		for(int i = 0 ; i < 100 ; i++)
		{
			listString.add(Integer.toString(i));
		}
		myAdapter = new MyAdapter(context,listString);
		listView.setAdapter(myAdapter);
		//		int color = ta.getColor(R.styleable., defValue)
	}

	

}
