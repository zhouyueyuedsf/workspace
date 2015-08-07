package socket;

import java.net.Socket;

public class DstServiceImpl implements Runnable {

	  Socket socket = null;
	  public DstServiceImpl(Socket s) {
	    this.socket = s;
	  }
	  public void run() {
	    try {
	      int index = 1;
	      while (true) {
	        // 5秒后中断连接
	        if (index > 10) {
	          socket.close();
	          System.out.println("服务端已经关闭链接！");
	          break;
	        }
	        index++;
	        Thread.sleep(1 * 1000);//程序睡眠1秒钟
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
