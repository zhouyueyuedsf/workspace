package com.example.volleydemo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView mIdTxt,mNameTxt,mDownloadTxt,mLogoTxt,mVersionTxt ;
	private ImageView mHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIdTxt = (TextView) findViewById(R.id.id) ;
        mNameTxt = (TextView) findViewById(R.id.name) ;
        mDownloadTxt = (TextView) findViewById(R.id.download) ;
        mLogoTxt = (TextView) findViewById(R.id.logo) ;
        mVersionTxt = (TextView) findViewById(R.id.version) ; 
        mHead = (ImageView)findViewById(R.id.show);
        String imageUrl = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
        String objectUrl = "http://www.minongbang.com/test.php?test=minongbang";
        String xmlUrl = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        loadImage(imageUrl);
        loadObject(objectUrl);
        loadXML(xmlUrl);
    }


    private void loadXML(String xmlUrl) {
    	XMLRequest xmlRequest = new XMLRequest(Method.GET, xmlUrl, new Response.Listener<XmlPullParser>() {
    		public void onResponse(XmlPullParser response) {
    			//xml解析
    			try {
					int eventType = response.getEventType();
					while(eventType != XmlPullParser.END_DOCUMENT){
						switch (eventType) {
						case XmlPullParser.START_TAG:
							String nodeName = response.getName();  
                            if ("city".equals(nodeName)) {  
                                String pName = response.getAttributeValue(0);  
                            }  
							break;

						case XmlPullParser.END_TAG:
							break;
						}
						eventType = response.next();
					}
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		};
    		
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private void loadObject(String objectUrl) {
		// TODO Auto-generated method stub
    	try {
    		ObjectRequest.getObjectRequst(objectUrl, new ResponseListener<TestBean>() {
    			
    			@Override
    			public void onErrorResponse(VolleyError arg0) {
    				
    			}
    			@Override
    			public void onResponse(TestBean response) {
    				String str = response.getName();
    				mIdTxt.setText(response.getId()+"");
    				mNameTxt.setText(response.getName());
    				mDownloadTxt.setText(response.getDownload()+"");
    				mLogoTxt.setText(response.getLogo());
    				mVersionTxt.setText(response.getVersion()+"");
    			}
    		});
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
		
	}


	public void loadImage(String url){
    	VolleyUtil.initialize(this);  
        //加载图片开始
        ImageLoader imageLoader = new ImageLoader(VolleyUtil.getRequestQueue(), new BitmapCache(10*1024*1024));
        /**
         * 第二个参数是默认的图片,第三个是加载错误的图片
         */
        ImageListener imageListener = ImageLoader.getImageListener(mHead, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get(url,  
        		imageListener, 200, 200); 
        //加载图片结束
    }
    
}
