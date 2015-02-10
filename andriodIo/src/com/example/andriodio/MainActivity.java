//package com.example.andriodio;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//
//
//public class MainActivity extends ActionBarActivity {
//  private Button button,button2;
//  private EditText editText,editText2;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        button=(Button)this.findViewById(R.id.button1);//write
//        button2=(Button)this.findViewById(R.id.button2);//read
//        editText=(EditText)this.findViewById(R.id.editText1);
//        editText2=(EditText)this.findViewById(R.id.editText2);
//        button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				write(editText.getText().toString());
//				editText.setText("");
//			}
//
//			private void write(String content) {
//				// TODO Auto-generated method stub
//				try {
//					FileOutputStream fileOutputStream=openFileOutput("fileIo.txt",MODE_APPEND);
//					PrintStream printStream=new PrintStream(fileOutputStream);
//					printStream.println(content);
//					printStream.close();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		});
//        button2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				editText2.setText(read("fileIo.txt"));
//			}
//
//			private String read(String string) {
//				// TODO Auto-generated method stub
//				try {
//					FileInputStream fileInputStream=openFileInput(string);
//					byte[] buff = new byte[1024];//一次读取1024个字节
//					StringBuilder sb=new StringBuilder("");
//					int hasRead=0;
//					while((hasRead=fileInputStream.read(buff))>0){
//						sb.append(new String(buff,0,hasRead));
//					}
//					fileInputStream.close();
//					return sb.toString();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				return null;
//			}
//		});
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
