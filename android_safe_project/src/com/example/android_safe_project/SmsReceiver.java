package com.example.android_safe_project;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SmsReceiver extends BroadcastReceiver {
	private static final String TAG = "SmsReceiver";
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String safenumber = sp.getString("safemuber", "");
		// ��ȡ�����е����ݡ�ϵͳ���յ�һ����Ϣ�㲥ʱ���Ὣ���յ�����Ϣ��ŵ�pdus������
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		// ��ȡ�ֻ��豸������
		DevicePolicyManager dm = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		// ����һ����MyAdmin����������
		ComponentName mAdminName = new ComponentName(context, MyAdmin.class);
		// ��������Ϣ�е���������
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			// ��ȡ�����˵ĺ���
			String sender = smsMessage.getOriginatingAddress();
			// ��ȡ������Ϣ����
			String body = smsMessage.getMessageBody();

			if ("#*location*#".equals(body)) {

				// ��ȡ�ϴε�λ��
				String lastlocation = GPSInfoProvider.getInstance(context)
						.getLocation();
				if (!TextUtils.isEmpty(lastlocation)) {
					// �õ���Ϣ������
					SmsManager smsManager = SmsManager.getDefault();
					// ��ȫ���뷢�͵�ǰ��λ����Ϣ
					smsManager.sendTextMessage(safenumber, null, lastlocation,
							null, null);
				}
				abortBroadcast();
			} else if ("#*alarm*#".equals(body)) {

				// //�õ���Ƶ������
				// MediaPlayer player = MediaPlayer.create(context,
				// R.raw.ylzs);//res\raw\ylzs.mp3
				// //��ʹ�ֻ��Ǿ���ģʽҲ�����ֵ�����
				// player.setVolume(1.0f, 1.0f);
				// //��ʼ��������
				// player.start();
				// //��ֹ�����͹�������Ϣ���ڱ��ز鿴��������Ϣ
				// abortBroadcast();
			} else if ("#*wipedata*#".equals(body)) {

				// �ж��豸�Ĺ���ԱȨ���Ƿ񱻼��ֻ�б�����󣬲ſ���ִ����Ƶ��������ݻָ����������ã�ģ������֧�ָò������Ȳ���
				if (dm.isAdminActive(mAdminName)) {
					dm.wipeData(0);// ������ݻָ����������á��ֻ����Զ�����
				}
				abortBroadcast();
			} else if ("#*lockscreen*#".equals(body)) {

				if (dm.isAdminActive(mAdminName)) {
					dm.resetPassword("123", 0);// ��Ļ����ʱ��Ҫ�Ľ�������123
					dm.lockNow();
				}
				abortBroadcast();
			}
		}
	}

}
