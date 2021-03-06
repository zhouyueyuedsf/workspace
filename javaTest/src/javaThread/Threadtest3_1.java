package javaThread;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//这段代码整体的意思是:一个类在线程中调用被synchronize标记的getPair()函数的时候,另一个类也想调用这函数于是发生阻塞,
class Pair {
	private int x , y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pair() {
		// TODO Auto-generated constructor stub
		this(0, 0);// 初始化x=0,y=0
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void incrementX() {
		x++;// 在java中x++这种操作不是线程安全的,需要用到原子类
	}

	public void incrementY() {
		y++;
	}

	public String toString() {
		return "x:" + x + ".y" + y;
	}

	public class PairVNEE extends RuntimeException {
		public PairVNEE() {
			super("Pair values not equal: " + Pair.this);
		}
	}

	public void checkState() {
		if (x != y) {
			throw new PairVNEE();
		}
	}
}

abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0);// 原子类
	protected Pair p = new Pair();
	private List<Pair> storage = Collections
			.synchronizedList(new ArrayList<Pair>());

	public synchronized Pair getPair() {
		return new Pair(p.getX(), p.getY());// 俩个线程都要调用getPair()
	}

	protected void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract void increment();
}

class PairManager1 extends PairManager {

	@Override
	public void increment() {
		// TODO Auto-generated method stub
		// pm1
		p.incrementX();
		p.incrementY();
		store(getPair());//

	}

}

class PairManager2 extends PairManager {

	@Override
	public void increment() { // pm2
		// TODO Auto-generated method stub
		Pair temp;
		// 如果某个任务处于一个对标记为synchronized的方法的调用中,
        //那么在这个线程从该方法返回之前,其他所有要调用类中任何标记为synchronized的方法都会被阻塞
		synchronized (this) {
			p.incrementX();// x++
			p.incrementY();// y++
			temp = getPair();//调用标记线程被阻塞
		}
		store(temp);// store方法是线程安全的不用放到synchronized
	}

}

class PairManipulator implements Runnable {
	private PairManager pm;

	public PairManipulator(PairManager pm) {
		// TODO Auto-generated constructor stub
		this.pm = pm;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			pm.increment();
		}

	}

	public String toString() {
		return "Pair: " + pm.getPair() + "checkCounter ="
				+ pm.checkCounter.get();
	}

}

class PairChecker implements Runnable {
	private PairManager pm;

	public PairChecker(PairManager pm) {
		this.pm = pm;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			pm.checkCounter.incrementAndGet();// 原子类本身提供的一个方法,自增
			pm.getPair().checkState();
		}
	}

}

public class Threadtest3_1 {
	static void testApproaches(PairManager pman1, PairManager pman2) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManipulator pm1 = new PairManipulator(pman1);// pm1
		PairManipulator pm2 = new PairManipulator(pman2);// pm2
		PairChecker pcheck1 = new PairChecker(pman1);
		PairChecker pcheck2 = new PairChecker(pman2);
		// 启动俩个线程
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pcheck1);
		exec.execute(pcheck2);
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
		System.exit(0);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PairManager pman1 = new PairManager1();
		PairManager pman2 = new PairManager2();
		testApproaches(pman1, pman2);
	}

}
