package com.example.myview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
	
public class MyAdapter extends BaseAdapter{
		Context mContext;
		LinearLayout linearLayout = null;
		LayoutInflater inflater;
		TextView tex;
		final int VIEW_TYPE = 3;
		final int TYPE_1 = 0;
		final int TYPE_2 = 1;
		final int TYPE_3 = 2;
		List<String> listString;
		public MyAdapter(Context context, List<String> listString) {
			// TODO Auto-generated constructor stub
			mContext = context;
			inflater = LayoutInflater.from(mContext);
			this.listString = listString;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
		
		//每个convert view都会调用此方法，获得当前所需要的view样式
		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			int p = position%4;
			if(p == 0)
				return TYPE_1;
			else if(p < 3 && p > 0)
				return TYPE_3;
			else if(p == 3)
				return TYPE_2;
			else
				return TYPE_1;
		}
		
		@Override
		public int getViewTypeCount() {
			return 3;
		}
		
		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			viewHolder1 holder1 = null;
			viewHolder2 holder2 = null;
			viewHolder3 holder3 = null;
			int type = getItemViewType(position);
			
			//无convertView，需要new出各个控件,这里是性能调优的核心,因为这样不会重新加载视图
			if(convertView == null)
			{ 
				Log.e("convertView = ", " NULL");
				
				//按当前所需的样式，确定new的布局
				switch(type)
				{
				case TYPE_1:
					convertView = inflater.inflate(R.layout.edit_bithday_listview1, parent, false);
					holder1 = new viewHolder1();
					holder1.name = (EditText)convertView.findViewById(R.id.birthday_name);
					holder1.headImage = (ImageView)convertView.findViewById(R.id.head_image);
					Log.e("convertView = ", "NULL TYPE_1");
					holder1.name.setText("周月玥");
					holder1.headImage.setBackgroundResource(R.drawable.head_image);
					convertView.setTag(holder1);//getTag()对应
					break;
				case TYPE_2:
					convertView = inflater.inflate(R.layout.edit_bithday_listview2, parent, false);
					holder2 = new viewHolder2();
					holder2.hobbyEdit = (EditText)convertView.findViewById(R.id.hobby_edit);
					holder2.hobbyText = (TextView)convertView.findViewById(R.id.hobby_txt);
					holder2.icon = (ImageView)convertView.findViewById(R.id.hobby_icon);
					Log.e("convertView = ", "NULL TYPE_2");
					holder2.hobbyText.setText(Integer.toString(position));
					holder2.hobbyEdit.setText("");
					holder2.icon.setBackgroundResource(R.drawable.aihao);
					convertView.setTag(holder2);
					break;
				case TYPE_3:
					convertView = inflater.inflate(R.layout.edit_bithday_listview3, parent, false);
					holder3 = new viewHolder3();
					holder3.birthdayOrSexTxt = (TextView)convertView.findViewById(R.id.bithday_or_sex_txt);
					holder3.birthdayOrSexEdit = (TextView)convertView.findViewById(R.id.bithday_or_sex_edit);
					holder3.icon = (ImageView)convertView.findViewById(R.id.bithday_or_sex_image);
					Log.e("convertView = ", "NULL TYPE_3");
					if(position == 2){
						holder3.birthdayOrSexTxt.setText("性别");
					}else{
						holder3.birthdayOrSexTxt.setText("生日");
					}
					holder3.birthdayOrSexEdit.setText("");
					convertView.setTag(holder3);
					break;
				}
			}else{
				//有convertView，按样式，取得不用的布局
				switch(type){
				case TYPE_1:
					holder1 = (viewHolder1) convertView.getTag();
					Log.e("convertView !!!!!!= ", "NULL TYPE_1");
					break;
				case TYPE_2:
					holder2 = (viewHolder2) convertView.getTag();
					Log.e("convertView !!!!!!= ", "NULL TYPE_2");
					break;
				case TYPE_3:
					holder3 = (viewHolder3) convertView.getTag();
					Log.e("convertView !!!!!!= ", "NULL TYPE_3");
					break;
				}
			}
			
			//设置资源
//			switch(type)
//			{
//			case TYPE_1:
//				holder1.name.setText("周月玥");
//				holder1.headImage.setBackgroundResource(R.drawable.head_image);
//				break;
//			case TYPE_2:	
//				holder2.hobbyText.setText(Integer.toString(position));
//				holder2.hobbyEdit.setText("");
//				holder2.icon.setBackgroundResource(R.drawable.aihao);
//				break;
//			case TYPE_3:
//				if(position == 2){
//					holder3.birthdayOrSexTxt.setText("性别");
//				}else{
//					holder3.birthdayOrSexTxt.setText("生日");
//				}
//				holder3.birthdayOrSexEdit.setText("");
//				break;
//			}
			
			return convertView;
		}
		//各个布局的控件资源
		class viewHolder1{
			ImageView headImage;
			EditText name;
		}
		//爱好
		class viewHolder2{
			ImageView icon;
			TextView hobbyText;
			EditText hobbyEdit;
		}
		//生日和性别
		class viewHolder3{
			ImageView icon;
			TextView birthdayOrSexTxt;
			TextView birthdayOrSexEdit;
			
		}
	}
	