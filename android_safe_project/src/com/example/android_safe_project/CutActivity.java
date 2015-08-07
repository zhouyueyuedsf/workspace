package com.example.android_safe_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.intService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CutActivity extends Activity {
	private Crop_Canvas canvas;
	private Bitmap backBitmap;
	private Button cancel;
	private Button ensure;
	String storePath;
    Map<String, Object> map=null;
private intService share;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.activity_cut);
		
share=new intService(this);
		Intent intent = getIntent();
		String path = (String) intent.getSerializableExtra("Cuted");
		storePath = (String) intent.getSerializableExtra("Packname");
		backBitmap = BitmapFactory.decodeFile(path);
		canvas = (Crop_Canvas) findViewById(R.id.myCanvas);
		Bitmap bitmap = backBitmap;
		Log.v("bitmap", "----->");
		canvas.setBitmap(bitmap);
		// backBitmap = MuPDFActivity.backBitmap;
		
		cancel = (Button) findViewById(R.id.cutCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CutActivity.this.finish();
			}
		});
		ensure = (Button) findViewById(R.id.cutEnsure);
		ensure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("getSubsetBitmap", "------>");
				saveBitMap(canvas.getSubsetBitmap());
				Log.v("getSubsetBitmap", "------>");
				   map= (Map<String, Object>) share.readboolean();
				
						map.put("Bottom", canvas.getbottom());
						map.put("Top", canvas.gettop());
						map.put("Left",canvas.getleft());
						map.put("Right", canvas.getright());
						Log.v("getright", "------>");
						share.writeboolean(map);
						Log.v("writeboolean", "------>");
			
				Log.v("getSubsetBitmap", "----->");
			}
		});

		// toPDF = (Button)findViewById(R.id.toPDF);
		// toPDF.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ArrayList<String> imageUrllist = new ArrayList<String>();
		// imageUrllist.add("/sdcard/lovereader/pic/" + "testpic" + ".png");
		// String pdfUrl = "/sdcard/lovereader/tmp/Foreverlove.pdf";
		// File tmp = new File("/sdcard/lovereader/tmp");
		// tmp.mkdirs();
		// File file = PdfManager.Pdf(imageUrllist, pdfUrl);
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// });
	}

	private void saveBitMap(Bitmap bitm) {
		try {
			File path = new File(storePath);
			String filepath = storePath + "/cuted" + ".jpeg";
			File file = new File(filepath);
			if (!path.exists()) {

				path.mkdirs();
			}
			if (!file.exists()) {

				file.createNewFile();
			}

			FileOutputStream fos = null;

			fos = new FileOutputStream(file);

			if (null != fos) {
				bitm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				Toast.makeText(this,
						"截屏文件已保存至SDCard/android_safe_project/ScreenImage/下",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.v("异常", "---->");
		}

	}

	private void init() {

	}

}