package com.example.birthdaytree;

import java.io.FileNotFoundException;

import com.example.birthdaytree.base.DbOperation;
import com.example.birthdaytree.bean.Person;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class AddPersonBirthday extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	public ImageView GuestHeadImage;
	public LineEditText GuestName,GuestHobby,GuestOther,GuestBirthday;
	public RadioGroup GuestSex;
	public TextView back,save;
	public Bitmap bmp;
	public DbOperation dbOperation;
	Person person;
	private String sSex = "男";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
	
		setContentView(R.layout.addpersonbirthday);

	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		GuestHeadImage = (ImageView)this.findViewById(R.id.GuestHeadImage);
		GuestName = (LineEditText)this.findViewById(R.id.GuestName);
		GuestHobby = (LineEditText)this.findViewById(R.id.GuestHobby);
		GuestOther = (LineEditText)this.findViewById(R.id.GuestOther);
		GuestBirthday = (LineEditText)this.findViewById(R.id.GuestBirthday);
		GuestSex = (RadioGroup)this.findViewById(R.id.GuestSex);
		back = (TextView)this.findViewById(R.id.back);
		save = (TextView)this.findViewById(R.id.save);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		GuestSex.setOnCheckedChangeListener(this);
		GuestHeadImage.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		 person = new Person();
			Log.v("DbOperation", "----->");
		dbOperation = new DbOperation(AddPersonBirthday.this, "birthdayTree.db");
		Log.v("DbOperation", "----->");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			Back(AddPersonBirthday.this,MainActivity.class);
			break;

		case R.id.save:
			if(TextUtils.isEmpty(GuestName.getText())||TextUtils.isEmpty(GuestBirthday.getText())){
				ShowToast("你的生日或名字没填写不能保存");
			}else{
				Intent intent = getIntent();
				String flag = (String) intent.getSerializableExtra("class");
				if(flag.equals("friends")){
					person.setName(GuestName.getText().toString().trim());
					person.setDate(GuestBirthday.getText().toString().trim());
					person.setSex(sSex);
					if(!TextUtils.isEmpty(GuestHobby.getText())){
						person.setHobby(GuestHobby.getText().toString().trim());
					}
					if(GuestHeadImage!=null){
						Bitmap image = ((BitmapDrawable)GuestHeadImage.getDrawable()).getBitmap();
						person.setHeadImage(image);
					}
					
					dbOperation.insertData(person, "friends");
					ShowToast("添加生日成功");
					
				}
			}
			break;
			
		case R.id.GuestHeadImage:
			uploadImage();
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkid) {
		// TODO Auto-generated method stub
		sSex = checkid == R.id.man ? "男" : "女";
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
          Uri  uri = data.getData();//得到选择的uri
            ContentResolver cr = this.getContentResolver();
            try {
            	
            	GuestHeadImage.setImageURI(uri);
           
            	//回收bitmap
                if (bmp != null) {
                    bmp.recycle();
                    
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
        }
    }
	public Boolean uploadImage(){
		CharSequence[] items = { "相册", "相机" };
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
 
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 1) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                           
                        } else {
                   
                            Intent intent = new Intent(
                                    Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, 2);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
 
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                       
                    }
                }).create();
        dialog.show();
        
        return true;
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
}