package javaThread;

public class Threadtest5 {
	//java中,类中的静态方法不能直接调用动态方法
	public static class MutliThread implements Runnable{ 
	    private int ticket=100;//每个线程都拥有100张票 
	    public void run(){ 
	        while(ticket>0){ 
	            System.out.println(ticket--+" is saled by "+Thread.currentThread()); 
	        } 
	    } 
	}
	/**
	 * main函数
	 * 多个线程共享同一资源100,因为m都是同一个对象,这是runnable的优势所在,
	 * 而继承Thread的话是达不到这种
	 * 效果的
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MutliThread m = new MutliThread();
		
        Thread t1=new Thread(m); 
        Thread t2=new Thread(m); 
        Thread t3=new Thread(m); 
        t1.start(); 
        t2.start(); 
        t3.start(); 
	}

}
