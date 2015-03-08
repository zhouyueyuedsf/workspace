package javaThread;

public class Threadtest1 extends Thread {
	int count;
	public Threadtest1() {
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Threadtest1().start();//此句便是run()的执行
	}

}
