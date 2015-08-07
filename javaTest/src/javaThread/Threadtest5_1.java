package javaThread;

public class Threadtest5_1 {
	public static class MutliThread extends Thread{ 
	    private int ticket=100;//每个线程都拥有100张票 
	    public void run(){ 
	        while(ticket>0){ 
	            System.out.println(ticket--+" is saled by "+Thread.currentThread()); 
	        } 
	    } 
	}
	/**
	 * main函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MutliThread m = new MutliThread();
		m.start();
//		Thread t1=new Thread(m); 
//        Thread t2=new Thread(m); 
//        Thread t3=new Thread(m); 
//        t1.start(); 
//        t2.start(); 
//        t3.start(); 
	}

}
