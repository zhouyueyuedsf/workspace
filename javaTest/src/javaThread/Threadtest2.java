package javaThread;

public class Threadtest2 implements Runnable {
	int count= 1, number;
	/**
	 * @param args
	 */
	public Threadtest2(int num) {
		number = num;
	System.out.println("创建线程 " + number);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i<5;i++) new Thread(new Threadtest2(i+1)).start();
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
	{
	System.out.println
	("线程 " + number + ":计数 " + count);
	}
	}

}
