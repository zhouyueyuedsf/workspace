package com.example.android_safe_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSInfoProvider {
	private static GPSInfoProvider mGPSInfoProvider;
	private static LocationManager lm;// λ�ù�����
	private static MyListener listener;// λ�ñ仯�ļ����������������ȽϺĵ�
	private static SharedPreferences sp;// �־û�λ�õ���Ϣ����γ�ȣ�

	// ˽�л����췽�������ɵ���ģʽ��Ŀ������ ������ϵͳ����ע��������������ҵ� �����ٺĵ���
	private GPSInfoProvider() {

	}

	public synchronized static GPSInfoProvider getInstance(Context context) {
		if (mGPSInfoProvider == null) {
			mGPSInfoProvider = new GPSInfoProvider();
			// ��ȡλ�ù�����
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			// ��ȡ��ѯ����λ�õĲ�ѯ���������ڲ���һ��Map���ϣ�
			Criteria criteria = new Criteria();
			// ���þ�ȷ�ȣ����ﴫ�ݵ����׼�ľ�ȷ��
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			// gps��λ�Ƿ��������������true��ʾ�����������������
			criteria.setCostAllowed(true);
			// �ֻ��Ĺ������������ʵʱ��λʱ��Ӧ������Ϊ�ߣ�
			criteria.setPowerRequirement(Criteria.POWER_HIGH);
			// ��ȡ������Ϣ
			criteria.setAltitudeRequired(true);
			// ���ֻ����ƶ����ٶ��Ƿ�����
			criteria.setSpeedRequired(true);
			// ��ȡ����ǰ�ֻ�����õ�λ���ṩ�ߣ�����һ����ѯ��ѡ������ ������������Ϊtrueʱ����ʾֻ�п��õ�λ���ṩ��ʱ�Żᱻ���ػ�ȥ
			String provider = lm.getBestProvider(criteria, true);
			// System.out.println(provider);
			listener = new GPSInfoProvider().new MyListener();
			// ���ø���λ�÷���������һ��λ���ṩ�ߣ�����������̵ĸ���λ����Ϣʱ�䣨���Ҫ����60000��һ���ӣ����������������֪ͨ���룬�����ģ�λ�øı�ʱ�ļ�������
			lm.requestLocationUpdates(provider, 60000, 100, listener);
			// ��Sdcard��Ӧ�İ��д���һ��config.xml�ļ����ļ��Ĳ�����������ΪPRIVATE
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return mGPSInfoProvider;
	}

	/**
	 * ȡ��λ�õļ���
	 */
	public void stopLinsten() {
		lm.removeUpdates(listener);
		listener = null;
	}

	protected class MyListener implements LocationListener {

		/**
		 * ���ֻ���λ�÷����ı��ʱ�� ���õķ���
		 */
		public void onLocationChanged(Location location) {
			String latitude = "latitude :" + location.getLatitude(); // γ��
			String longitude = "longitude: " + location.getLongitude(); // ����
			String meter = "accuracy :" + location.getAccuracy();// ��ȷ��
			System.out.println(latitude + "-" + longitude + "-" + meter);

			Editor editor = sp.edit();
			editor.putString("last_location", latitude + "-" + longitude + "-"
					+ meter);
			editor.commit();
		}

		/**
		 * ��λ���ṩ�� ״̬�����ı��ʱ�� ���õķ���
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		/**
		 * ��ĳ��λ���ṩ�߿��õ�ʱ��.
		 */
		public void onProviderEnabled(String provider) {

		}

		/**
		 * ��ĳ��λ���ṩ�� �����õ�ʱ��
		 */
		public void onProviderDisabled(String provider) {

		}
	}

	/**
	 * ��ȡ�ֻ���λ��
	 * 
	 * @return
	 */
	public String getLocation() {
		return sp.getString("last_location", "");
	}

}
