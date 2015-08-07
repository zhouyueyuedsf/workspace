package javaServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SocketServer {
	public static void main(String[] args) {
		try {
			ServerSocket socket = new ServerSocket(12345);
			Socket client = socket.accept();
			JOptionPane.showMessageDialog(null, "有客户端连接");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
