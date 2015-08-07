package com.example.android_safe_project;

import java.io.File;




import java.io.FileOutputStream;

import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SolutionActivity1 extends Activity {

	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
private int X,Y;
	private ImageView iv_image;
	private static String struri;
	String SavePath;
	String filepath;
private Button button;
int count=0;
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solution1);
		this.iv_image = (ImageView) this.findViewById(R.id.iv_image);
	
	}

	public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

//	private void crop(Uri uri) {
//		// TODO Auto-generated method stub
//		 Intent intent = new Intent("com.android.camera.action.CROP");
//	        intent.setDataAndType(uri, "image/*");
//	        intent.putExtra("crop", "true");
//	       //自由剪裁
//	        intent.putExtra("aspectX", X);	  
//            intent.putExtra("aspectY", Y);
//            
//         //輸出爲 
//	        intent.putExtra("outputX",true);
//	        intent.putExtra("outputY",true);
////
////      Rect rect=intent.getSourceBounds();
////   if( rect.isEmpty())
////   {
////	   
////  	 Log.v("rect", "-+");
////   }
//	        intent.putExtra("outputFormat", "JPEG");// 图片格式
//	        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//	        intent.putExtra("return-data", true);
//	        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//	        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//	}
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			// 从相册返回的数据
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				struri=getPath(uri, SolutionActivity1.this);
				Bitmap bm = BitmapFactory.decodeFile(struri);
				saveBitMap(bm);
				Bundle data1 = new Bundle();
				data1.putSerializable("Packname", SavePath);
				data1.putSerializable("Cuted", struri);
				data1.putSerializable("filePath", filepath);
				Intent intent = new Intent(SolutionActivity1.this,
						 CutActivity.class);
				intent.putExtras(data1);
				startActivity(intent);
	
				count++;
			}

		}  else if (requestCode == PHOTO_REQUEST_CUT) {
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				
				this.iv_image.setImageBitmap(bitmap);
				count++;
				saveBitMap(bitmap);
			}
			try {
				// 将临时文件删除
				tempFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else if(requestCode==1000&&resultCode==1001)
		{
			String result_value = data.getStringExtra("result");
			 X=Integer.parseInt(result_value);
		}
		else if(requestCode==1000&&resultCode==1002)
		{
			String result_value = data.getStringExtra("result");
			 Y=Integer.parseInt(result_value);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void saveBitMap(Bitmap bitm) {
//		String strcount=Integer.toString(count);
	Intent intent=getIntent();
	String strPackName=(String)intent.getSerializableExtra("packname");
	SavePath = getSDCardPath() +"/android_safe_project"+"/"+strPackName+ "/ScreenImage";
		// TODO Auto-generated method stub
		try{
			File path = new File(SavePath);
		 filepath = SavePath+"/Screen_"+".jpeg";
		    File file = new File(filepath);
		   if (!path.exists()) {
			Log.v("path", "---");
			path.mkdirs();
		}
		if (!file.exists()) {
			Log.v("file", "---");
			file.createNewFile();	
		}
		Log.v("fosl", "---");
		FileOutputStream fos = null;
		Log.v("fosm", "---");
		fos = new FileOutputStream(file);
		Log.v("fosh", "---");
		if (null != fos) {
			bitm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			Toast.makeText(this,
					"截屏文件已保存至SDCard/android_safe_project/ScreenImage/下",
					Toast.LENGTH_LONG).show();
		}
   
		}catch (Exception e) {
		e.printStackTrace();
		Log.v("异常", "---->");
	}
	

	}
	private String getSDCardPath() {
		// TODO Auto-generated method stub
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}

		return sdcardDir.toString();
	}
	/**
	 *将uri转为真实路径 
	 * @param uri
	 * @param context
	 * @return
	 */
	 public static String getPath(Uri uri, Context context) {
	        String[] proj = { MediaStore.Images.Media.DATA };
	        ContentResolver cr = context.getContentResolver();

	        Cursor cursor = cr.query(uri, proj, null, null, null);

	        cursor.moveToFirst();

	        int actual_image_column_index = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        return cursor.getString(actual_image_column_index);

	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solutin, menu);
		return true;
	}

}
