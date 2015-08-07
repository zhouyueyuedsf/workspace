package com.example.volleydemo;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

	public class BitmapCache implements ImageCache{

		private LruCache<String, Bitmap> mCache; 
		 public BitmapCache(int maxSize) {  
		        mCache = new LruCache<String, Bitmap>(maxSize) {  
		            @Override  
		            protected int sizeOf(String key, Bitmap bitmap) {  
		                return bitmap.getRowBytes() * bitmap.getHeight();  
		            }  
		        };  
		    }  
	@Override
	public Bitmap getBitmap(String url) {
	      return mCache.get(url); 
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap); 
		
	}

}
