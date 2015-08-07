package javaServer;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class RemoteControlServer extends JFrame {
	private static int port;
	ServerThread serverthread; // 初始化线程

	ServerSocket socket;
	final JTextField messagebox;
	private JTextField field;
	final JButton stopbutton;
	final JButton startbutton;
	static int menux = 0; // menux信号量 0表示未开启 1表示开启 2表示暂停
	String message = null;
	String[] messages = null;
	String type = null;// 发过来的信息属于哪种类型，如：鼠标、左键、右键、滚轮
	String info = null;// information;执行何种操作

	public RemoteControlServer() {
		super();     //继承父类的构造方法
	  //setResizable(false); //设置窗体大小不可变
		setTitle("服务器端");
		setSize(500, 182);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = getToolkit(); // 获得Toolkit对象
		Dimension dimension = toolkit.getScreenSize(); // 获得Dimension对象
		int screenHeight = dimension.height; // 获得屏幕的高度
		int screenWidth = dimension.width; // 获得屏幕的宽度
		int frm_Height = this.getHeight(); // 获得窗体的高度
		int frm_width = this.getWidth(); // 获得窗体的宽度
		setLocation((screenWidth - frm_width) / 2,
				(screenHeight - frm_Height) / 2); // 使用窗体居中显示

		getContentPane().setLayout(null);
		final JLabel label = new JLabel();
		try {
			label.setText("主机IP:  "
					+ InetAddress.getLocalHost().getHostAddress());// 界面显示IP
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setBounds(10, 20, 300, 25);
		Font font = new Font("SimSun", Font.PLAIN, 14);
		label.setFont(font);
		getContentPane().add(label);

		final JLabel label2 = new JLabel();
		label2.setText("监听端口:");
		label2.setBounds(10, 50, 100, 25);
		Font font1 = new Font("SimSun", Font.PLAIN, 14);
		label2.setFont(font1);
		getContentPane().add(label2);

		field = new JTextField();
		field.setBounds(110,50,90,25);
		getContentPane().add(field);

		startbutton = new JButton();
		startbutton.setText("Begin");
		startbutton.setBounds(10, 90, 80, 25);
		getContentPane().add(startbutton);

		stopbutton = new JButton();
		stopbutton.setText("Stop");
		stopbutton.setEnabled(false);
		stopbutton.setBounds(120, 90, 80, 25);
		getContentPane().add(stopbutton);

		final JLabel label3 = new JLabel();
		label3.setText("--------监听信息--------");
		label3.setBounds(292, 35, 280, 25);
		getContentPane().add(label3);

		final JLabel label4 = new JLabel();
		label4.setText("接收到信息：");
		label4.setBounds(50, 150, 280, 20);
		//getContentPane().add(label4);

		messagebox = new JTextField();
		messagebox.setBounds(270, 90, 190, 25);
		messagebox.enable(false);
		getContentPane().add(messagebox);

		final JLabel label5 = new JLabel();
		label5.setText("-----------客户端-----------");
		label5.setBounds(10, 220, 190, 25);
		//getContentPane().add(label5);

		startbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String str = field.getText().trim();
				int num;
				if (str.equals("")) {
					JOptionPane.showMessageDialog(null, "您没有输入端口号！");
					return;
				}
				try {
					num = Integer.parseInt(str);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "您输入的不是数字！");
					return;
				}
				if (num < 0 || num > 65535) {
					JOptionPane.showMessageDialog(null, "您输入的范围不正确！");
					return;
				}
				port = num;
				stopbutton.setEnabled(true); // 输入错误时可以点击stop键
				startbutton.setEnabled(false);
				start();

			}
		});

		stopbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// startbutton.setEnabled(false);
				stop();
				stopbutton.setEnabled(false);
				startbutton.setEnabled(true);
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new RemoteControlServer();
			}
		});

	}

	
	public void start() {
		if (menux == 0) { // menux信号量 0表示未开启  1表示开启  2表示暂停
			serverthread = new ServerThread();
			serverthread.start();
			menux = 1;
			messagebox.setText("开始进程！");
			field.setEditable(false);
		}
		if (menux == 2) {
			serverthread.resume();
			menux = 1;
			messagebox.setText("恢复进程！");
		}
	}

	
	public void stop() {
		if (menux == 1) {
			serverthread.suspend();
			menux = 2;
			messagebox.setText("进程挂起！");
		}

	}

	public class ServerThread extends Thread {

		public void run() {
			try {
				// 创建一个DatagramSocket对象，并指定监听的端口号
		
				try {
					socket = new ServerSocket(12345);
					while(true){
						Socket client = socket.accept();
						DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
						DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
						dataOutputStream.writeUTF("hello");
						
						dataOutputStream.flush();
						
						JOptionPane.showMessageDialog(null, "客户端已连接");
//						
//						
//						String str = dataInputStream.readUTF();
					}
					
				} catch (Exception e) {
					messagebox.setText("端口被使用,请更换端口");
					startbutton.setEnabled(true);
					stopbutton.setEnabled(false);
					menux = 0;
					field.setEditable(true);
					return;
				}
				
				// 创建一个空的DatagramPacket（数据报）对象


				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
		   }
		
	
       
        
//		private void sendMessage(String string) {
//			// TODO Auto-generated method stub
//
//	 		try {
//	 			// 首先创建一个DatagramSocket对象
//	 			socket=new DatagramSocket();
//	 			sendMessage("hello");
//	 			byte data[] = string.getBytes();
//	 			DatagramPacket dp = new DatagramPacket(data, data.length,packet.getAddress(),packet.getPort());
////	 			packet1.setData(string.getBytes());
//	 			socket.send(dp);
//	 		
//	 		} catch (Exception e) {
//	 			// TODO Auto-generated catch block
//	 			e.printStackTrace();
//	 		}
//	 	
//		}
	  };
   }

	

	        


