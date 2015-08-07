package com.example.android_safe_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "�ֻ�������");
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		// �ֻ������Ƿ���
		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting) {
			// ��ȡ��ȫ����
			String safemuber = sp.getString("safemuber", "");
			// �ж� ��ǰ�ֻ���sim�� ���Ұ󶨵�sim�Ƿ�һ��.
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			// ��ȡ��ǰsim���Ĵ���
			String realsim = tm.getSimSerialNumber();
			// ��ȡ֮ǰ�����sim���Ĵ���
			String savedSim = sp.getString("simserial", "");
			// �ж����������Ƿ���ͬ
			if (!savedSim.equals(realsim)) {
				// ����������
				Log.i(TAG, "���Ͷ���");
				SmsManager smsManager = SmsManager.getDefault();
				// 1.���ն��ź��� 2.���ŵ�Դ��ַ 3.��Ϣ���� 4.������ͼ����ǰ�¼���������ִ�У� 5.�ʹﱨ��
				smsManager.sendTextMessage(safemuber, null, "sim card changed",
						null, null);

			}
		}
	}
}
