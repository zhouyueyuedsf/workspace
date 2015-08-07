

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//监听8899端口的信息并转发给客户端
public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;

	List clients = new ArrayList();

	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			// ServerSocket监听8899端口
			ss = new ServerSocket(8899);
			started = true;
		} catch (BindException e) {
			System.out.println("start....");
			System.out.println("有问题");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (started) {
				Socket s = ss.accept();
				Client c = new Client(s);//建立一个客户端
				System.out.println("a client connected!");
				clients.add(c);//加入客户端
				new Thread(c).start();	
				// dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable {
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void send(String str) {
			try {
				System.out.println("消息"+str);
				dos.writeUTF(str);
				
			} catch (IOException e) {
				clients.remove(this);//第一次写入时为何会出现IO异常?然后屏幕就不能显示
				// e.printStackTrace();
			}
		}

		public void run() {
			try {
				while (bConnected) {
					//无数据会阻塞
					String str = dis.readUTF();
//					System.out.println(str);
					for (int i = 0; i < clients.size(); i++) {
						Client c = (Client) clients.get(i);//将某客户段的消息广播给各个客户端
						c.send(str);
					}
					
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					System.out.println("close All !");
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null) {
						s.close();
						// s = null;
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

	}
}
