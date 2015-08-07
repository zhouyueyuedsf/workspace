package com.example.quadrantunlock;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button quaOne, quaTwo, quaThree, quaFour;
	private Button sure;
	private EditText password;
	private int[] pwdC = {0,0,0,0,0,0,0,0,0,0};
	private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
    }
    

    private void initView() {
		// TODO Auto-generated method stub
		quaOne = (Button)this.findViewById(R.id.qua_one);
		quaTwo = (Button)this.findViewById(R.id.qua_two);
		quaThree = (Button)this.findViewById(R.id.qua_three);
		quaFour = (Button)this.findViewById(R.id.qua_four);
		sure = (Button)this.findViewById(R.id.sure);
		password = (EditText)this.findViewById(R.id.password);
		sure.setOnClickListener(this);
		quaOne.setOnClickListener(this);
		quaTwo.setOnClickListener(this);
		quaThree.setOnClickListener(this);
		quaFour.setOnClickListener(this);
		password.setOnClickListener(this);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
			case R.id.qua_one:
				if(count < 9){
					count++;
					pwdC[count] = 1;
					password.append(String.valueOf(pwdC[count]));
				}else
				Toast.makeText(MainActivity.this, "最大点击次数为10次哦", Toast.LENGTH_LONG).show();
				break;
	
			case R.id.qua_two:
				if(count < 9){
					count++;
					pwdC[count] = 2;
					password.append(String.valueOf(pwdC[count]));
				}else
				Toast.makeText(MainActivity.this, "最大点击次数为10次哦", Toast.LENGTH_LONG).show();
				break;
				
			case R.id.qua_three:
				if(count < 9){
					count++;
					pwdC[count] = 3;
					password.append(String.valueOf(pwdC[count]));
				}else
				Toast.makeText(MainActivity.this, "最大点击次数为10次哦", Toast.LENGTH_LONG).show();
				break;
			
			case R.id.qua_four:
				if(count < 9){
					count++;
					pwdC[count] = 4;
					password.append(String.valueOf(pwdC[count]));
				}else
				Toast.makeText(MainActivity.this, "最大点击次数为10次哦", Toast.LENGTH_LONG).show();
				break;
			
			case R.id.sure:
				String pwd = "";
				for(int i = 0; i < 10; i++){
					if(pwdC[i] != 0){
						pwd = pwd + String.valueOf(pwdC[i]);
					}
				}	
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				SharedPreferencesUtils.putString(MainActivity.this, "password", Encrypt.MD5(pwd));
				Intent intent = new Intent(MainActivity.this,  EncryptActivity.class);
				startActivity(intent);
				break;
		}
	}

	
    
}
