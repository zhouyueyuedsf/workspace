package com.example.birthdaytree.util;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.provider.Browser;
import android.util.Log;
import android.widget.ImageView;

public class BitmapUtil{

	static Bitmap compress(Bitmap image){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 85, baos);
		float zoom = (float) Math.sqrt(getSize(image) * 1024 /(float) baos.toByteArray().length);
		Matrix matrix = new Matrix();
		matrix.setScale(zoom, zoom);
		Bitmap result = Bitmap.createBitmap(image,0,0,image.getWidth(),image.getHeight(),matrix,true);
		baos.reset();
		result.compress(Bitmap.CompressFormat.JPEG, 85, baos);
		while(baos.toByteArray().length > getSize(image) * 1024){
			System.out.println(baos.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            baos.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, baos);
		}

		return result;
	}
	public static ContentValues bmpToBlob(Bitmap icon){
		if(icon == null){
			Log.v("icon == null","------>");
			return null;
		
		}else{
			Log.v("icon != null","------>");
			ContentValues values = new ContentValues();
			final ByteArrayOutputStream os = new ByteArrayOutputStream(); 
			icon.compress(Bitmap.CompressFormat.PNG, 100, os);  
			values.put("user_image", os.toByteArray());
			
			return values;
		}
		
	}
	public static byte[] bmpTobytes(Bitmap bit){
		if(bit == null){
			return null;
		}else{
			final ByteArrayOutputStream os = new ByteArrayOutputStream(); 
			bit.compress(Bitmap.CompressFormat.PNG, 100, os);
			return os.toByteArray();
		}
		
	}
	private static double getSize(Bitmap image) {
		// TODO Auto-generated method stub
	double	size = Math.sqrt(image.getWidth() * image.getWidth()+image.getHeight() * image.getHeight()); 
		return size;
	}
	public static void BitmapRecyle(Bitmap bitmap){
		bitmap.recycle();
		bitmap = null;
		System.gc();
	}
}