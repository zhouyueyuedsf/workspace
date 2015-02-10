package com.example.activitylifetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract  class BaseActivity extends Activity {
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	Log.v("this is first", "--->");//抽象类调用
    	showView();//因为程序入口是MainActivity所以默认创造MainActivity实例
    	FindView();
    	setupView();
    }
      
      public abstract void showView();
      public abstract void FindView();
      public abstract void setupView();
      
}
