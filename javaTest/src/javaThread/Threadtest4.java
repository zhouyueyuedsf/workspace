package javaThread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
//
//public class Threadtest4 implements Runnable{
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ExecutorService exec = Executors.newCachedThreadPool();
//		exec.execute(new Threadtest4());
//	}
//
//	public void run() {
//		// TODO Auto-generated method stub
//		throw new RuntimeException();
//	}
//
//}
	//具体的捕获线程异常
class ExceptionThread2 implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		Thread t = Thread.currentThread();
		System.out.println("run() by"+t);
		System.out.println("en = "+t.getUncaughtExceptionHandler());
		throw new RuntimeException();
		}
}
class MyUncaughtExecHandler implements Thread.UncaughtExceptionHandler{//1.2:建立线程异常捕获函数

	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		System.out.println("caught" + e);
    	}
}
class HandlerThreadFactory implements ThreadFactory{//1:建立线程工厂函数

	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		System.out.println(this + "creating new Thread");
		Thread t = new Thread(r);//1.1:初始化监听线程异常的线程
		System.out.println("created" + t);
		t.setUncaughtExceptionHandler(new MyUncaughtExecHandler());//1.3:设置线程异常捕获函数
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		return t;
	}
	
}
public class Threadtest4{
	
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(new ExceptionThread2());//1.4:运行异常
	}
}