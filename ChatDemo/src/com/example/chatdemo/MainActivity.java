package com.example.chatdemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	SocketThread clientThread;
	Handler myHandler;
	EditText etMessage;
	TextView tvSend, tvMessage;
	Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
      myHandler = new Handler(){
    	  public void handleMessage(Message msg) {
    		  tvMessage.append((CharSequence) msg.obj);
    	  };
      };
        clientThread = new SocketThread("10.0.2.2", 8899, new MessageListener() {
				
				@Override
				public void Message(String msg) {
					//子线程向主线程传递消息更新UI
					Message message = Message.obtain();//新建一个Message消息
					message.obj = msg;	
					myHandler.sendMessage(message);
				}
			});
		
        clientThread.start();
      
        tvSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 String message = etMessage.getText().toString();
  			      Log.v("发送消息", message);		   
			      clientThread.sendMsg(message);		      
			}
		});
    }
//    private class MyHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//          // TODO Auto-generated method stub
//          Log.v("处理收到的消息", "  ");
//          tvMessage.append((CharSequence) msg.obj);
//        }
//      }

    private void setUp() {
		// TODO Auto-generated method stub
    	etMessage = (EditText) findViewById(R.id.etMessage);
        tvSend = (TextView) findViewById(R.id.tvSend);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
//        myHandler = new MyHandler();

	}
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
