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

	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
private int X,Y;
	private ImageView iv_image;
	private static String struri;
	String SavePath;
	String filepath;
private Button button;
int count=0;
	/* ͷ������ */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solution1);
		this.iv_image = (ImageView) this.findViewById(R.id.iv_image);
	
	}

	public void gallery(View view) {
        // ����ϵͳͼ�⣬ѡ��һ��ͼƬ
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

//	private void crop(Uri uri) {
//		// TODO Auto-generated method stub
//		 Intent intent = new Intent("com.android.camera.action.CROP");
//	        intent.setDataAndType(uri, "image/*");
//	        intent.putExtra("crop", "true");
//	       //���ɼ���
//	        intent.putExtra("aspectX", X);	  
//            intent.putExtra("aspectY", Y);
//            
//         //ݔ���� 
//	        intent.putExtra("outputX",true);
//	        intent.putExtra("outputY",true);
////
////      Rect rect=intent.getSourceBounds();
////   if( rect.isEmpty())
////   {
////	   
////  	 Log.v("rect", "-+");
////   }
//	        intent.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
//	        intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
//	        intent.putExtra("return-data", true);
//	        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
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
			// ����᷵�ص�����
			if (data != null) {
				// �õ�ͼƬ��ȫ·��
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
			// �Ӽ���ͼƬ���ص�����
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				
				this.iv_image.setImageBitmap(bitmap);
				count++;
				saveBitMap(bitmap);
			}
			try {
				// ����ʱ�ļ�ɾ��
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
					"�����ļ��ѱ�����SDCard/android_safe_project/ScreenImage/��",
					Toast.LENGTH_LONG).show();
		}
   
		}catch (Exception e) {
		e.printStackTrace();
		Log.v("�쳣", "---->");
	}
	

	}
	private String getSDCardPath() {
		// TODO Auto-generated method stub
		File sdcardDir = null;
		// �ж�SDCard�Ƿ����
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}

		return sdcardDir.toString();
	}
	/**
	 *��uriתΪ��ʵ·�� 
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
