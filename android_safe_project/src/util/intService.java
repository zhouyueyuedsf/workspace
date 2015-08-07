package util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class intService {
	public final String TAG="Mytest";
	private Context context;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	public intService(Context context) {
	      this.context=context;
		}
	public void writeboolean(Map<String, Object> map)
	{
		 preferences=context.getSharedPreferences("share", Context.MODE_PRIVATE);
		 Log.v("share", "------>");
		 editor=preferences.edit();
			for(Map.Entry<String, Object> entry:map.entrySet())
			{
			       String key=entry.getKey();
			  	 Log.v("share", "------>");	 
			       Object s=entry.getValue();
			  Log.v(s.toString(), "------>");
				editor.putFloat(key,Float.parseFloat(s.toString()));//把key and value 打到文件名为boolean里面
				 Log.v("share", "------>");
			}
		

		editor.commit();
	}
	public Map<String, ?> readboolean()
	{	
		Log.v("getSubsetBitmap", "------>");
	 preferences=context.getSharedPreferences("share", Context.MODE_PRIVATE);
		Log.v("getSubsetBitmap", "------>");
	Map<String, ?> map=preferences.getAll();
    return map;
   
	}
}
