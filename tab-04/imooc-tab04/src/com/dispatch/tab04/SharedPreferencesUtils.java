package com.dispatch.tab04;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPreferencesUtils {
	public static final String PREF_NAME = "org.king.pref_name_jenly";

	public static SharedPreferences getSharedPreferences(Context context) {
		return getSharedPreferences(context, PREF_NAME);
	}

	public static SharedPreferences getSharedPreferences(Context context,
			String prefName) {
		return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
	}

	public static void clearFile(Context context, String prefName) {
		File file = new File("/data/data/"
				+ context.getPackageName().toString() + "/shared_prefs",
				prefName+".xml");
		if (file.exists()) {
			file.delete();
		}
	}

	// --------------------------Int
	public static void putInt(Context context, String key, int value) {
		getSharedPreferences(context).edit().putInt(key, value).commit();
	}

	public static int getInt(Context context, String key, int defValue) {
		return getSharedPreferences(context).getInt(key, defValue);
	}

	public static int getInt(Context context, String key) {
		return getInt(context, key, 0);
	}

	public static int getInt(Context context, String key, String prefName) {
		return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
				.getInt(key, -1);
	}

	// --------------------------Float
	public static void putFloat(Context context, String key, float value) {
		getSharedPreferences(context).edit().putFloat(key, value).commit();
	}

	public static float getFloat(Context context, String key, float defValue) {
		return getSharedPreferences(context).getFloat(key, defValue);
	}

	public static float getFloat(Context context, String key) {
		return getFloat(context, key, 0);
	}

	public static float getFloat(Context context, String key, String prefName) {
		return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
				.getFloat(key, -1f);
	}

	// --------------------------Long
	public static void putLong(Context context, String key, long value) {
		getSharedPreferences(context).edit().putLong(key, value).commit();
	}

	public static long getLong(Context context, String key, long defValue) {
		return getSharedPreferences(context).getLong(key, defValue);
	}

	public static long getLong(Context context, String key) {
		return getLong(context, key, 0);
	}

	// --------------------------Boolean
	public static void putBoolean(Context context, String key, boolean value) {
		getSharedPreferences(context).edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		return getSharedPreferences(context).getBoolean(key, defValue);
	}

	public static boolean getBoolean(Context context, String key) {
		return getBoolean(context, key, false);
	}

	// --------------------------String
	public static void putString(Context context, String key, String value) {
		getSharedPreferences(context).edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		return getSharedPreferences(context).getString(key, defValue);
	}

	public static String getString(Context context, String key) {
		return getString(context, key, null);
	}

	/**
	 * 
	 * @param context
	 * @param map
	 */
	public static void putMapObject(Context context, Map<?, ?> map,
			String prefName) {
		if (map != null) {
			SharedPreferences.Editor editor = getSharedPreferences(context, prefName)
					.edit();
			for (Entry<?, ?> entry : map.entrySet()) {
				if (entry.getKey() != null)
					// 检查entry.getkey()是否是String
					if (entry.getKey() instanceof String) {
						editor.putString((String) entry.getKey(),
								(String) entry.getValue());
					} else if (entry.getKey() instanceof Integer) {
						editor.putFloat(entry.getKey() + "",
								(Float) entry.getValue());
					}
			}
			editor.commit();

		}
	}

	// --------------------------Increase int
	/**
	 * 自增(默认自增1)
	 * 
	 * @param context
	 * @param key
	 */
	public static void increase(Context context, String key) {
		increase(context, key, 1);
	}

	/**
	 * 自增
	 * 
	 * @param context
	 * @param key
	 * @param deltaValue
	 *            增量值(基数)
	 */
	public static void increase(Context context, String key, int deltaValue) {
		getSharedPreferences(context).edit()
				.putInt(key, getInt(context, key) + deltaValue).commit();
	}
	public static void clearData(Context context,String prefName){
		getSharedPreferences(context, prefName).edit().clear().commit();
	}
}
