package com.example.downloaddemo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpStatus;

import android.content.Context;
import android.content.Intent;

import com.example.downloaddemo.bean.FileInfo;
import com.example.downloaddemo.bean.ThreadInfo;
import com.example.downloaddemo.db.ThreadDAO;
import com.example.downloaddemo.db.ThreadDAOImpl;

/*
 * ����������
 */
public class DownLoadTask {
	private Context mContext = null;
	private FileInfo mFileInfo = null;
	private ThreadDAO mDao = null;
	private int mFinished;
	public boolean isPause = false;
	public DownLoadTask(Context mContext, FileInfo mFileInfo) {
		mDao = new ThreadDAOImpl(mContext);
		this.mContext = mContext;
		this.mFileInfo = mFileInfo;
	}
	//��������ش���
	public void download(){
		List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
		ThreadInfo threadInfo = null;
		if(threadInfos.size() == 0){
			threadInfo = new ThreadInfo(0, mFileInfo.getUrl() , 0, mFileInfo.getLength(), 0);
		}else{
			threadInfo = threadInfos.get(0);
		}
		new DownLoadThread(threadInfo).start();
	}
	class DownLoadThread extends Thread{
		private ThreadInfo mThreadInfo;

		public DownLoadThread(ThreadInfo mThreadInfo) {
			super();
			this.mThreadInfo = mThreadInfo;
		}
		
		@SuppressWarnings("resource")
		@Override
		public void run() {
			InputStream input = null;
			RandomAccessFile raf = null;
			HttpURLConnection conn = null;
			try {
				// TODO Auto-generated method stub
				//�����ݿ���������߳���Ϣ
				if(!mDao.isExists(mThreadInfo.getUrl(), mThreadInfo.getId())){
					mDao.insertThread(mThreadInfo);
				}
				//��ʼ������
				URL url = new URL(mThreadInfo.getUrl());
				conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");		
				//��������λ��
				int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
				conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());

				//�����ļ�д��λ��
				File file = new File(DownLoadService.DOWNLOAD_PATH, mFileInfo.getFileName());
				 raf = new RandomAccessFile(file, "rwd");
				raf.seek(start);
				Intent intent = new Intent(DownLoadService.ACTION_UPDATE);
				mFinished = mThreadInfo.getFinished();
				//��ʼ����
				if(conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT){

					//��ȡ����
					 input = conn.getInputStream();
					byte[] buffer = new byte[1024 * 4];
					int len = -1;
					long time = System.currentTimeMillis();
					while((len = input.read(buffer)) != -1){
						//д���ļ�
						raf.write(buffer, 0, len);
						//�����ؽ��ȷ��͹㲥��activity
						mFinished += len;
						if(System.currentTimeMillis() - time > 500){
							//����������Ϣ500ms�ڷ���һ��,��������������Ҫ�úܶ�
							intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
							mContext.sendBroadcast	(intent);
						}
						//��������ͣʱ,�������ؽ���
						if(isPause){
							mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
							return;
						}
					}
					//ɾ���߳���Ϣ
					mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());					
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					conn.disconnect();
					raf.close();
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
