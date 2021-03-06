package javaThread;

public class Threadtest3 {

	private int j;
	 public static void main(String[] args){
		 Threadtest3 t = new Threadtest3();
	     Inc inc = t.new Inc();
	     Dec dec = t.new Dec();
	     for(int i=0;i<2;i++){
	      Thread ts = new Thread(inc);
	      ts.start();
	      ts= new Thread(dec);
	      ts.start();
	     }
	 }
	 //synchronized让方法按顺序执行,在线程中用得特别多
	 private synchronized void inc(){
	     j++;
	     System.out.println(Thread.currentThread().getName()+"-inc:"+j);
	 }
	 private synchronized void dec(){
	     j--;
	     System.out.println(Thread.currentThread().getName()+"-dec:"+j);
	 }
	 class Inc implements Runnable{
	    public void run(){
	     for(int i=0;i<10;i++){
	      inc();
	     }
	    }
	 }
	 class Dec implements Runnable{
	  public void run(){
	   for(int i=0;i<10;i++){
	    dec();
	   }
	     }
	 }
}
