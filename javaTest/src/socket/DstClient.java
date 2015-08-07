package socket;

import java.net.Socket;

public class DstClient {
	  public static void main(String[] args) {
		    try {
		      Socket socket = new Socket("127.0.0.1", 30000);
		      socket.setKeepAlive(true);
		      socket.setSoTimeout(10);
		      while (true) {
		        System.out.println(socket.isBound());
		        System.out.println(socket.isClosed());
		        System.out.println(socket.isConnected());
		        System.out.println(socket.isInputShutdown());
		        System.out.println(socket.isOutputShutdown());
		        System.out.println("------------我是分割线------------");
		        Thread.sleep(3 * 1000);
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }
}
