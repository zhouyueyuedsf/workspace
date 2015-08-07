//UI线程
package com.example.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	public MyThread myThread;
	private TextView textView;
	private Button button,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)this.findViewById(R.id.textView1);
        button = (Button)this.findViewById(R.id.button1);
        button2 = (Button)this.findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        Handler handler = new Handler(){//定义了一个主线程的handler
        	public void handleMessage(Message msg) {
        		textView.setText((CharSequence) msg.obj);//通过子线程发送到的信息更新TextView
        	};
        };
        myThread = new MyThread(handler);
        myThread.start();
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button1:
			Message msg1 = Message.obtain();
	        msg1.obj = "hello1" ;
	        msg1.what = 0;
	        myThread.handler.sendMessage(msg1);//将主线程里面的消息送到子线程里面去
			break;

		case R.id.button2:
			 Message msg2 = Message.obtain();
		        msg2.obj = "hello2";
		        msg2.what = 1;
		        myThread.handler.sendMessage(msg2);
			break;
		}
	}
    
}
