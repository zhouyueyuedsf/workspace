package com.example.downloaddemo.db;

import java.util.List;

import com.example.downloaddemo.bean.ThreadInfo;

/*
 * 数据访问接口
 */
public interface ThreadDAO {
	public void insertThread(ThreadInfo threadInfo);
	public void deleteThread(String url, int thread_id);
	public void updateThread(String url, int thread_id, int finished);
	public List<ThreadInfo> getThreads(String url);
	/*
	 * 线程信息是否存在
	 */
	public boolean isExists(String url, int thread_id);
}
