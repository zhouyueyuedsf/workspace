package com.example.clienttest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DatagramSocket s = null;
	private String ip = "192.168.191.4";
	private int port = 12345;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textView1;
		TextView textView2;
		TextView textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
		final TextView tv1;
		TextView tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
		Button button;
		String curDate;
		String info;
		textView1 = (TextView) this.findViewById(R.id.textView1);
		textView2 = (TextView) this.findViewById(R.id.textView2);
		textView3 = (TextView) this.findViewById(R.id.textView3);
		textView4 = (TextView) this.findViewById(R.id.textView4);
		textView5 = (TextView) this.findViewById(R.id.textView5);
		textView6 = (TextView) this.findViewById(R.id.textView6);
		textView7 = (TextView) this.findViewById(R.id.textView7);
		textView8 = (TextView) this.findViewById(R.id.textView8);
		textView9 = (TextView) this.findViewById(R.id.textView9);
		textView10 = (TextView) this.findViewById(R.id.textView10);
		tv1 = (TextView) this.findViewById(R.id.tv1);
		tv2 = (TextView) this.findViewById(R.id.tv2);
		tv3 = (TextView) this.findViewById(R.id.tv3);
		tv4 = (TextView) this.findViewById(R.id.tv4);
		tv5 = (TextView) this.findViewById(R.id.tv5);
		tv6 = (TextView) this.findViewById(R.id.tv6);
		tv7 = (TextView) this.findViewById(R.id.tv7);
		tv8 = (TextView) this.findViewById(R.id.tv8);
		tv9 = (TextView) this.findViewById(R.id.tv9);
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				tv1.setText((CharSequence) msg.obj);
			}
		};
		new Thread() {
			public void run() {
				try {
					/* tcp */
					Socket socket = new Socket(ip, 12345);
					DataOutputStream dataOutputStream = new DataOutputStream(
							socket.getOutputStream());
					
					DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
					String str = dataInputStream.readUTF();
					
					Message msg = Message.obtain();
					msg.obj = str;
					handler.sendMessage(msg);
					
//					dataOutputStream.writeUTF("hello" + "\n");
//					dataOutputStream.flush();
					
					dataInputStream.close();
					/* UDP */
					// DatagramSocket ds = new DatagramSocket();
					// InetAddress ia = InetAddress.getByName("192.168.191.4");
					// int port = 12345;
					// String message = "hello";
					// byte[] messageBytes = message.getBytes();
					// DatagramPacket dp = new DatagramPacket(messageBytes,
					// message.length(), ia, port);
					// ds.send(dp);
					/* udp2 */
					// s = new DatagramSocket();
					// String message = "hello";
					// byte[] messageByte = message.getBytes();
					// DatagramPacket dp = new DatagramPacket(messageByte,
					// message.length(), InetAddress.getByName(ip), port);
					// s.send(dp);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
