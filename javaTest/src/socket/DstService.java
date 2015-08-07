package socket;

import java.net.ServerSocket;
import java.net.Socket;

public class DstService {
	  public static void main(String[] args) {
		  try {				
			  //启动监听端口 30000
		      ServerSocket ss = new ServerSocket(30000);
		      // 没有连接这个方法就一直堵塞
		      Socket s = ss.accept();
		      // 将请求指定一个线程去执行
		      new Thread(new DstServiceImpl(s)).start();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
}
