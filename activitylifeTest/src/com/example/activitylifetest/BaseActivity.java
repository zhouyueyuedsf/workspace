package com.example.activitylifetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract  class BaseActivity extends Activity {
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	Log.v("this is first", "--->");//���������
    	showView();//��Ϊ���������MainActivity����Ĭ�ϴ���MainActivityʵ��
    	FindView();
    	setupView();
    }
      
      public abstract void showView();
      public abstract void FindView();
      public abstract void setupView();
      
}
